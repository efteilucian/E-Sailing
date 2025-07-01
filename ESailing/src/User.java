public class User {
    private String name, username, password, role;
    public User(String name, String username, String password, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getRole(){
        return role;
    }

    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }


}
