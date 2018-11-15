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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Menu;
import kmitl.it.recipe.recipe.model.Mymenu;

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

    private void getObjOnFirebase(){

        category = "อบ - ตุ๋น";
//        menuName = "ต้มยำกุ้ง";

        //get data
        _fbfs.collection("Menu")
                .document(category)
                .collection("menu")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                            _menuList.add(new Menu(doc.get("menuName").toString(), doc.get("writer").toString()));

                            ListView _menuView = getView().findViewById(R.id.choose_menu_list);
                            _menuAdapter = new ChooseMenuAdapter(getActivity(), R.layout.item_show_menu, _menuList);
                            _menuView.setAdapter(_menuAdapter);
                        }
                    }
                });


                /*.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> list = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                        _menuList.add((Menu) document.get("menu"));
                        _menuAdapter = new ChooseMenuAdapter(getActivity(), R.layout.item_show_menu, _menuList);
                        ListView choos_menu_list = getView().findViewById(R.id.choose_menu_list);
                        choos_menu_list.setAdapter(_menuAdapter);
                    }
                    if (list.isEmpty()) {
                        Log.d("CHOOSE_MENU", "MENU_EMPTY");
                    }
                } else {
                    Log.d("CHOOSE_MENU", "Error getting documents: ", task.getException());
                }
            }
        }); */
    }
}