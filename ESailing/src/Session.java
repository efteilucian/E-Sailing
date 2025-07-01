import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Session {
    private static User currentUser;

    public static void setCurrentUser(User user) {
        currentUser = user;
        System.out.println("Welcome user: "+user.getUsername()+" you have the role of: "+user.getRole());
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean checkIfUserExists(String name){
        String currentLine;
        String[] data;

        try{
            FileReader fr = new FileReader("textLogin.txt");
            BufferedReader br = new BufferedReader(fr);
            while((currentLine = br.readLine())!=null){
                data = currentLine.split(",");
                if(data[0].equals(name)){
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return false;
    }

    public static String checkUserRole(String name){
        String currentLine;
        String[] data;
        try{
            FileReader fr = new FileReader("textLogin.txt");
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine())!=null){
                data = currentLine.split(",");
                if(data[0].equals(name)){
                    return data[3];
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "ERROR";
    }

    public static void showLoginPanel(){
        var loginPanel = new LoginScreen();
    }

    public static void showMainPanel(){
        var mainPanel = new MainScreen();
    }

    public static void showChangePassword(){
        var passwordPanel = new changePassword();
    }

    public static void showChangeRoles(){
        var rolesPanel = new changeRole();
    }

    public static void showCompetitionManager(){
        var compPanel = new addCompetition();
    }

    public static void showCompetitionEditor(){
        var compPanel = new manageCompetitions();
    }

    public static void showEditor(Competition comp){
        var compPanel = new editCompetition(comp);
    }
}
