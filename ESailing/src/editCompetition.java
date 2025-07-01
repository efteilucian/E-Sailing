import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class editCompetition extends JFrame {
    private JLabel titleLabel;
    private JPanel MainPanel;
    private JPanel TextPanel;
    private JTextArea competitionTextArea;
    private JButton buttonConfirm;
    private JButton buttonCancel;
    private JPanel ButtonsPanel;
    private JLabel formatLabel;
    private JButton buttonFinal;

    public editCompetition(Competition comp) {
        var currentUser = Session.getCurrentUser();

        MainPanel = new JPanel();
        MainPanel.setLayout(new GridLayout(0, 1));

        TextPanel = new JPanel();
        TextPanel.setLayout(new BoxLayout(TextPanel, BoxLayout.Y_AXIS));
        MainPanel.add(TextPanel);

        titleLabel = new JLabel();
        titleLabel.setText("Edit Competition " + comp.getName());
        TextPanel.add(titleLabel);

        formatLabel = new JLabel();
        formatLabel.setText("Format: Name,Time in seconds");
        TextPanel.add(formatLabel);

        competitionTextArea = new JTextArea();
        competitionTextArea.setText("");
        TextPanel.add(competitionTextArea);

        ButtonsPanel = new JPanel();
        ButtonsPanel.setLayout(new GridLayout());
        MainPanel.add(ButtonsPanel);

        buttonConfirm = new JButton();
        buttonConfirm.setText("Save");
        ButtonsPanel.add(buttonConfirm);

        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        ButtonsPanel.add(buttonCancel);

        buttonFinal = new JButton();
        buttonFinal.setText("Make Final");
        ButtonsPanel.add(buttonFinal);

        setContentPane(MainPanel);
        titleLabel.setText("Edit Competition " + comp.getName());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        String filePath = "Partials\\" + comp.getName() + "Partial.txt";
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            competitionTextArea.read(br, null);
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String areaText = competitionTextArea.getText();
                boolean ok = true;
                for (String line : areaText.split("\\n")) {
                    String[] data;
                    data = line.split(",");
                    if ((!Session.checkIfUserExists(data[0])) && !data[0].isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error! User " + data[0] + " does not exist!");
                        ok = false;
                    }
                    if ((!(Session.checkUserRole(data[0]).equals("ATHLETE"))) && !data[0].isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error! user " + data[0] + " is not an athlete!");
                        ok = false;
                    }
                    if (data.length != 2) {
                        JOptionPane.showMessageDialog(null, "Error! Please respect the format: Name,Time in seconds!");
                        ok = false;
                    } else if (!data[1].matches("-?(0|[1-9]\\d*)")) {
                        JOptionPane.showMessageDialog(null, "Error! " + data[0] + "'s time is not valid!");
                        ok = false;
                    }
                }
                if (ok) {
                    try {
                        new FileWriter(filePath, false).close();
                        FileWriter fw = new FileWriter(filePath, true);
                        fw.write(competitionTextArea.getText());
                        fw.close();
                        JOptionPane.showMessageDialog(null, "Competition edited successfully!");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        buttonFinal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to finalize this competition? This can not be undone!", "?", JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Competition finalized successfully!");
                    dispose();
                    try {
                        List<String> lines = new ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                if (!line.trim().isEmpty()) {
                                    lines.add(line);
                                }
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        lines.sort(Comparator.comparingInt(line -> {
                            try {
                                return Integer.parseInt(line.split(",")[1].trim());
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }));

                        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Final\\" + comp.getName() + "Final.txt"))) {
                            int i = 1;
                            for (String line : lines) {
                                i += 1;
                                String[] data;
                                data = line.split(",");
                                writer.write("#" + i + " " + data[0] + " with a time of " + data[1] + " seconds.");
                                writer.newLine();
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                        File fileToDel = new File(filePath);
                        fileToDel.delete();
                        CompetitionManager.removeCompetition(comp);
                        Session.showCompetitionEditor();
                        dispose();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }


                }
            }
        });
    }
}
