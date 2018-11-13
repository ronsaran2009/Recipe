package kmitl.it.recipe.recipe.Register;

public class User {
    String email;
    String displayname;
    public User(){

    }
    public User(String email, String displayname){
        this.email = email;
        this.displayname = displayname;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
