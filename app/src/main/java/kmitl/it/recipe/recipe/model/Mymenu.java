package kmitl.it.recipe.recipe.model;

public class Mymenu {
    String menu;
    String type;
    String date;
    String writer;

    public Mymenu() { }

    public Mymenu(String menu) {
        this.menu = menu;
    }

    public Mymenu(String menu, String type, String date, String writer) {
        this.menu = menu;
        this.type = type;
        this.date = date;
        this.writer = writer;

    }
    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }


}
