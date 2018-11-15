package kmitl.it.recipe.recipe;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import kmitl.it.recipe.recipe.ChooseMenu.ChooseMenuFragment;
import kmitl.it.recipe.recipe.Register.RegisterFragment;
import kmitl.it.recipe.recipe.Register.User;

public class LoginFragment extends Fragment {

    FirebaseAuth _auth;
    FirebaseFirestore _firestore;
    String _uid;
    User user;

    //Category
    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("LOGIN");
        _auth = FirebaseAuth.getInstance();

        _firestore = FirebaseFirestore.getInstance();
        Log.d("Login", "START_LOGIN");
        if (_auth.getCurrentUser() != null) {
            Log.d("Login", "ALREADY LOGIN");
            Log.d("Login", "GOTO MENU");
            _uid = _auth.getUid();
            getData();


        } else {


            initLoginBtn();
            initRegisterBtn();
        }

//        MenuItem _profile = getActivity().findViewById(R.id.nav_menu_profile);
//        if (_auth.getCurrentUser() != null) {
//            _profile.setTitle("USER");
//        }
    }

    void initLoginBtn() {
        Button _loginBtn = getView().findViewById(R.id.login_loginbnt);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText _userId = getView().findViewById(R.id.login_email);
                EditText _password = getView().findViewById(R.id.login_pass);
                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();
                if (_userIdStr.isEmpty() || _passwordStr.isEmpty()) { //check empty input
                    Toast.makeText(
                            getActivity(),
                            "กรุณาระบุ user or password",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("Login", "USER OR PASSWORD IS EMPTY");
                } else {
                    _auth.signInWithEmailAndPassword(_userIdStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (_auth.getCurrentUser().isEmailVerified()) {
                                Log.d("Login", "LOGIN SUCCESS");

                                _uid = _auth.getUid();
                                getData();

                                Log.d("Login", "GOTO MENU");

                            } else {
                                Toast.makeText(
                                        getActivity(),
                                        "Please Verified Email",
                                        Toast.LENGTH_SHORT
                                ).show();
                                _auth.signOut();
                                Log.d("Login", "INVALID Verified Email");
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(
                                    getActivity(),
                                    e.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                            _auth.signOut();
                            Log.d("Login", "ERROR = " + e.getMessage());
                        }
                    });

                }
            }
        });

    }

    void initRegisterBtn() {
        TextView _regBtn = getView().findViewById(R.id.login_register);
        _regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Login", "GOTO REGISTER");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).commit();
            }
        });
    }

    void getData() {
        Log.d("Login", "GETDATA()  :  " + _uid);
        _firestore.collection("User")
                .document(_uid)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    user = task.getResult().toObject(User.class);
                    Log.d("Login", user.getEmail() + "  :  " + user.getDisplayname());
                    TextView name = getActivity().findViewById(R.id.nav_head_text);
                    name.setText(user.getDisplayname());
                    Log.d("Login", "GETDATA()  :  SETNAME   " + user.getDisplayname());
                    for (int i = 0 ; i<=100;i++){
                        Log.d("Login", "time : "+ i);
                    }
                    //callCate();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new ChooseMenuFragment()).commit();///ชั่วคราว
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).commit();
            }
        });
    }

    void callCate() {
        getActivity().setContentView(R.layout.category_main);
        viewPager = getActivity().findViewById(R.id.viewPager);
        tabLayout = getActivity().findViewById(R.id.tabLayout);
        tabAdapter = new TabAdapter(getActivity().getSupportFragmentManager());
        tabAdapter.addFragment(new ChooseMenuFragment(), " ต้ม - แกง ");
        tabAdapter.addFragment(new ChooseMenuFragment(), " ผัด - ทอด ");
        tabAdapter.addFragment(new ChooseMenuFragment(), " อบ - ตุ๋น ");
        tabAdapter.addFragment(new ChooseMenuFragment(), " ปิ้ง - ย่าง ");
        tabAdapter.addFragment(new ChooseMenuFragment(), " อาหารจานเดียว");
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
