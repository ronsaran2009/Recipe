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

import java.util.List;

import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Mymenu;


public class MyMenuAdapter extends RecyclerView.Adapter<MymenuViewHolder> {
    private List<Mymenu> _menus;
    private MyMenuItemClickListener myMenuItemClickListener;


    public MyMenuAdapter(List<Mymenu> _menus, MyMenuItemClickListener myMenuItemClickListener) {
        this._menus = _menus;
        this.myMenuItemClickListener = myMenuItemClickListener;
    }


    @Override
    public void onBindViewHolder(@NonNull MymenuViewHolder mymenuViewHolder, int i) {
        Log.d("MyMenuAdapter", "onBindViewHolder");


        Mymenu _menu = _menus.get(i);
        mymenuViewHolder.setMymenu(_menu);
        mymenuViewHolder._menuTitle.setText(_menu.getMenu());
        mymenuViewHolder._menuWriter.setText(_menu.getWriter());

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
    private Mymenu menu;
    protected ImageView _menuImg;
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


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MymenuViewHolder.this.myMenuItemClickListener.onMyMenuItemClick(menu.getMenu());
                Log.d("MyMenuAdapter", "setOnClickListener Menu "+ menu.getMenu());
            }
        });
    }

    public void setMymenu(Mymenu menu) {
        this.menu = menu;
        Log.d("MyMenuAdapter", "setMymenu Menu "+ menu.getMenu());
    }

}