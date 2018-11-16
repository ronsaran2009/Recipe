package kmitl.it.recipe.recipe.RecyclerView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.RecyclerView.MainAdapter;
import kmitl.it.recipe.recipe.Register.User;
import kmitl.it.recipe.recipe.model.Menu;
import kmitl.it.recipe.recipe.model.Mymenu;

public class RecipeFragment extends Fragment {

    //Recycle View
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    //keep menu
    private Menu _menu;
    private ArrayList<String> _step = new ArrayList<>();
    private String category;
    private String menuName;
    private ImageView profileImg;

    //firebase
    private FirebaseFirestore _fbfs = FirebaseFirestore.getInstance();

    FirebaseAuth _auth ;

    Mymenu mymenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Recipe");
        _auth = FirebaseAuth.getInstance();
        getObjOnFirebase();
    }

    private void getObjOnFirebase(){
//get data

        _fbfs.collection("Menu")
                .document(mymenu.getType())
                .collection("menu")
                .document(mymenu.getMenu())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    _menu = task.getResult().toObject(Menu.class);
                    TextView topic = getActivity().findViewById(R.id.menu_topic_recipe);
                    topic.setText(_menu.getMenuName());
                    TextView ing = getActivity().findViewById(R.id.ingredient_item_recipe);
                    ing.setText(_menu.getIngredient());
                    TextView step = getActivity().findViewById(R.id.step_recipe);
                    step.setText(_menu.get_step());

                    Log.d("RECIPE", "Menu = " + _menu.getMenuName());
            }



    }});}

    private void setPage(){
        //set name Menu
        ((TextView) getView().findViewById(R.id.menu_topic_recipe)).setText(_menu.getMenuName());

        //set prepare item
        ((TextView) getView().findViewById(R.id.ingredient_item_recipe)).setText(_menu.getIngredient());

        //set Image
        profileImg = getView().findViewById(R.id.profile_image_menu_recipe);
        Glide.with(getContext()).load(_menu.getProfileMenu()).into(profileImg);

        //set step
        _step = _menu.getstep();
        setRecyclerView();
    }

    private void setRecyclerView(){
        mRecyclerView = getView().findViewById(R.id.recycle_recipe);
        Log.d("RECIPE", "Prepare set : "+getView().findViewById(R.id.recycle_recipe).toString());
        if(mRecyclerView != null){
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MainAdapter(this.getContext(), _step);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            Log.d("RECIPE", "RecyclerView has empty");
        }

    }
}
