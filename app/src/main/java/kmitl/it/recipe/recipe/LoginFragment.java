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


import kmitl.it.recipe.recipe.Register.RegisterFragment;
import kmitl.it.recipe.recipe.Register.User;

public class LoginFragment extends Fragment {

    FirebaseAuth _auth;
    FirebaseFirestore _firestore;
    String _uid;
    User user;

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



        initLoginBtn();
        initRegisterBtn();
        TextView _profile = getActivity().findViewById(R.id.nav_head_text);
        _profile.setText("User");

//        MenuItem _profile = getActivity().findViewById(R.id.nav_menu_profile);
//        if (_auth.getCurrentUser() != null) {
//            _profile.setTitle("USER");
//        }
    }

    void initLoginBtn(){
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
                }
//                else if (_auth.getCurrentUser() != null) { //check if user is already login
//                    Log.d("USER", "ALREADY LOGIN");
//                    Log.d("USER", "GOTO MENU");
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new CategoryFragment()).commit();
//                }
                else {
                    _auth.signInWithEmailAndPassword(_userIdStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if (_auth.getCurrentUser().isEmailVerified()) {
                                Log.d("Login", "LOGIN SUCCESS");

                                _uid  = _auth.getUid();
                                getData();


                                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_view, new HomeFragment()).commit();
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

    void initRegisterBtn(){
        TextView _regBtn = getView().findViewById(R.id.login_register);
        _regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Login", "GOTO REGISTER");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).commit();
            }
        });
    }
    void getData(){
        _firestore.collection("User")
            .document(_uid)
            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    user = task.getResult().toObject(User.class);
                    TextView name = getActivity().findViewById(R.id.nav_head_text);
                    name.setText(user.getDisplayname());
                    Log.d("Login" , user.getEmail()+ "  :  " + user.getDisplayname());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).commit();
            }
        });
    }


}
