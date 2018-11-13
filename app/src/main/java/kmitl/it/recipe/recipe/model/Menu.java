package kmitl.it.recipe.recipe.model;

import java.sql.Array;

public class Menu {
    private String menuName;
    private String step;
    private  int picture;
    private String ingredient;
    private String description;
    private String category;
    private String time;
    private int profileMenu;

    public Menu(){

    }
    public Menu(String menuName, String step, int picture, String ingredient, String description, String category, String time, int profileMenu) {
        this.menuName = menuName;
        this.step = step;
        this.picture = picture;
        this.ingredient = ingredient;
        this.description = description;
        this.category = category;
        this.time = time;
        this.profileMenu = profileMenu;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getstep() {
        return step;
    }

    public void setstep(String step) {
        this.step = step;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
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

    public int getProfileMenu() {
        return profileMenu;
    }

    public void setProfileMenu(int profileMenu) {
        this.profileMenu = profileMenu;
    }
}
