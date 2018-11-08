package kmitl.it.recipe.recipe;

import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    ArrayList<String> _menu = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("HOME");


        _menu.add("ข้่วผัดหมู");
        _menu.add("กระเพราหมู");
        _menu.add("สุกกี้แห้ง");
        _menu.add("ข้าวป่าว?");
        _menu.add("อะไรก็ได้");


        final ArrayAdapter<String> _menuAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                _menu
        );
        ListView _menuList = getView().findViewById(R.id.menu_list);
        _menuList.setAdapter(_menuAdapter);
        _menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Log.d("text" , "Click on  " + _menu.get(i));

                _menuAdapter.notifyDataSetChanged();
                if (_menu.get(i).equals("ข้่วผัดหมู")){
                    Log.d("USER", "GOTO ข้่วผัดหมูไม่ใส่ข้าว");
                    Toast.makeText(
                            getActivity(),
                            "ข้่วผัดหมูไม่ใส่ข้าว",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else if (_menu.get(i).equals("กระเพราหมู")){
                    Log.d("USER", "GOTO กระเพราหมูไม่ใส่หมูใส่ไก่แทน");
                    Toast.makeText(
                            getActivity(),
                            "กระเพราหมูไม่ใส่หมูใส่ไก่แทน",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else if (_menu.get(i).equals("สุกกี้แห้ง")){
                    Log.d("USER", "GOTO สุกกี้แห้งใส่น้ำ");
                    Toast.makeText(
                            getActivity(),
                            "สุกกี้แห้งใส่น้ำ",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else if (_menu.get(i).equals("ข้าวป่าว?")){
                    Log.d("USER", "GOTO ข้าวป่าว?");
                    Toast.makeText(
                            getActivity(),
                            "ข้าวป่าว? ไม่รู้",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else if (_menu.get(i).equals("อะไรก็ได้")){
                    Log.d("USER", "GOTO อะไรก็ได้");
                    Toast.makeText(
                            getActivity(),
                            "อะไรก็ได้",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }
}
