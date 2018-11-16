package kmitl.it.recipe.recipe.Register;

public class User {
    String email;
    String displayname;
    String pictureUser;

    public User(){ }

    public User(String email, String displayname, String pictureUser){
        this.email = email;
        this.displayname = displayname;
        this.pictureUser = pictureUser;
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

    public String getPictureUser() { return pictureUser; }

    public void setPictureUser(String pictureUser) { this.pictureUser = pictureUser; }
}
