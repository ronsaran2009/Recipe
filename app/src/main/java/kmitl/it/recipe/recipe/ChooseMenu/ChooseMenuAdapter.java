package kmitl.it.recipe.recipe.ChooseMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import kmitl.it.recipe.recipe.model.Menu;
import kmitl.it.recipe.recipe.R;

public class ChooseMenuAdapter extends ArrayAdapter<Menu> {

    List<Menu> _menu;
    Context context;

    public ChooseMenuAdapter (Context context, int resouce, List<Menu> objects) {
        super(context, resouce, objects);
        Log.d("MENU_ADAP", "call choose menu adapter : menu :"+objects);
        this._menu = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View _menuItem = LayoutInflater.from(context)
                .inflate(R.layout.item_show_menu, parent, false);

        //เรียกตัวแปรจากหน้า item_show_menu
//        TextView _image = _menuItem.findViewById(R.id.show_menu_img);
//        TextView _profile = _menuItem.findViewById(R.id.show_menu_profile);
        TextView _menuName = _menuItem.findViewById(R.id.show_menu_name);
//        TextView _user = _menuItem.findViewById(R.id.show_menu_username);

        //เรียกคลาส Weight เพื่อ get ค่าออกมาแสดงที่ item_show_menu
        Menu _row = _menu.get(position);
        Log.d("MENU_ADAP", "menu in adap : "+_row);
//        _image.setText(_row.getProfileMenu());
//        _profile.setText(_row.getProfileMenu()); //แก้เป็นภาพของคนเขียน
        _menuName.setText(_row.getMenuName());
//        _user.setText(_row.getWriter());
//        _image.setText("Test");
//        _profile.setText("Test"); //แก้เป็นภาพของคนเขียน
//        _menuName.setText("Boil");
//        _user.setText("Aal");

        return _menuItem;
    }
}
