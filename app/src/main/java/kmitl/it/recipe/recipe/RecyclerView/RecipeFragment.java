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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import kmitl.it.recipe.recipe.ChooseMenu.ChooseMenuFragment;
import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.RecyclerView.MainAdapter;
import kmitl.it.recipe.recipe.model.Menu;

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

    //youtube
    Button youtubeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Recipe");
        getObjOnFirebase();
        initYoutubeBtnPressed();
    }

    void initYoutubeBtnPressed(){
        youtubeBtn = (Button)getView().findViewById(R.id.btnYoutube);
        youtubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new YouTubePlayerSupportFragment())
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    private void getObjOnFirebase(){
        Log.d("Recipe", "get Bundle");

        String menuName = "";
        String menuType = "";

        Bundle b = new Bundle();
        if (b != null){
            menuName = getArguments().getString("myMenuName");
            menuType = getArguments().getString("myMenuType");
            Toast.makeText(getActivity(),menuName+" " +menuType,Toast.LENGTH_SHORT).show();

        }

        //get data
        try {
            _fbfs.collection("Menu")
                    .document(menuType)
                    .collection("menu")
                    .document(menuName)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            documentSnapshot.getData();
                            _menu = documentSnapshot.toObject(Menu.class);
                            //Log.d("RECIPE", "SUCCESS : "+documentSnapshot.toObject(Menu.class).getMenuName());
                            //set data
                            Log.d("RECIPE", "Menu = "+_menu);
                            if(_menu != null){
                                setPage();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("RECIPE", "Get data from firebase FAILED!!");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "ทำการไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
        }
    }

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
        if(_step != null){
            Log.d("RECIPE", "Call Recycler view");
            setRecyclerView();
        }else{
            Log.d("RECIPE", "Step is null");
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_view, new ChooseMenuFragment()).addToBackStack(null).commit();
            Toast.makeText(getActivity(),"ไม่พบข้อมูลขั้นตอนการทำอาหาร",Toast.LENGTH_SHORT).show();
        }

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
