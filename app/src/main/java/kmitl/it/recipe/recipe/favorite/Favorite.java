package kmitl.it.recipe.recipe.favorite;

public class Favorite {

    String menu;
    String type;
    String img;

    public Favorite(String menu, String type) {
        this.menu = menu;
        this.type = type;
//        this.img = img;
    }

    public String getType() {
        return type;
    }

    public String getImg() {
        return img;
    }

    public String getMenu() {
        return menu;
    }
}
