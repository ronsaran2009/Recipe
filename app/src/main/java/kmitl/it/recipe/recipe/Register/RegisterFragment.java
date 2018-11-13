package kmitl.it.recipe.recipe.Register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import kmitl.it.recipe.recipe.LoginFragment;
import kmitl.it.recipe.recipe.R;

public class RegisterFragment extends Fragment {
    private FirebaseAuth mailAuth;
    FirebaseFirestore _firestore;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);}
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("REGISTER");
        _firestore = FirebaseFirestore.getInstance();
        mailAuth = FirebaseAuth.getInstance();
        initRegisterBtn();

    }

    void initRegisterBtn(){
        TextView _regBtn = getView().findViewById(R.id.register_registerbtn);
        _regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText _display = getView().findViewById(R.id.register_display);
                EditText _email = getView().findViewById(R.id.register_email);
                EditText _password = getView().findViewById(R.id.register_password);
                EditText _repassword = getView().findViewById(R.id.register_repassword);

                final String _displayStr = _display.getText().toString();
                final String _emailStr = _email.getText().toString();
                String _passwordStr = _password.getText().toString();
                String _repasswordStr = _repassword.getText().toString();
                if (_passwordStr.length() < 6){
                    Toast.makeText(
                            getActivity(),
                            "Password ต้องมีความกว้างขั้นต่ำ 6 ตัวอักษร",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("reg", "PASSWORD LENGHT < 6");
                }else if (! _passwordStr.equals(_repasswordStr) ){
                    Toast.makeText(
                            getActivity(),
                            "Password ไม่ตรงกัน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("reg", "PASSWORD IS NOT EXIST");
                }else if (_displayStr.isEmpty() || _emailStr.isEmpty() || _passwordStr.isEmpty() || _repasswordStr.isEmpty()){
                    Toast.makeText(
                            getActivity(),
                            "กรุณากรอกข้อมูลให้ครบ",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("reg", "INFORMATION IS EMPTY");
                }
                else{
                    mailAuth.createUserWithEmailAndPassword(_emailStr,_passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>(){
                        public void onSuccess(AuthResult authResult) {
                            sendVerifiedEmail(authResult.getUser());
                            String _uid = mailAuth.getCurrentUser().getUid();
                            User _data = new User(_emailStr, _displayStr);
                            Log.d("reg", "before");
                            _firestore.collection("User")
                                    .document(_uid)
                                    .set(_data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("reg", "RECORD_database");

                                            Toast.makeText(
                                                    getActivity(),
                                                    "SAVE",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                            mailAuth.signOut();
                                            Toast.makeText(
                                                    getActivity(),
                                                    "กรุณาตรวจสอบEMAIL",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                            Log.d("reg", "GOTO login");
                                            getActivity().getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .addToBackStack(null)
                                                    .replace(R.id.main_view, new LoginFragment())
                                                    .commit();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(
                                            getActivity(),
                                            "ERROR = " + e.getMessage(),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                    Log.d("reg", "ERROR = " + e.getMessage());
                                }
                            });
                            Log.d("reg", "after");


                        }
                    }).addOnFailureListener(new OnFailureListener(){
                        public void onFailure(Exception e){
                            Toast.makeText(
                                    getActivity(),
                                    "ERROR : " + e.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                            Log.d("reg", "ERROR : " + e.getMessage());
                        }
                    });
                }

            }
        });
    }


    void sendVerifiedEmail(FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("reg", "SEND VERIFIED EMAIL");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(
                        getActivity(),
                        "ERROR : " + e.getMessage(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }




}
