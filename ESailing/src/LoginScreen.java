import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LoginScreen extends JFrame {

    private JTextField usernameField1;
    private JPasswordField passwordField1;
    private JTextField nameField1;

    public LoginScreen() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0, 1));

        JLabel nameLabel = new JLabel();
        nameLabel.setText("Name");
        mainPanel.add(nameLabel);
        nameField1 = new JTextField();
        mainPanel.add(nameField1);

        JLabel usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        mainPanel.add(usernameLabel);
        usernameField1 = new JTextField();
        mainPanel.add(usernameField1);

        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("Password");
        mainPanel.add(passwordLabel);
        passwordField1 = new JPasswordField();
        mainPanel.add(passwordField1);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonsPanel);

        JButton login = new JButton();
        login.setEnabled(true);
        login.setText("Login");
        buttonsPanel.add(login, BorderLayout.WEST);

        JButton register = new JButton();
        register.setText("Register");
        buttonsPanel.add(register, BorderLayout.EAST);

        setContentPane(mainPanel);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField1.getText();
                String username = usernameField1.getText();
                String password = passwordField1.getText();
                if (checkUser(name, username, password)) {
                    JOptionPane.showMessageDialog(null, "Welcome!");
                    var newUser = new User(name, username, password, checkUserRole(name));
                    Session.setCurrentUser(newUser);
                    dispose();
                    Session.showMainPanel();
                } else {
                    JOptionPane.showMessageDialog(null, "Error! User does not exist. Register to continue.");
                }
            }
        });
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField1.getText();
                String username = usernameField1.getText();
                String password = passwordField1.getText();

                FileWriter writer = null;
                try {
                    if (!name.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                        if (checkUserRegister(name)) {
                            if (password.length() > 5) {
                                writer = new FileWriter("textLogin.txt", true);
                                writer.write(name + "," + username + "," + password + ",VISITOR");
                                writer.write(System.lineSeparator());
                                writer.close();
                                JOptionPane.showMessageDialog(null, "Registered successfully! Please log in.");
                                nameField1.setText("");
                                usernameField1.setText("");
                                passwordField1.setText("");
                            } else {
                                JOptionPane.showMessageDialog(null, "Error! Password must be at least 6 characters long!");
                            }

                        } else {
                            JOptionPane.showMessageDialog(null, "Error! User with this name already exists!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error! All fields must NOT be empty to register!");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public String checkUserRole(String name) {
        String currentLine;
        String[] data;
        try {
            FileReader fr = new FileReader("textLogin.txt");
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                data = currentLine.split(",");
                if (data[0].equals(name)) {
                    return data[3];
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "ERROR";
    }

    public boolean checkUser(String name, String username, String password) {
        String currentLine;
        String[] data;

        try {
            FileReader fr = new FileReader("textLogin.txt");
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                data = currentLine.split(",");
                if (data[0].equals(name) && data[1].equals(username) && data[2].equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return false;
    }

    public boolean checkUserRegister(String name) {
        String currentLine;
        String[] data;

        try {
            FileReader fr = new FileReader("textLogin.txt");
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                data = currentLine.split(",");
                if (data[0].equals(name)) {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return true;
    }
}
