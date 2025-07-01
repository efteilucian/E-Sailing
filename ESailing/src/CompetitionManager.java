import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class CompetitionManager {
    private static ArrayList<Competition> competitionList = new ArrayList<Competition>();

    public static void removeCompetition(Competition comp){
        String currentLine;
        String[] data;
        StringBuilder textToKeep= new StringBuilder();
        try{
            FileReader fr = new FileReader("textAllCompetitions.txt");
            BufferedReader br = new BufferedReader(fr);

            while((currentLine = br.readLine())!=null){
                data = currentLine.split(",");
                if(!data[0].equals(comp.getName())){
                    textToKeep.append(currentLine).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String finalText = textToKeep.toString();
        try{
            new FileWriter("textAllCompetitions.txt", false);
            FileWriter fw = new FileWriter("textAllCompetitions.txt", true);
            fw.write(finalText);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Competition> getCompetitionList() {
        competitionList.clear();
        String currentLine;
        String[] data;

        try{
            FileReader fr = new FileReader("textAllCompetitions.txt");
            BufferedReader br = new BufferedReader(fr);

            while((currentLine = br.readLine())!=null){
                data = currentLine.split(",");
                Competition newComp = new Competition(data[0],data[1],data[2]);
                competitionList.add(newComp);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return competitionList;
    }

    public static void createCompetition(String name, String organizer, String location) throws IOException {
        Competition newComp = new Competition(name, organizer, location);
        if (!checkCompetition(newComp)){
            if(!name.isEmpty() && !organizer.isEmpty() && !location.isEmpty()){
                addCompetition(newComp);
                try{
                    File file = new File("Partials", name+"Partial.txt");
                    if(!file.exists()){
                        file.createNewFile();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                JOptionPane.showMessageDialog(null, "Competition created successfully!");
            }else{
                JOptionPane.showMessageDialog(null, "Error! Competition fields must not be empty!");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Error! Competition already exists!");
        }
    }

    private static void addCompetition(Competition comp) throws IOException {
        competitionList.add(comp);
        FileWriter writer = new FileWriter("textAllCompetitions.txt", true);
        try{
            writer.write(comp.getName()+","+comp.getOrganizer()+","+comp.getLocation());
            writer.write(System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkCompetition(Competition comp){
        String currentLine;
        String[] data;
        try{
            FileReader fr = new FileReader("textAllCompetitions.txt");
            BufferedReader br = new BufferedReader(fr);
            while((currentLine=br.readLine())!=null){
                data = currentLine.split(",");
                if(data[0].equals(comp.getName())){
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public static void printCompetitions(){
        var compList = getCompetitionList();
        for(Competition i : compList){
            System.out.println(i.getName());
        }
    }

}
