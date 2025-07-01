import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class manageCompetitions extends JFrame {
    private JPanel MainPanel = new JPanel();
    private ArrayList<Button> buttons = new ArrayList<Button>();
    public manageCompetitions(){
        var currentUser = Session.getCurrentUser();
        MainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        setContentPane(MainPanel);
        setTitle("Edit Competitions");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300,500);
        setLocationRelativeTo(null);
        setVisible(true);
        loadButtons();
    }
    public void loadButtons(){
        buttons.clear();
        for(Component c : MainPanel.getComponents()){
            if(c instanceof Button){
                MainPanel.remove(c);
            }
        }

        for(Competition i : CompetitionManager.getCompetitionList()){
            Button newButton = new Button("Edit competition "+i.getName());
            buttons.add(newButton);
            MainPanel.add(newButton);
            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Session.showEditor(i);
                    dispose();
                }
            });
        }
    }
}
