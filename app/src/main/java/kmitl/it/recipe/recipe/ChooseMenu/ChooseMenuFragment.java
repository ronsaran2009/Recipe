package kmitl.it.recipe.recipe.ChooseMenu;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Menu;
import kmitl.it.recipe.recipe.model.Mymenu;

import static android.content.Intent.getIntent;

public class ChooseMenuFragment extends Fragment {

    private ViewPager viewpager;
    private LinearLayout liner;
    private ChooseSlideAdapter myadapter;

    //keep menu
    private String menuName, cate;

    private ArrayList<Menu> _menuList = new ArrayList<>();

    //firebase
    private FirebaseFirestore _fbfs = FirebaseFirestore.getInstance();

    ChooseMenuAdapter _menuAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _menuList = new ArrayList<>();

        controlPath();


        getObjOnFirebase();
    }


    private void showViewPager(ViewPager.OnPageChangeListener _menuListener) {
        viewpager = getView().findViewById(R.id.choose_view_pager);
        viewpager = getView().findViewById(R.id.choose_view_pager);
        viewpager = getView().findViewById(R.id.choose_view_pager);
        liner = getView().findViewById(R.id.choose_dot);

        myadapter = new ChooseSlideAdapter(getActivity());
        viewpager.setAdapter(myadapter);

        viewpager.addOnPageChangeListener(_menuListener);

    }

    private void controlPath() {

        ViewPager.OnPageChangeListener _menuListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // mCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }

        };
        showViewPager(_menuListener);
    }

    private void showListView() {

        Log.d("CHOOSE_MENU", "list : "+_menuList);
        ListView choos_menu_list = getView().findViewById(R.id.choose_menu_list);
        _menuAdapter = new ChooseMenuAdapter(getActivity(), R.layout.item_show_menu, _menuList);
        Log.d("CHOOSE_MENU", "list : "+choos_menu_list+"\n Adap: "+_menuAdapter);
        choos_menu_list.setAdapter(_menuAdapter);


    }

    private void getObjOnFirebase(){
        showListView();

        cate = cate();
        Toast.makeText(getActivity(),cate,Toast.LENGTH_SHORT).show();
        Log.d("NAV_MENU", "GOTO_CATEGORY mPgaer "+ cate);

        menuName = "ต้มยำจุ้ง";
        _menuAdapter.clear();
        //get data
        _fbfs.collection("Menu")
                .document(cate)
                .collection("menu")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> list = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                        _menuList.add(document.toObject(Menu.class));
                        ListView _menuView = getView().findViewById(R.id.choose_menu_list);

                        _menuAdapter = new ChooseMenuAdapter(getActivity(), R.layout.item_show_menu, _menuList);
                        _menuView.setAdapter(_menuAdapter);

//                        Log.d("MyMenuFragment", "" + _menuList);
                        Log.d("CHOOSE_MENU", "+" +list.toString());
                    }
                    if (list.isEmpty()) {
                        Log.d("CHOOSE_MENU", "MYMENU_EMPTY");
                    }
                } else {
                    Log.d("MyMenuFragment", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private String cate(){

        int i = TabAdapter.menu_type;

        switch (i) {
            case 0:
                return   "ต้ม - แกง";

            case 1:
                return  "ผัด - ทอด";

            case 2:
                return   "อบ - ตุ๋น";

            case 3:
                return   "ปิ้ง - ย่าง ";

            case 4:
                return   "อาหารจานเดียว";

            default:
                return   "ต้ม - แกง";

        }
    }
}