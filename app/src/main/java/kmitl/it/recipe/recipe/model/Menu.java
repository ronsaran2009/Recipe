package kmitl.it.recipe.recipe.model;

import java.sql.Array;
import java.util.ArrayList;

public class Menu {
    private String menuName;
    private ArrayList<String> step;
    private ArrayList<String> picture;
    private String ingredient;
    private String description;
    private String category;
    private String time;
    private String profileMenu;
    private String profileUser;

    String imgUser;
    String writer;
    String link;

    public Menu(){

    }

    public Menu(String menuName, ArrayList<String> picture) {
        this.menuName = menuName;
        this.picture =picture;
    }

    public Menu(String profileMenu, String menuName, String description, String category, String time, String ingredient, String writer, ArrayList<String> step, String link, String imgUser) {
        this.profileMenu = profileMenu;
        this.menuName = menuName;
        this.description = description;
        this.category = category;
        this.time = time;
        this.ingredient = ingredient;
        this.writer = writer;
        this.step = step;
        this.link = link;
        this.imgUser = imgUser;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public ArrayList<String> getstep() {
        return step;
    }

    public void setstep(ArrayList<String> step) {
        this.step = step;
    }

    public ArrayList<String> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList<String> picture) {
        this.picture = picture;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileMenu() {
        return profileMenu;
    }

    public void setProfileMenu(String profileMenu) {
        this.profileMenu = profileMenu;
    }

    public String getProfileUser() {
        return profileUser;
    }

    public void setProfileUser(String profileMenu) {
        this.profileUser = profileUser;
    }

    public String getWriter() { return writer; }

    public void setWriter(String writer) { this.writer = writer; }
}
