import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class addCompetition extends JFrame {
    private JLabel competitionLabel;
    private JLabel competitionNameLabel;
    private JLabel organizerLabel;
    private JLabel locationLabel;
    private JTextField textFieldCompetition;
    private JTextField textFieldOrganizer;
    private JTextField textFieldLocation;
    private JButton buttonConfirm;
    private JButton buttonCancel;
    private JPanel MainPanel;
    private JPanel ButtonPanel;

    public addCompetition() {
        var currentUser = Session.getCurrentUser();

        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayout(0, 1));

        competitionLabel = new JLabel();
        competitionLabel.setText("Create a new competition");
        MainPanel.add(competitionLabel);

        competitionNameLabel = new JLabel();
        competitionNameLabel.setText("Competition name:");
        MainPanel.add(competitionNameLabel);

        textFieldCompetition = new JTextField();
        MainPanel.add(textFieldCompetition);

        organizerLabel = new JLabel();
        organizerLabel.setText("Organizer:");
        MainPanel.add(organizerLabel);

        textFieldOrganizer = new JTextField();
        MainPanel.add(textFieldOrganizer);

        locationLabel = new JLabel();
        locationLabel.setText("Location:");
        MainPanel.add(locationLabel);

        textFieldLocation = new JTextField();
        MainPanel.add(textFieldLocation);

        ButtonPanel = new JPanel();
        ButtonPanel.setLayout(new GridLayout());
        MainPanel.add(ButtonPanel);

        buttonConfirm = new JButton();
        buttonConfirm.setText("Confirm");
        ButtonPanel.add(buttonConfirm);

        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        ButtonPanel.add(buttonCancel);


        setContentPane(MainPanel);
        setTitle("Main Page");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 300);
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
                String compName = textFieldCompetition.getText();
                String compOrg = textFieldOrganizer.getText();
                String compLoc = textFieldLocation.getText();
                try {
                    CompetitionManager.createCompetition(compName, compOrg, compLoc);
                    CompetitionManager.printCompetitions();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
