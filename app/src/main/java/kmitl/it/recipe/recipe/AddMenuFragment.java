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
import com.google.firebase.firestore.FirebaseFirestore;

import kmitl.it.recipe.recipe.model.Menu;


public class AddMenuFragment extends Fragment {

    Menu _menu = new Menu("น้ำส้ม","คั่รน้ำส้มด้วยมือเรา", 1 , "เนื้อ เกลือ น้ำปลา","กินแแล้วอร่่อยสุดใจ","ของกินสุดอร่อย","15:50",5);


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
            upToFireBase();
            initSubmitBtn();
//
//         public Menu(String menuName, String step, int picture, String ingredient, String description, String category, String time, int profileMenu) {

            }

    //up data to firebase
    private void upToFireBase()
    {
         Log.d("RECORD", "Up to firebase"+_fbfs);
        _fbfs.collection("USER")
//                .document(_muth.getCurrentUser().getUid())
                .document(_muth.getUid())
                .set(_menu)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("RECORD", "SUCCESS");

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("RECORD", "FAILED");
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
