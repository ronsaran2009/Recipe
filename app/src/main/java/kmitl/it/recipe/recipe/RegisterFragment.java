package kmitl.it.recipe.recipe;

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

public class RegisterFragment extends Fragment {
    private FirebaseAuth mailAuth;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);}
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mailAuth = FirebaseAuth.getInstance();
        initRegisterBtn();

    }


    void initRegisterBtn(){
        TextView _regBtn = getView().findViewById(R.id.register_registerbtn);
        _regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText _email = getView().findViewById(R.id.register_email);
                EditText _password = getView().findViewById(R.id.register_password);
                EditText _repassword = getView().findViewById(R.id.register_repassword);
                String _emailStr = _email.getText().toString();

                String _passwordStr = _password.getText().toString();
                String _repasswordStr = _repassword.getText().toString();
                if (_passwordStr.length() < 6){
                    Toast.makeText(
                            getActivity(),
                            "Password ต้องมีความกว้างขั้นต่ำ 6 ตัวอักษร",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER", "PASSWORD LENGHT < 6");
                }else if (! _passwordStr.equals(_repasswordStr) ){

                    Toast.makeText(
                            getActivity(),
                            "Password ไม่ตรงกัน",
                            Toast.LENGTH_SHORT
                    ).show();
                    Log.d("USER", "PASSWORD IS NOT EXIST");
                }
                else{



                    mailAuth.createUserWithEmailAndPassword(_emailStr,_passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>(){
                        public void onSuccess(AuthResult authResult) {
                            sendVerifiedEmail(authResult.getUser());
                            mailAuth.signOut();
                            Log.d("USER", "GOTO login");
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new LoginFragment()).commit();

                        }
                    }).addOnFailureListener(new OnFailureListener(){
                        public void onFailure(Exception e){
                            Toast.makeText(
                                    getActivity(),
                                    "ERROR : " + e.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                            Log.d("USER", "ERROR : " + e.getMessage());
                        }
                    });

                    //
                }

            }
        });
    }


    void sendVerifiedEmail(FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("USER", "SEND VERIFIED EMAIL");

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
