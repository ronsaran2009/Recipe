package kmitl.it.recipe.recipe.ChooseMenu;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Menu;

public class ChooseMenuFragment extends Fragment {

    private ViewPager viewpager;
    private LinearLayout liner;
    private ChooseSlideAdapter myadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showListView();
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

    ArrayList<Menu> _menu = new ArrayList<>();

    private void showListView() {

        ListView choos_menu_list = getView().findViewById(R.id.choose_menu_list);
        final ChooseMenuAdapter _menuAdapter = new ChooseMenuAdapter(getActivity(), R.layout.item_show_menu, _menu);
        choos_menu_list.setAdapter(_menuAdapter);
        _menuAdapter.clear();

    }
}