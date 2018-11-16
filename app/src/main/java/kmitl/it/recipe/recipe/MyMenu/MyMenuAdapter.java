package kmitl.it.recipe.recipe.MyMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Menu;


public class MyMenuAdapter extends RecyclerView.Adapter<MymenuViewHolder> {
    private List<Menu> _menus;
    private MyMenuItemClickListener myMenuItemClickListener;
    private String profileImg;

    public MyMenuAdapter(List<Menu> _menus,String profileImg, MyMenuItemClickListener myMenuItemClickListener) {
        this._menus = _menus;
        this.myMenuItemClickListener = myMenuItemClickListener;
        this.profileImg = profileImg;
    }

    @Override
    public void onBindViewHolder(@NonNull MymenuViewHolder mymenuViewHolder, int i) {
        Log.d("MyMenuAdapter", "onBindViewHolder");

        Menu _menu = _menus.get(i);
        mymenuViewHolder.setMymenu(_menu);
        mymenuViewHolder._menuTitle.setText(_menu.getMenuName());
        mymenuViewHolder._menuWriter.setText(_menu.getWriter());

        Glide.with(mymenuViewHolder._menuImg.getContext())
                .load(_menu.getProfileMenu())
                .into(mymenuViewHolder._menuImg);
        Glide.with(mymenuViewHolder._writerImg.getContext())
                .load(profileImg)
                .into(mymenuViewHolder._writerImg);

 }

    @Override
    public MymenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_show_menu, viewGroup, false);
        return new MymenuViewHolder(itemView, myMenuItemClickListener);
    }

    @Override
    public int getItemCount() {
        return _menus.size();
    }
}

class MymenuViewHolder extends RecyclerView.ViewHolder {

    private MyMenuItemClickListener myMenuItemClickListener;
    private Menu menu;
    protected ImageView _menuImg , _writerImg;
    protected TextView _menuTitle, _menuWriter;

    private Context ctx;

    public Context getCtx() {
        Log.d("MyMenuAdapter", "getContext");
        return ctx;
    }

    public MymenuViewHolder(View v, MyMenuItemClickListener myMenuItemClickListener) {
        super(v);
        Log.d("MyMenuAdapter", "MymenuViewHolder go on");
        this.myMenuItemClickListener = myMenuItemClickListener;
        ctx = v.getContext();

            _menuImg = v.findViewById(R.id.show_menu_img);
            _menuTitle = v.findViewById(R.id.show_menu_name);
            _menuWriter = v.findViewById(R.id.show_menu_username);
            _writerImg = v.findViewById(R.id.show_menu_profile);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MymenuViewHolder.this.myMenuItemClickListener.onMyMenuItemClick(menu.getMenuName(), menu.getCategory());
            }
        });
    }

    public void setMymenu(Menu menu) {
        this.menu = menu;
    }

}