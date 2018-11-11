package kmitl.it.recipe.recipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import kmitl.it.recipe.recipe.Menu.MenuFragment;

public class CategoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        getActivity().setTitle("CATEGORY");
        clickImg();

    }

    void clickImg (){
        ImageButton cat_boil = getView().findViewById(R.id.cat_boiled);
        cat_boil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new RecipeFragment())
//                        .replace(R.id.main_view, new MenuFragment())
                        .commit();
                Toast.makeText(
                        getActivity(),"GOTO HOW TO COOK", Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
