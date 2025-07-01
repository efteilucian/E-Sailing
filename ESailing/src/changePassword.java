import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class changePassword extends JFrame {
    private JLabel passwordChangeLabel;
    private JPanel MainPanel;
    private JPanel ChangePasswordPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel oldPasswordLabel;
    private JLabel newPasswordLabel;
    private JButton buttonConfirm;
    private JButton buttonCancel;
    private JPanel ButtonsPanel;

    public changePassword() {
        var currentUser = Session.getCurrentUser();

        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayout(0, 1));

        passwordChangeLabel = new JLabel("Change your password?", SwingConstants.CENTER);
        MainPanel.add(passwordChangeLabel);

        ChangePasswordPanel = new JPanel();
        ChangePasswordPanel.setLayout(new GridLayout(0, 1));
        MainPanel.add(ChangePasswordPanel);

        oldPasswordLabel = new JLabel();
        oldPasswordLabel.setText("Type your old password:");
        ChangePasswordPanel.add(oldPasswordLabel);

        textField1 = new JTextField();
        ChangePasswordPanel.add(textField1);

        newPasswordLabel = new JLabel();
        newPasswordLabel.setText("Type your new password:");
        ChangePasswordPanel.add(newPasswordLabel);

        textField2 = new JTextField();
        ChangePasswordPanel.add(textField2);

        ButtonsPanel = new JPanel();
        ButtonsPanel.setLayout(new GridLayout());
        MainPanel.add(ButtonsPanel);

        buttonConfirm = new JButton();
        buttonConfirm.setText("Change");
        ButtonsPanel.add(buttonConfirm);

        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        ButtonsPanel.add(buttonCancel);


        setContentPane(MainPanel);
        setTitle("Main Page");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
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
                String oldPass = textField1.getText();
                String newPass = textField2.getText();
                if (currentUser.checkPassword(oldPass)) {
                    String name = currentUser.getName();
                    String username = currentUser.getUsername();
                    try {
                        String content = new String(Files.readAllBytes(Paths.get("textLogin.txt")));
                        content = content.replace(name + "," + username + "," + oldPass, name + "," + username + "," + newPass);
                        Files.write(Paths.get("textLogin.txt"), content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    JOptionPane.showMessageDialog(null, "Password changed successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error! That password does not match your old password!");
                }
            }
        });
    }

}
