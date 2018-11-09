package kmitl.it.recipe.recipe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import kmitl.it.recipe.recipe.R;

public class SecondActivity extends AppCompatActivity {

    private static int WELCOME_TIMEOUT = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("SECOND_Ac", "Start fade to Main");
                Intent _welcome = new Intent();
                _welcome.setClass(SecondActivity.this, MainActivity.class);
                startActivity(_welcome);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
                Log.d("SECOND_Ac", "Fade to Main finished");
            }
        }, WELCOME_TIMEOUT);
    }
}
