package kmitl.it.recipe.recipe.AddMenu;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import kmitl.it.recipe.recipe.R;
import kmitl.it.recipe.recipe.model.Image;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class AddMenuFragment extends Fragment{

//    final  static int IMG_GALLERY_REQUEST = 1;
    ImageView _imageView;

    EditText _name, _desc, _time, _ing;
    String _nameStr, _descStr, _typeStr, _timeStr, _ingStr, _imgStr="null";

    ImageView _imgBtn;
    Button  _nextBtn;
    Spinner _typeSpin;

    FirebaseFirestore mDB;
    SQLiteDatabase mySQL;

    //Att. for checkMenuName
    List allMenu = new ArrayList();

    private static final int select = 100;
    Uri uriImage;

    Image image = new Image();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Add Menu");

        //get FirebaseFirestore
        mDB = FirebaseFirestore.getInstance();

        //create SQLite
        mySQL = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        mySQL.execSQL(
                "CREATE TABLE IF NOT EXISTS menu (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), description VARCHAR(255), " +
                        "type VARCHAR(20), time VARCHAR(20), ingredient VARCHAR(255))");

        _imgBtn = getView().findViewById(R.id.add_recipe_img_btn);
        _imageView = getView().findViewById(R.id.add_recipe_img);
        _nextBtn = getView().findViewById(R.id.add_recipe_next_btn);

        //Categories
        checkMenuName();

        //click Add Photo
        onGalleryClick();

        //click Next Step
        nextBtn();

        //Array Food's type - DROPDOWN
        _typeSpin = getView().findViewById(R.id.add_recipe_spinner_type);

        final String[] _typeArray = getResources().getStringArray(R.array.type);
        ArrayAdapter<String> _adapType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, _typeArray);

        _typeSpin.setAdapter(_adapType);

        _typeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _typeStr = _typeArray[position];
                Log.d("ADD RECIPE", _typeStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("ADD RECIPE", "ERROR");
            }
        });
    }

    void nextBtn(){
        _nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _name = getView().findViewById(R.id.add_recipe_name);
                        _desc = getView().findViewById(R.id.add_recipe_desc);
                        _time = getView().findViewById(R.id.add_recipe_time);
                        _ing = getView().findViewById(R.id.add_recipe_ing);

                        _nameStr = _name.getText().toString();
                        _descStr = _desc.getText().toString();
                        _timeStr = _time.getText().toString();
                        _ingStr = _ing.getText().toString();

                        if(allMenu.contains(_nameStr)){
                            Toast.makeText(getActivity(), "ชื่อเมนูนี้ มีผู้ใช้แล้ว", Toast.LENGTH_SHORT).show();
                        } else if(_nameStr.isEmpty() || _descStr.isEmpty() || _typeStr.isEmpty() || _timeStr.isEmpty() || _ingStr.isEmpty() || _imgStr.equals("null")){
                            Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                        } else {
                            ContentValues _row = new ContentValues();
                            _row.put("name", _nameStr);
                            _row.put("description", _descStr);
                            _row.put("type", _typeStr);
                            _row.put("time", _timeStr);
                            _row.put("ingredient", _ingStr);

                            mySQL.insert("menu", null, _row);

                            Log.d("ADD RECIPE", "INSERT ALREADY");

                            //create Bundle
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("uriImage", image); //Uri Image

                            AddStepFragment obj = new AddStepFragment();
                            obj.setArguments(bundle);

                            Log.d("ADD RECIPE", "GOTO STEP");
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.main_view, obj)
                                    .addToBackStack(null)
                                    .commit();
                        }

                    }
                });
            }
        });
    }

    //get Photo's path
    public void onGalleryClick(){
        _imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);

                File _picDirect = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String _picPath = _picDirect.getPath();
                _imgStr = _picPath;
                Log.d("ADD MENU", _picPath);

                intent.setType("image/*");
                startActivityForResult(intent, select);
            }
        });
    }

    //set Photo to fragment_addrecipe
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case select:
                if (resultCode == RESULT_OK) {
                    uriImage = data.getData();

                    image.setUriImage(uriImage);

                    try {
                        Bitmap _bitmap = BitmapFactory.decodeStream(
                                getActivity()
                                        .getContentResolver()
                                        .openInputStream(uriImage)
                        );
                        Bitmap _bitmap2 = Bitmap.createScaledBitmap(_bitmap, 300, 300, true);
                        _imageView.setImageBitmap(_bitmap2);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                }
        }
    }

    void checkMenuName(){
        String[] cate = {"ต้ม - แกง", "ผัด - ทอด", "อบ - ตุ๋น", "ปิ้ง - ย่าง", "อาหารจานเดียว"};

        for(int i=0; i<cate.length; i++){
            mDB.collection("Menu").document(cate[i]).collection("menu")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                            for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                                allMenu.add(doc.get("menuName").toString());
                            }
                        }
                    });
        }
    }
}
