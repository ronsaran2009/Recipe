package kmitl.it.recipe.recipe.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import kmitl.it.recipe.recipe.CategoryFragment;
import kmitl.it.recipe.recipe.HomeFragment;
import kmitl.it.recipe.recipe.LoginFragment;
import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.Tab1Fragment;
import kmitl.it.recipe.recipe.TabAdapter;

public class MainActivity extends AppCompatActivity {

    //for fade in & fade out
    private int WELCOME_TIMEOUT = 0;
    protected static boolean COUNT = true;

    //Navigation
    private FirebaseAuth _auth;
    private DrawerLayout _drawMain;
    private ActionBarDrawerToggle _abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fadeInOut(savedInstanceState);

    }

    private void fadeInOut(Bundle savedInstanceState) {
        if (COUNT) {
            COUNT = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent welcome = new Intent();
                    welcome.setClass(MainActivity.this, SecondActivity.class);
                    startActivity(welcome);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }, WELCOME_TIMEOUT);
            Log.d("Main", "fade | count : " + COUNT);
        } else {
            if (savedInstanceState == null) {
                callNavigationBar();
                checkSelectNavigation();
                goToHome();
            }
        }
    }

    private void callNavigationBar() {
        _auth = FirebaseAuth.getInstance();
        _drawMain = findViewById(R.id.drawMain);
        _abdt = new ActionBarDrawerToggle(this, _drawMain, R.string.open, R.string.close);
        _abdt.setDrawerIndicatorEnabled(true);

        _drawMain.addDrawerListener(_abdt);
        _abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void goToHome() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_out, R.anim.fade_in)
                .replace(R.id.main_view, new HomeFragment())
                .commit();
        Log.d("Main", "Goto home fragment");

    }

    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private void checkSelectNavigation() {
        NavigationView nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();


                if (id == R.id.nav_menu_mymenu) {
                    if (_auth.getCurrentUser() != null) {
                        Toast.makeText(MainActivity.this, "ALREADY_LOG_IN", Toast.LENGTH_SHORT).show();
                        Log.d("NAV_MENU", "ALREADY_LOG_IN");
                    } else {
                        Toast.makeText(MainActivity.this, "MYPROFILE", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.main_view, new LoginFragment())
                                .commit();
                        Log.d("NAV_MENU", "GOTO PROFILE");
                    }
                    _drawMain.closeDrawers();
                } else if (id == R.id.nav_menu_addmenu) {
                    Toast.makeText(MainActivity.this, "CATEGORY", Toast.LENGTH_SHORT).show();

                    //Category Tabs
                    setContentView(R.layout.category_main);
                    viewPager = findViewById(R.id.viewPager);
                    tabLayout = findViewById(R.id.tabLayout);
                    tabAdapter = new TabAdapter(getSupportFragmentManager());
                    tabAdapter.addFragment(new Tab1Fragment(), " ต้ม - แกง ");
                    tabAdapter.addFragment(new Tab1Fragment(), " ผัด - ทอด ");
                    tabAdapter.addFragment(new Tab1Fragment(), " อบ - ตุ๋น ");
                    tabAdapter.addFragment(new Tab1Fragment(), " ปิ้ง - ย่าง ");
                    tabAdapter.addFragment(new Tab1Fragment(), " อาหารจานเดียว");
                    viewPager.setAdapter(tabAdapter);
                    tabLayout.setupWithViewPager(viewPager);

                    Log.d("NAV_MENU", "GOTO CATEGORY");
                    _drawMain.closeDrawers();
                }
//                else if (id == R.id.editprofile){
//                    Toast.makeText(MainActivity.this,"EDITPROFILE",Toast.LENGTH_SHORT).show();
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .addToBackStack(null)
//                            .replace(R.id.main_view, new FavoriteFragment())
//                            .commit();
//                    Log.d("NAV_MENU", "GOTO EDIT_PROFILE");
//                    _drawMain.closeDrawers();
//                }
                else if (id == R.id.nav_menu_singout) {
                    Log.d("NAV_MENU", "elseif");
                    if (_auth.getCurrentUser() != null) {
                        Toast.makeText(MainActivity.this, "ออกจากระบบเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
                        Log.d("NAV_MENU", "SING OUT COMPLETE");
                        _auth.signOut();
                        TextView _profile = findViewById(R.id.nav_head_text);
                        _profile.setText("Guest");
                    } else {
                        Toast.makeText(MainActivity.this, "ท่านไม่ได้อยู่ในระบบ", Toast.LENGTH_SHORT).show();
                        Log.d("NAV_MENU", "SING OUT BUT NO CURRENT USER");
                    }
                    _drawMain.closeDrawers();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (_abdt.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
