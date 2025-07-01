import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class changeRole extends JFrame {
    private JLabel userNameLabel;
    private JTextField textFieldUserName;
    private JRadioButton radioButtonVisitor;
    private JRadioButton radioButtonAthlete;
    private JRadioButton radioButtonSecretary;
    private JRadioButton radioButtonCoach;
    private JRadioButton radioButtonAdmin;
    private JButton buttonConfirm;
    private JButton buttonCancel;
    private JPanel ButtonPanel;
    private JPanel UserInfoPanel;
    private JPanel MainPanel;

    public changeRole() {
        var currentUser = Session.getCurrentUser();

        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayout(0, 1));

        UserInfoPanel = new JPanel();
        UserInfoPanel.setLayout(new GridLayout(0, 1));
        MainPanel.add(UserInfoPanel);

        userNameLabel = new JLabel();
        userNameLabel.setText("Input the name of the user you wish to change the role of:");
        UserInfoPanel.add(userNameLabel);

        textFieldUserName = new JTextField();
        UserInfoPanel.add(textFieldUserName);

        radioButtonVisitor = new JRadioButton();
        radioButtonVisitor.setSelected(true);
        radioButtonVisitor.setText("Visitor");
        UserInfoPanel.add(radioButtonVisitor);

        radioButtonAthlete = new JRadioButton();
        radioButtonAthlete.setText("Athlete");
        UserInfoPanel.add(radioButtonAthlete);

        radioButtonSecretary = new JRadioButton();
        radioButtonSecretary.setText("Secretary");
        UserInfoPanel.add(radioButtonSecretary);

        radioButtonCoach = new JRadioButton();
        radioButtonCoach.setText("Coach");
        UserInfoPanel.add(radioButtonCoach);

        radioButtonAdmin = new JRadioButton();
        radioButtonAdmin.setText("Admin");
        UserInfoPanel.add(radioButtonAdmin);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonVisitor);
        buttonGroup.add(radioButtonAthlete);
        buttonGroup.add(radioButtonSecretary);
        buttonGroup.add(radioButtonCoach);
        buttonGroup.add(radioButtonAdmin);

        ButtonPanel = new JPanel();
        ButtonPanel.setLayout(new GridLayout());
        MainPanel.add(ButtonPanel);

        buttonConfirm = new JButton();
        buttonConfirm.setText("Confirm");
        ButtonPanel.add(buttonConfirm);

// Cancel button
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        ButtonPanel.add(buttonCancel);

        setContentPane(MainPanel);
        setTitle("Main Page");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        if (currentUser.getRole().equals("SECRETARY")) {
            radioButtonAdmin.setVisible(false);
            radioButtonSecretary.setVisible(false);
        }
        setVisible(true);


        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newRole = "VISITOR";
                if (radioButtonAdmin.isSelected()) {
                    newRole = "ADMIN";
                } else if (radioButtonSecretary.isSelected()) {
                    newRole = "SECRETARY";
                } else if (radioButtonCoach.isSelected()) {
                    newRole = "COACH";
                } else if (radioButtonAthlete.isSelected()) {
                    newRole = "ATHLETE";
                }
                changeUserRole(textFieldUserName.getText(), newRole);
            }
        });


    }

    public void changeUserRole(String userName, String newRole) {
        String filePath = "textLogin.txt"; // Modify as needed
        boolean found = false;
        List<String> modifiedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.trim().split(",");

                if (words.length > 0 && words[0].startsWith(userName)) {
                    found = true;
                    words[words.length - 1] = newRole;
                    line = String.join(",", words);
                }

                modifiedLines.add(line);
            }
        } catch (IOException e) {

        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String modifiedLine : modifiedLines) {
                writer.write(modifiedLine);
                writer.newLine();
            }

        } catch (IOException e) {

        }
        if (found) {
            JOptionPane.showMessageDialog(null, "User role change successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Error! User does not exist!");
        }
    }

}
