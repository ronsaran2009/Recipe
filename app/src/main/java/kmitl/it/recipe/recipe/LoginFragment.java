package kmitl.it.recipe.recipe;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.protobuf.compiler.PluginProtos;


import java.io.ByteArrayOutputStream;

import java.io.IOException;

import kmitl.it.recipe.recipe.ChooseMenu.ChooseMenuFragment;
import kmitl.it.recipe.recipe.ChooseMenu.TabAdapter;
import kmitl.it.recipe.recipe.Register.RegisterFragment;
import kmitl.it.recipe.recipe.Register.User;
import kmitl.it.recipe.recipe.model.Image;

public class LoginFragment extends Fragment {

    FirebaseAuth _auth;
    FirebaseFirestore _firestore;
    String _uid, profileUrl;
    User user;

    ImageView profileUser;
    //Category
    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //image
    StorageReference storageRef;
    FirebaseStorage storage;
    StorageReference propic;

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
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new RegisterFragment())
                        .commit();
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
                if (task.isSuccessful() && (_auth.getCurrentUser() != null) && !_auth.getCurrentUser().isEmailVerified()) {
                    user = task.getResult().toObject(User.class);
                    Log.d("Login", user.getEmail() + "  :  " + user.getDisplayname());
                    TextView name = getActivity().findViewById(R.id.nav_head_text);
                    name.setText(user.getDisplayname());

                    if (!user.getDisplayname().isEmpty()) {
                        profileUrl = user.getPictureUser();
                        setImageProfile();
                    }
                    Log.d("Login", "GETDATA()  :  SETNAME   " + user.getDisplayname());

                    for (int i = 0; i <= 100; i++) {
                        Log.d("Login", "time : " + i);

                        Log.d("Login", "GETDATA()  :  SETNAME   " + user.getDisplayname());
//                        getImange();
                        //callCate();
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_view, new ChooseMenuFragment()).commit();///ชั่วคราว
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_view, new RegisterFragment()).commit();
            }
        });
    }


    private void setImageProfile(){
        profileUser =   getActivity().findViewById(R.id.nav_head_imange);
        Glide.with(getContext()).load(profileUrl)
                .into(profileUser);
    }
}
