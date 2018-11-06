package kmitl.it.recipe.recipe;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.open,R.string.close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.myprofile){

                    Toast.makeText(MainActivity.this,"MYPROFILE",Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();
                    dl.closeDrawers();
                }
                else if (id == R.id.nav_menu_register){
                    Toast.makeText(MainActivity.this,"REGISTER",Toast.LENGTH_SHORT).show();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).commit();
                    dl.closeDrawers();
                }
                else if (id == R.id.editprofile){
                    Toast.makeText(MainActivity.this,"EDITPROFILE",Toast.LENGTH_SHORT).show();
                    dl.closeDrawers();
                }


                return true;
            }
        });
//        if (savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();
//
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(abdt.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);

    }
}
