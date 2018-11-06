package kmitl.it.recipe.recipe;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private NavigationView nv;
    FirebaseAuth _auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _auth = FirebaseAuth.getInstance();
        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.open,R.string.close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view);
//        TextView _profile = findViewById(R.id.nav_menu_profile);
//        if (_auth.getCurrentUser() != null) {
//            _profile.setText(_auth.getCurrentUser().getEmail());
//        }

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.myprofile){
                    if (_auth.getCurrentUser() != null){
                        Toast.makeText(MainActivity.this, "ALREADY_LOG_IN", Toast.LENGTH_SHORT).show();
                        Log.d("NAV_MENU", "ALREADY_LOG_IN");
                    }
                    else {
                        Toast.makeText(MainActivity.this, "MYPROFILE", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();
                        Log.d("NAV_MENU", "GOTO PROFILE");
                    }
                    dl.closeDrawers();
                }
                else if (id == R.id.nav_menu_category){
                    Toast.makeText(MainActivity.this,"CATEGORY",Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).commit();
                    Log.d("NAV_MENU", "GOTO CATEGORY");
                    dl.closeDrawers();
                }
                else if (id == R.id.editprofile){
                    Toast.makeText(MainActivity.this,"EDITPROFILE",Toast.LENGTH_SHORT).show();
                    Log.d("NAV_MENU", "GOTO EDIT_PROFILE");
                    dl.closeDrawers();
                }
                else if (id == R.id.nav_menu_singout){
                    Log.d("NAV_MENU", "elseif");
                    if (_auth.getCurrentUser() != null){
                        Toast.makeText(MainActivity.this,"ออกจากระบบเรียบร้อยแล้ว",Toast.LENGTH_SHORT).show();
                        Log.d("NAV_MENU", "SING OUT COMPLETE");
                        _auth.signOut();

                    }
                    else {

                        Toast.makeText(MainActivity.this, "ท่านไม่ได้อยู่ในระบบ", Toast.LENGTH_SHORT).show();
                        Log.d("NAV_MENU", "SING OUT BUT NO CURRENT USER");
                    }
                    dl.closeDrawers();
                }



                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new HomeFragment()).commit();      //Set home page(Start page)

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(abdt.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);

    }
}
