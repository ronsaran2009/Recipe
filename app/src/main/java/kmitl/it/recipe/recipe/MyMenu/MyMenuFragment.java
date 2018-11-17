package kmitl.it.recipe.recipe.MyMenu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kmitl.it.recipe.recipe.AddMenu.AddMenuFragment;
import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.RecyclerView.RecipeFragment;
import kmitl.it.recipe.recipe.Register.User;
import kmitl.it.recipe.recipe.model.Menu;
import kmitl.it.recipe.recipe.model.Mymenu;

public class MyMenuFragment extends Fragment
        implements MyMenuItemClickListener {

    RecyclerView recyclerView;

    private FirebaseFirestore _fbfs = FirebaseFirestore.getInstance();

    FirebaseAuth mAuth;
    String uidUser, cate;
    String _proFileUser = " ";
    MyMenuAdapter myMenuAdapter;

    private ArrayList<Mymenu> myMenuArrayList = new ArrayList<>();
    private ArrayList<Menu> menuArrayList = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("My Menu");


        recyclerView = getView().findViewById(R.id.mymenu_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        mAuth = FirebaseAuth.getInstance();
        uidUser = mAuth.getUid();

        Log.d("MyMenuFragment", "UID : " + uidUser);

        initAddBtn();
        getImgprofile();

    }


    @Override
    public void onMyMenuItemClick(String _recipeId, String _type) {
        Log.d("MyMenuFragment", "goto RecipeFragment " + _recipeId);

        if (_recipeId == null) {
            Toast.makeText(getActivity(), "เมนูยังว่างอยู่", Toast.LENGTH_LONG).show();
            Log.d("MyMenuFragment", "goto RecipeFragment " + _recipeId);
        } else {

            Bundle b = new Bundle();

            //Log.d("MyMenuFragment", "Set Bundle");

            b.putString("myMenuName",_recipeId);
            b.putString("myMenuType", _type);
            Fragment fragment = new RecipeFragment();

            //Log.d("MyMenuFragment", "Sent Bundle");
            fragment.setArguments(b);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_view, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            Log.d("MyMenuFragment", "GOTO RECIPE");

          myMenuArrayList.clear();
          menuArrayList.clear();

        }
    }

    private void getImgprofile(){

        _fbfs.collection("User")
                .document(uidUser)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        documentSnapshot.getData();
                        _proFileUser = documentSnapshot.toObject(User.class).getPictureUser();

                        Log.d("MyMenuFragment", " "+ _proFileUser);
                            getDataResults();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("MyMenuFragment", "--- BYE");
            }
        });

    }

    private void getDataResults() {
        Log.d("MyMenuFragment", "GO TO DB");

        _fbfs.collection("User")
                .document(uidUser)
                .collection("Mymenu")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> list = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                        myMenuArrayList.add((document.toObject(Mymenu.class)));
                    }
                    if (!myMenuArrayList.isEmpty()) {
                        getImage(myMenuArrayList);
                        Log.d("MyMenuFragment", ""+!menuArrayList.isEmpty());

                    }
                    Log.d("MyMenuFragment", ""+menuArrayList.size());


                    if (list.isEmpty()) {
                        Log.d("MyMenuFragment", "MYMENU_EMPTY");
                    }
                    Log.d("MyMenuFragment", "ListMenu " + list.toString());


                } else {
                    Log.d("MyMenuFragment", "Error getting documents: ", task.getException());
                }

            }
        });
    }

    private void setMyMenuAdapter(ArrayList<Menu> _menus, String profileImg) {
        if (!_menus.isEmpty()) {
            myMenuAdapter = new MyMenuAdapter(_menus,profileImg, MyMenuFragment.this);
            recyclerView.setAdapter(myMenuAdapter);

        }

 }

    private void initAddBtn() {
        if (!myMenuArrayList.isEmpty() || !menuArrayList.isEmpty()){
            menuArrayList.clear();
            myMenuArrayList.clear();
        }
        Button addBtn = getView().findViewById(R.id.mymenu_addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          getActivity().getSupportFragmentManager().beginTransaction()
                                                  .replace(R.id.main_view, new AddMenuFragment())
                                                  .addToBackStack(null)
                                                  .commit();
                                      }
                                  }
        );
    }

    private void getImage(final ArrayList<Mymenu> _menus) {
        cate = getCategoryStr(_menus);
                //get data
                _fbfs.collection("Menu")
                        .document(cate)
                        .collection("menu")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<String> list = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getId());
                                if ((_menus.get(0) != null) &&
                                        ((_menus.get(0).getWriter()).equals(document.toObject(Menu.class).getWriter())))
                                {
                                    menuArrayList.add((document.toObject(Menu.class)));
                                }
                                Log.d("MyMenuFragment", "GET IMAGE "+menuArrayList.size());
                            }
                            if (list.isEmpty()) {
                                Log.d("MyMenuFragment", "MYMENU_EMPTY");
                            }
                            // setMyMenuAdapter(_menus);
                            setMyMenuAdapter(menuArrayList, _proFileUser);
                        } else {
                            Log.d("MyMenuFragment", "Error getting documents: ", task.getException());
                        }
                    }
                });
                      Log.d("MyMenuFragment", "OUT GETIMAGE");
        Log.d("MyMenuFragment", "GET IMAGE "+menuArrayList.size());

    }
    private String getCategoryStr(ArrayList<Mymenu> _menu){
        String gotcha = "";
        int i ;
        for (i = 0; i< _menu.size();i++){
            gotcha = _menu.get(i).getType();
            return gotcha;
        }
        return gotcha;
    }
}
