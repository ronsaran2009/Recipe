package kmitl.it.recipe.recipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import kmitl.it.recipe.recipe.RecyclerView.HomeFragment;
import kmitl.it.recipe.recipe.model.Menu;


public class AddMenuFragment extends Fragment {

    ArrayList<String> _step = new ArrayList<>();
    ArrayList<String> _picture = new ArrayList<>();
    Menu _menu;

    FirebaseFirestore _fbfs = FirebaseFirestore.getInstance();
    FirebaseAuth _muth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Add Menu");

        _step.add("1. tom");
        _step.add("2. yumyum");
        _picture.add("https://firebasestorage.googleapis.com/v0/b/recipe-mobileproject.appspot.com/o/%E0%B8%95%E0%B9%89%E0%B8%A1%E0%B8%A2%E0%B8%B3.jpg?alt=media&token=39bd44b0-77f6-4cba-93d5-bf76b08635ac");
        _picture.add("https://firebasestorage.googleapis.com/v0/b/recipe-mobileproject.appspot.com/o/%E0%B8%95%E0%B9%89%E0%B8%A1%E0%B8%A2%E0%B8%B3.jpg?alt=media&token=39bd44b0-77f6-4cba-93d5-bf76b08635ac");

        //Menu(String menuName, ArrayList<String> step, ArrayList<String> picture, String ingredient, String description, String category, String time, String profileMenu)
        _menu = new Menu("ต้มยำจุ้ง",_step, _picture , "เนื้อ เกลือ น้ำปลา","กินแแล้วอร่่อยสุดใจ","ต้ม","15:50","https://firebasestorage.googleapis.com/v0/b/recipe-mobileproject.appspot.com/o/%E0%B8%95%E0%B9%89%E0%B8%A1%E0%B8%A2%E0%B8%B3.jpg?alt=media&token=39bd44b0-77f6-4cba-93d5-bf76b08635ac");
        upToFireBase();
        initSubmitBtn();

    }

    //up data to firebase
    private void upToFireBase()
    {
         Log.d("ADD_MENU", "Up to firebase"+_fbfs);
        _fbfs.collection("Menu")
                .document(_menu.getCategory())
                .collection(_menu.getMenuName())
                .document(_menu.getMenuName())
                .set(_menu)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD_MENU", "SUCCESS");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ADD_MENU", "FAILED");
                Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initSubmitBtn(){
        Button _submiitBtn = getView().findViewById(R.id.addmenu_submitBtn);
        _submiitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new HomeFragment())
                        .addToBackStack(null).commit();
            }
        });
    }
}
