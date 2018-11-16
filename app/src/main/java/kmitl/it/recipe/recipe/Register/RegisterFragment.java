package kmitl.it.recipe.recipe.Register;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;


import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import kmitl.it.recipe.recipe.LoginFragment;
import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Image;

import static android.app.Activity.RESULT_OK;

public class RegisterFragment extends Fragment {
    //Att. for onGalleryClick
    private static final int select = 100;

    //Att. for onActivityResult
    ProgressDialog progressDialog;
    UploadTask uploadTask;
    Uri uriImage;
    ImageView _imageView;

    private FirebaseAuth mailAuth;
    FirebaseFirestore _firestore;
    FirebaseUser _mUser;
    FirebaseStorage storage;
    StorageReference storageRef, imgRef;

    private ActionBarDrawerToggle _abdt;
    private DrawerLayout _drawMain;

    String _imgStr, _uidStr;

    //Att. for registerUser
    EditText _display, _email, _password, _repassword;
    String _displayStr, _emailStr, _passwordStr, _repasswordStr;
    TextView _regBtn;

    ImageButton _imgBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);}
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d("REGISTER", "1");

        _drawMain = getActivity().findViewById(R.id.drawMain);
        _abdt = new ActionBarDrawerToggle(getActivity(), _drawMain, R.string.open, R.string.close);
        _abdt.setDrawerIndicatorEnabled(false);
        Log.d("REGISTER", "2");

        getActivity().setTitle("REGISTER");

        //open FirebaseFirestore
        _firestore = FirebaseFirestore.getInstance();
        mailAuth = FirebaseAuth.getInstance();

        //open FirebaseStorage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        _regBtn = getView().findViewById(R.id.register_registerbtn);

        _imgBtn = getView().findViewById(R.id.register_img_btn);

        //choose Image
        _imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGalleryClick();
            }
        });
    }

    //get Photo's path
    public void onGalleryClick() {
        Intent intent = new Intent(Intent.ACTION_PICK);

        File _picDirect = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String _picPath = _picDirect.getPath();
        _imgStr = _picPath;
        Log.d("ADD MENU", _picPath);

        intent.setType("image/*");
        startActivityForResult(intent, select);
    }

    //set Photo to fragment_addrecipe
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case select:
                if (resultCode == RESULT_OK) {
                    uriImage = data.getData();

                    try {
                        Bitmap _bitmap = BitmapFactory.decodeStream(
                                getActivity()
                                        .getContentResolver()
                                        .openInputStream(uriImage)
                        );
                        _imageView = getActivity().findViewById(R.id.register_img);
                        _imageView.setImageBitmap(_bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    //Click Register
                    _regBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            _display = getView().findViewById(R.id.register_display);
                            _email = getView().findViewById(R.id.register_email);
                            _password = getView().findViewById(R.id.register_password);
                            _repassword = getView().findViewById(R.id.register_repassword);

                            _displayStr = _display.getText().toString();
                            _emailStr = _email.getText().toString();
                            _passwordStr = _password.getText().toString();
                            _repasswordStr = _repassword.getText().toString();

                            if (_passwordStr.length() < 6) {
                                Toast.makeText(
                                        getActivity(),
                                        "Password ต้องมีความกว้างขั้นต่ำ 6 ตัวอักษร",
                                        Toast.LENGTH_SHORT
                                ).show();
                                Log.d("REGISTER", "PASSWORD LENGHT < 6");
                            } else if (!_passwordStr.equals(_repasswordStr)) {
                                Toast.makeText(
                                        getActivity(),
                                        "Password ไม่ตรงกัน",
                                        Toast.LENGTH_SHORT
                                ).show();
                                Log.d("REGISTER", "PASSWORD IS NOT EXIST");
                            } else if (_displayStr.isEmpty() || _emailStr.isEmpty() || _passwordStr.isEmpty() || _repasswordStr.isEmpty() || _imgStr.isEmpty()) {
                                Toast.makeText(
                                        getActivity(),
                                        "กรุณากรอกข้อมูลให้ครบ",
                                        Toast.LENGTH_SHORT
                                ).show();
                                Log.d("REGISTER", "INFORMATION IS EMPTY");
                            } else {
                                sendVerifiedEmail();

                                //upload imageUser to FireStorage
                                Bitmap bmp = null;
                                try {
                                    bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uriImage);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bmp.compress(Bitmap.CompressFormat.JPEG, 10, baos); //resize image

                                byte[] byteArray = baos.toByteArray();

                                progressDialog = new ProgressDialog(getContext());
                                progressDialog.setMax(100);
                                progressDialog.setMessage("Registering...");
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                progressDialog.show();
                                progressDialog.setCancelable(false);

                                imgRef = storageRef.child("profile/" + _emailStr + "/" + "pic"); //+ _emailStr
                                uploadTask = imgRef.putBytes(byteArray);

                                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                        progressDialog.incrementProgressBy((int) progress);
                                    }
                                });

                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                                        String urlImage = downloadUrl.toString();
                                        progressDialog.dismiss();

                                        _imgStr = urlImage; //get URL image
                                        Log.d("REGISTER", "URL imageUser = " + _imgStr);
                                        Log.d("REGISTER", "Detail" + _displayStr + _emailStr);
                                        if(mailAuth.getCurrentUser() != null){
                                        _uidStr = mailAuth.getCurrentUser().getUid();
                                        User user = new User(_uidStr,_emailStr, _displayStr, _imgStr);



                                        //uploadImage();
                                        _firestore.collection("User")
                                                .document(_uidStr)
                                                .set(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("REGISTER", "RECORD_database");

                                                        Toast.makeText(getActivity(), "SAVE", Toast.LENGTH_SHORT).show();

                                                        mailAuth.signOut();

                                                        Toast.makeText(getActivity(), "กรุณาตรวจสอบEMAIL", Toast.LENGTH_SHORT).show();

                                                        Log.d("REGISTER", "GOTO LOGIN");
                                                        getActivity().getSupportFragmentManager()
                                                                .beginTransaction()
                                                                .addToBackStack(null)
                                                                .replace(R.id.main_view, new LoginFragment())
                                                                .commit();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                Toast.makeText(getActivity(), "ERROR = " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                Log.d("REGISTER", "ERROR = " + e.getMessage());

                                            }
                                        });
                                        }
                                        else {
                                            getActivity().getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .addToBackStack(null)
                                                    .replace(R.id.main_view, new RegisterFragment())
                                                    .commit();
                                            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                                        }

                                        Log.d("REGISTER", "after");
                                    }
                                });

                            }
                        }
                    });
                }
        }
    }

    void sendVerifiedEmail() {
        mailAuth.createUserWithEmailAndPassword(_emailStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            public void onSuccess(AuthResult authResult) {
                authResult.getUser()
                        .sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
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
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.main_view, new RegisterFragment())
                                .commit();
                        Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            public void onFailure(Exception e) {
                Toast.makeText(getActivity(), "ERROR : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("reg", "ERROR : " + e.getMessage());
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new RegisterFragment())
                        .commit();
                
            }
        });
    }

}
