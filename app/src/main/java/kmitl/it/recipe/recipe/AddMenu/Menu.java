package kmitl.it.recipe.recipe.AddMenu;

import android.content.ContentValues;

public class Menu {

    ContentValues _row;

    public Menu() {}

    public void setMenu(String nameFood, String descFood, String typeFood, String timeCook, String ingredient, String pathImg) {
        _row = new ContentValues();

        _row.put("name", nameFood);
        _row.put("description", descFood);
        _row.put("type", typeFood);
        _row.put("time", timeCook);
        _row.put("ingredient", ingredient);
        _row.put("image", pathImg);
    }

    public ContentValues getContent() {
        return _row;
    }
}
