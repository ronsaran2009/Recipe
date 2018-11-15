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

    public MyMenuAdapter(List<Menu> _menus, MyMenuItemClickListener myMenuItemClickListener){
        Log.d("MyMenuAdapter",String.valueOf(_menus.size()));
        this._menus = _menus;
        this.myMenuItemClickListener = myMenuItemClickListener;
    }


    @Override
    public void onBindViewHolder(@NonNull MymenuViewHolder mymenuViewHolder, int i) {

        Menu _menu = _menus.get(i);
        mymenuViewHolder.setMenu(_menu);
        mymenuViewHolder._menuTitle.setText(_menu.getMenuName());
        Glide.with(mymenuViewHolder.getCtx()).load(_menu.getProfileMenu()).into(mymenuViewHolder._menuImg);
    }


    @Override
    public MymenuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_show_menu, viewGroup, false);
        return new MymenuViewHolder(itemView, myMenuItemClickListener);
    }

    @Override
    public int getItemCount(){
        return _menus.size();
    }
}
class MymenuViewHolder extends RecyclerView.ViewHolder{

    private MyMenuItemClickListener myMenuItemClickListener;
    private Menu menu;
    protected ImageView _menuImg;
    protected TextView _menuTitle;

    private Context ctx;

    public Context getCtx(){
        return ctx;
    }

    public MymenuViewHolder(View v, MyMenuItemClickListener myMenuItemClickListener){
        super(v);
        this.myMenuItemClickListener = myMenuItemClickListener;
        ctx = v.getContext();

        _menuImg = v.findViewById(R.id.show_menu_img);
        _menuTitle = v.findViewById(R.id.show_menu_name);

        v.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MymenuViewHolder.this.myMenuItemClickListener.onMyMenuItemClick(menu.getMenuName());
            }
        });
    }

    public void setMenu(Menu menu){
        this.menu = menu;
    }

}