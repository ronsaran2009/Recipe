package kmitl.it.recipe.recipe.ChooseMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kmitl.it.recipe.recipe.model.Menu;
import kmitl.it.recipe.recipe.R;

public class ChooseMenuAdapter extends ArrayAdapter<Menu> {

    List<Menu> _menu;
    Context context;

    public ChooseMenuAdapter(Context context, int resouce, List<Menu> objects) {
        super(context, resouce, objects);
        Log.d("MENU_ADAP", "call choose menu adapter : menu :" + objects);
        this._menu = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View _menuItem = LayoutInflater.from(context)
                .inflate(R.layout.item_show_menu, parent, false);

        ImageView _image = _menuItem.findViewById(R.id.show_menu_img);
        CircleImageView _profile = _menuItem.findViewById(R.id.show_menu_profile);
        TextView _menuName = _menuItem.findViewById(R.id.show_menu_name);
        TextView _user = _menuItem.findViewById(R.id.show_menu_username);

        Menu _row = _menu.get(position);
        Log.d("CHOOSR_MENU", "profile "+_row.getProfileUser());
        Glide.with(getContext()).load(_row.getProfileMenu()).into(_image);
        Glide.with(getContext()).load(_row.getProfileUser()).into(_profile);
        _menuName.setText(_row.getMenuName());
        _user.setText("Written by "+_row.getWriter());

        return _menuItem;
    }
}
