package kmitl.it.recipe.recipe.MyMenu;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import kmitl.it.recipe.recipe.Activity.MainActivity;
import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.RecipeFragment;
import kmitl.it.recipe.recipe.model.Menu;

public class MyMenuFragment extends Fragment
        implements MyMenuItemClickListener {

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private FirebaseFirestore _fbfs = FirebaseFirestore.getInstance();
    private FirebaseAuth _mAuth = FirebaseAuth.getInstance();


    MyMenuAdapter myMenuAdapter;

    private ArrayList<Menu> _menus = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        recyclerView = getView().findViewById(R.id.mymenu_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        getDataResults();

    }


    @Override
    public void onMyMenuItemClick(String _recipeId) {

       getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_view, new RecipeFragment())
                .addToBackStack(null)
                .commit();
        Log.d("MyMenuFragment", "goto RecipeFragment" + _recipeId);
    }

    public void getDataResults() {


        String category = "ต้ม";
        String menuName = "ต้มยำจุ้ง";

        String name;

        Log.d("MyMenuFragment", "GET F DB");


        _fbfs.collection("Menu")
                .document(category)
                .collection(menuName)
                .document(menuName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        documentSnapshot.getData();
                        _menus.add(documentSnapshot.toObject(Menu.class));
                        Log.d("MyMenuFragment", "inside" + String.valueOf(_menus.size()));
                        setMyMenuAdapter( _menus);
                        //set data
                        Log.d("MyMenuFragment", "Menu = " + _menus);
                        if (_menus != null) {

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MyMenuFragment", "Get data from firebase FAILED!!");
            }
        });

        //Log.d("MyMenuFragment", String.valueOf(myMenuAdapter.getItemCount()));

    }

//
//        _fbfs.collection("Menu")
//                .document(category)
//                .collection(menuName)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                            if (doc != null) {
//                                _menus.add(doc.toObject(Menu.class));
//                                Log.d("MY_FRG", "SUCCES!!");
//                            }
//                            else
//                                Log.d("MY_FRG", "FAIL!!");
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("MY_FRG", "e FAIL!!");
//            }
//        });


    private void setMyMenuAdapter( ArrayList<Menu> _menus) {
        myMenuAdapter = new MyMenuAdapter(_menus, MyMenuFragment.this);
        recyclerView.setAdapter(myMenuAdapter);
    }

}
