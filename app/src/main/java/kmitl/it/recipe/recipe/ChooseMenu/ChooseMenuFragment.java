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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Menu;

public class ChooseMenuFragment extends Fragment {

    private ViewPager viewpager;
    private LinearLayout liner;
    private ChooseSlideAdapter myadapter;

    //keep menu
    private String category;
    private String menuName;
    private ArrayList<Menu> _menuList;

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

        getObjOnFirebase();
        controlPath();
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
        _menuAdapter.clear();

    }

    private void getObjOnFirebase(){

        showListView();

        category = "ต้ม - แกง";
        menuName = "ต้มยำจุ้ง";

        //get data
        _fbfs.collection("Menu")
                .document(category)
                .collection("foodname")
                .document("foodname")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        documentSnapshot.getData();
                        _menuList.add(documentSnapshot.toObject(Menu.class));
                        Log.d("CHOOSE_MENU", "SUCCESS _menu list : "+_menuList);
                        _menuAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("CHOOSE_MENU", "Get data from firebase FAILED!!");
            }
        });


    }
}