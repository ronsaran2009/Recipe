package kmitl.it.recipe.recipe.MyMenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import kmitl.it.recipe.recipe.Activity.MainActivity;
import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Menu;

public class MyMenuFragment extends Fragment
        implements MyMenuItemClickListener{

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;


    private ArrayList<Menu> _menu = new ArrayList<>();



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recyclerView = getView().findViewById(R.id.mymenu_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        getDataResults();
    }


    @Override
    public void onMyMenuItemClick(String _recipeId) {
        Toast.makeText(getActivity(), _recipeId, Toast.LENGTH_SHORT).show();
    }

    public void getDataResults() {
        final ArrayList<Menu> _menus = new ArrayList<Menu>();

        _menus.add(new Menu("ต้มยำกุ้ง"));
        _menus.add(new Menu("น้ำปลา"));

        Log.d("MyMenuFragment", String.valueOf(_menus.size()));
        MyMenuAdapter myMenuAdapter = new MyMenuAdapter(_menus, MyMenuFragment.this);
        Log.d("MyMenuFragment", String.valueOf(myMenuAdapter.getItemCount()));
        recyclerView.setAdapter(myMenuAdapter);
    }
}
