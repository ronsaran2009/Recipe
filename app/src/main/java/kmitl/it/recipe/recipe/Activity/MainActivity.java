package kmitl.it.recipe.recipe.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import kmitl.it.recipe.recipe.ChooseMenu.ChooseMenuFragment;
import kmitl.it.recipe.recipe.LoginFragment;
import kmitl.it.recipe.recipe.MyMenu.MyMenuFragment;
import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.ChooseMenu.TabAdapter;

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
                goToNextPage();
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

    private void goToNextPage() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_out, R.anim.fade_in)
                .replace(R.id.main_view, new LoginFragment())
                .commit();
        Log.d("Main", "Goto home fragment");

    }

//    private TabAdapter tabAdapter;
//    private TabLayout tabLayout;
//    private ViewPager viewPager;

    private void checkSelectNavigation() {
        NavigationView nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (_auth.getCurrentUser() == null){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.fade_out, R.anim.fade_in)
                            .replace(R.id.main_view, new LoginFragment())
                            .commit();
                    _drawMain.closeDrawers();
                }else {
                    if (id == R.id.nav_menu_category) {
                        Toast.makeText(MainActivity.this, "CATEGORY", Toast.LENGTH_SHORT).show();

//                        setContentView(R.layout.category_main);
//
//                        viewPager = findViewById(R.id.viewPager);
//                        tabLayout = findViewById(R.id.tabLayout);
//
//                        tabAdapter = new TabAdapter(getSupportFragmentManager(),viewPager,tabLayout);
//
//                        tabAdapter.addFragment( " ต้ม - แกง ");
//                        tabAdapter.addFragment( " ผัด - ทอด ");
//                        tabAdapter.addFragment( " อบ - ตุ๋น ");
//                        tabAdapter.addFragment( " ปิ้ง - ย่าง ");
//                        tabAdapter.addFragment( " อาหารจานเดียว");
//
//                        viewPager.setAdapter(tabAdapter);

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new ChooseMenuFragment())
                                .addToBackStack(null)
                                .commit();

                        Log.d("NAV_MENU", "GOTO_CATEGORY mPgaer "+ String.valueOf(item));
                        _drawMain.closeDrawers();


                    }else if (id == R.id.nav_menu_mymenu) {
                        // Toast.makeText(MainActivity.this, "MYMENU", Toast.LENGTH_SHORT).show();

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new MyMenuFragment())
                                .addToBackStack(null)
                                .commit();
                        Log.d("NAV_MENU", "GOTO MYMENU");

                        _drawMain.closeDrawers();
                    } else if (id == R.id.nav_menu_addmenu) {
                        Toast.makeText(MainActivity.this, "ADDMENU", Toast.LENGTH_SHORT).show();

                        //Category Tabs

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new kmitl.it.recipe.recipe.AddMenu.AddMenuFragment())
                                .addToBackStack(null)
                                .commit();

                        Log.d("NAV_MENU", "GOTO_ADDMENU");
                        _drawMain.closeDrawers();
                    } else if (id == R.id.nav_menu_singout) {
                        Log.d("NAV_MENU", "elseif");
                        Toast.makeText(MainActivity.this, "ออกจากระบบเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();
                        Log.d("NAV_MENU", "SING OUT COMPLETE");
                        TextView name = findViewById(R.id.nav_head_text);
                        name.setText("Guest");
                        _auth.signOut();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.anim.fade_out, R.anim.fade_in)
                                .replace(R.id.main_view, new LoginFragment())
                                .commit();

                        _drawMain.closeDrawers();
                    }
                }return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("NAV_MENU", String.valueOf(item));

        if (_abdt.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}