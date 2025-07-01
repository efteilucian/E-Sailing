import java.util.ArrayList;

public class Competition {
    private String name;
    private String organizer;
    private String location;
    private ArrayList<User> athletes = new ArrayList<User>();
    public Competition(String name, String organizer, String location){
        this.name = name;
        this.organizer = organizer;
        this.location = location;
    }

    public void addAthlete(User athlete){
        athletes.add(athlete);
    }

    public void removeAthlete(User athlete){
        athletes.remove(athlete);
    }

    public String getName(){
        return name;
    }

    public String getOrganizer(){
        return organizer;
    }

    public String getLocation(){
        return location;
    }
}
