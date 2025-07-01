import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class MainScreen extends JFrame {
    private JLabel userLabel;
    private JPanel MainPanel;
    private JLabel roleLabel;
    private JLabel welcomeLabel;
    private JButton buttonChangePassword;
    private JButton buttonChangeUserRoles;
    private JPanel ButtonsPanel;
    private JButton buttonAddConpetition;
    private JButton buttonLogout;
    private JButton buttonPartials;

    public MainScreen() {
        var currentUser = Session.getCurrentUser();
        MainPanel = new JPanel();
        MainPanel.setLayout(new BoxLayout(MainPanel, BoxLayout.Y_AXIS));

        userLabel = new JLabel("Hello, " + currentUser.getName() + "!", SwingConstants.CENTER);

        MainPanel.add(userLabel);

        roleLabel = new JLabel();
        roleLabel.setEnabled(true);
        roleLabel.setText(currentUser.getRole());
        MainPanel.add(roleLabel);

        welcomeLabel = new JLabel();
        welcomeLabel.setText("Select an option");
        MainPanel.add(welcomeLabel);

        ButtonsPanel = new JPanel();
        ButtonsPanel.setLayout(new GridLayout(0, 1));
        MainPanel.add(ButtonsPanel);

        buttonChangePassword = new JButton();
        buttonChangePassword.setText("Change Password");
        ButtonsPanel.add(buttonChangePassword);

        buttonChangeUserRoles = new JButton();
        buttonChangeUserRoles.setText("Change User Roles");
        ButtonsPanel.add(buttonChangeUserRoles);

        buttonAddConpetition = new JButton();
        buttonAddConpetition.setText("Add Competition");
        ButtonsPanel.add(buttonAddConpetition);

        buttonPartials = new JButton();
        buttonPartials.setText("Manage Competitions");
        ButtonsPanel.add(buttonPartials);

        buttonLogout = new JButton();
        buttonLogout.setText("Logout");
        ButtonsPanel.add(buttonLogout);

        setContentPane(MainPanel);
        setTitle("Main Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        if (!currentUser.getRole().equals("SECRETARY") && !currentUser.getRole().equals("ADMIN")) {
            buttonChangeUserRoles.setVisible(false);
            buttonAddConpetition.setVisible(false);
        }
        setVisible(true);
        buttonChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Session.showChangePassword();
            }
        });
        buttonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Session.logout();
                dispose();
                Session.showLoginPanel();
            }
        });
        buttonChangeUserRoles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Session.showChangeRoles();
            }
        });
        buttonAddConpetition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Session.showCompetitionManager();
            }
        });
        buttonPartials.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Session.showCompetitionEditor();
            }
        });
    }
}
