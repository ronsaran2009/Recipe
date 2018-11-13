package kmitl.it.recipe.recipe;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    //Recycle View
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    //keep menu
    private ArrayList<String> _menu = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("HOME");


        addItem();
        loadItemRecommend();
    }

    private void addItem(){
        _menu.add("ข้าวผัดหมู");
        _menu.add("กระเพราหมู");
        _menu.add("สุกกี้แห้ง");
        _menu.add("ข้าวป่าว?");
        _menu.add("อะไรก็ได้1");
        _menu.add("อะไรก็ได้2");
        _menu.add("อะไรก็ได้3");
        _menu.add("อะไรก็ได้4");
        _menu.add("อะไรก็ได้5");
        _menu.add("อะไรก็ได้6");
        _menu.add("อะไรก็ได้7");
        _menu.add("อะไรก็ได้8");
        _menu.add("อะไรก็ได้9");
    }

    private void loadItemRecommend(){
        mRecyclerView = getView().findViewById(R.id.recycle_home);
        Log.d("HOME", getView().findViewById(R.id.recycle_home).toString());
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(this.getContext(), _menu);
        mRecyclerView.setAdapter(mAdapter);
    }

}
