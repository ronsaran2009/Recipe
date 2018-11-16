package kmitl.it.recipe.recipe.Register;

public class User {
    String email;
    String displayname;
    String pic;
    public User(){

    }



    public User(String email, String displayname, String pic){
        this.email = email;
        this.displayname = displayname;
        this.pic = pic;

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
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
