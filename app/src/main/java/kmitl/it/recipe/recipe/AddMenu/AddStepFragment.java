package kmitl.it.recipe.recipe.AddMenu;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import kmitl.it.recipe.recipe.MyMenu.MyMenuFragment;
import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Image;
import kmitl.it.recipe.recipe.model.Menu;
import kmitl.it.recipe.recipe.model.Mymenu;

import static android.content.Context.MODE_PRIVATE;

public class AddStepFragment  extends Fragment{

    private static final int select = 100;
    ProgressDialog progressDialog;
    UploadTask uploadTask;

    FirebaseAuth mAuth;
    FirebaseFirestore myDB;
    FirebaseStorage storage;
    StorageReference storageRef, imgRef;

    SQLiteDatabase mySQL;

    String _imgStr, _nameStr, _descStr, _typeStr, _timeStr, _ingStr, uidUser, stepStr, linkStr, _writer;
    Button _submitBtn;

    Image image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_addstep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get UID
        mAuth = FirebaseAuth.getInstance();
        uidUser = mAuth.getUid();

        //open firebase
        myDB = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        //query data
        mySQL = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        Cursor myCursor = mySQL.rawQuery("SELECT * FROM menu", null);

        while (myCursor.moveToNext()) {
            _nameStr = myCursor.getString(1);
            _descStr = myCursor.getString(2);
            _typeStr = myCursor.getString(3);
            _timeStr = myCursor.getString(4);
            _ingStr = myCursor.getString(5);
        }

        getWriter(uidUser);

        initSubmitBtn();
    }

    void initSubmitBtn() {
        _submitBtn = getView().findViewById(R.id.step_submit);
        _submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Step info
                EditText step = getView().findViewById(R.id.step);
                EditText link = getView().findViewById(R.id.step_link);

                //Step info
                stepStr = step.getText().toString();
                linkStr = link.getText().toString();

                Log.d("ADD RECIPE", _nameStr + _descStr
                        + _typeStr + _timeStr + _ingStr + _writer + _imgStr + stepStr + linkStr);

                Log.d("ADD STEP", "GOTO MY_MENU");
                //Set data to Mymenu
                setMyMenu();
            }
        });
    }

    //Writer's name
    void getWriter(final String uidUser) {
        myDB.collection("User").document(uidUser)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        _writer = documentSnapshot.get("displayname").toString();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ADD STEP", "ERROR");
            }
        });
    }

    //Set data to Mymenu
    void setMyMenu(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd_HH:mm:ss");
        String date = sdf.format(Calendar.getInstance().getTime());

        Mymenu myMenu = new Mymenu(_nameStr, _typeStr, date.split("_")[0], _writer);

        myDB.collection("User")
                .document(uidUser)
                .collection("Mymenu")
                .document(_nameStr)
                .set(myMenu)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD STEP", "insert MyMenu");
                        Log.d("ADD STEP", "GOTO set IMAGE");
                        uploadImage();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ADD STEP", "FAIL");
            }
        });
    }

    //Get URL image
    void uploadImage(){

        Bundle bundle = getArguments();
        if(bundle != null){
            image = (Image) bundle.getSerializable("uriImage");
            Log.d("ADD STEP", "Bundle");
        } else {
            Log.d("ADD STEP", "Bundle Null");
        }

        Uri uriImage = image.getUriImage();

        imgRef = storageRef.child("recipes/"+_typeStr+"/"+_nameStr); //+ typeFood + nameFood

        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uriImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos); //resize image
        byte[] data = baos.toByteArray();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMax(100);
        progressDialog.setMessage("Uploading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        progressDialog.setCancelable(false);

        UploadTask uploadTask = imgRef.putBytes(data);

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
                /*
                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                String urlImage = downloadUrl.toString();
                progressDialog.dismiss();
                */
                storageRef.child("recipes/"+_typeStr+"/"+_nameStr).getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                _imgStr = uri.toString(); //get URL image
                                progressDialog.dismiss();
                                Log.d("ADD STEP", _imgStr);
                                Log.d("ADD STEP", "GOTO SET MENU");
                                setMenu();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "downloadUrl FAIL", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //Set data to Menu
    void setMenu(){

        Menu menu = new Menu(_imgStr, _nameStr, _descStr, _typeStr, _timeStr, _ingStr, _writer, stepStr, linkStr);

        myDB.collection("Menu")
                .document(_typeStr)
                .collection("menu")
                .document(_nameStr)
                .set(menu)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ADD STEP", "GOTO MY_MENU");
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view, new MyMenuFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
