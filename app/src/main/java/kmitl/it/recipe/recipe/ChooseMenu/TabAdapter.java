package kmitl.it.recipe.recipe.ChooseMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kmitl.it.recipe.recipe.Activity.MainActivity;
import kmitl.it.recipe.recipe.ChooseMenu.ChooseMenuFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    protected static int menu_type;
    private final List<String> pin = new ArrayList<>();


    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    ChooseMenuAdapter menuAdapter;
    public TabAdapter(FragmentManager fm , ViewPager vp, TabLayout tb) {
        super(fm);
        tb.setupWithViewPager(vp);

        tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                menu_type = tab.getPosition();
               // menuAdapter.clear();

                Log.d("NAV_MENU", "MENU :"+ menu_type);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    public void addFragment(String Title) {
        Fragment fragment = new ChooseMenuFragment();




        mFragmentList.add(fragment);
        mFragmentTitleList.add(Title);

        Log.d("Tapp",""+getCount()+" "+ getItem(0)+ " "+getPageTitle(0));



    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
