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

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;

import kmitl.it.recipe.recipe.R;

import static android.content.Context.MODE_PRIVATE;

public class AddMenuFragment extends Fragment{

    final  static int IMG_GALLERY_REQUEST = 1;
    ImageView imageView;

    EditText _name, _desc, _time, _ing;
    String _imgStr, _nameStr, _descStr, _typeStr, _timeStr, _ingStr;

    Button _imgBtn, _nextBtn;
    Spinner _typeSpin;

    FirebaseFirestore mDB;
    SQLiteDatabase mySQL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //get FirebaseFirestore
        mDB = FirebaseFirestore.getInstance();

        //create SQLite
        mySQL = getActivity().openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        mySQL.execSQL(
                "CREATE TABLE IF NOT EXISTS menu (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), description VARCHAR(255), " +
                        "type VARCHAR(20), time VARCHAR(20), ingredient VARCHAR(255), image VARCHAR(255))");

        //click Add Photo
        _imgBtn = getView().findViewById(R.id.add_recipe_img_btn);
        _imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGalleryClick();
            }
        });

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

        //click Next Step
        initNextBtn();
    }

    //store data into SQLite
    public void initNextBtn(){
        _nextBtn = getView().findViewById(R.id.add_recipe_next_btn);
        _nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _name = getView().findViewById(R.id.add_recipe_name);
//                _desc = getView().findViewById(R.id.add_recipe_desc);
                _time = getView().findViewById(R.id.add_recipe_time);
//                _ing = getView().findViewById(R.id.add_recipe_ing);

                _nameStr = _name.getText().toString();
                _descStr = _desc.getText().toString();
                _timeStr = _time.getText().toString();
                _ingStr = _ing.getText().toString();

                ContentValues _row = new ContentValues();
                _row.put("name", _nameStr);
                _row.put("description", _descStr);
                _row.put("type", _typeStr);
                _row.put("time", _timeStr);
                _row.put("ingredient", _ingStr);
                _row.put("image", _imgStr);

                mySQL.insert("menu", null, _row);

                Log.d("ADD RECIPE", "INSERT ALREADY");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new AddStepFragment())
                        .addToBackStack(null)
                        .commit();

                Log.d("ADD RECIPE", "GOTO STEP");
            }
        });
    }

    //get Photo's path
    public void onGalleryClick(){
        Intent intent = new Intent(Intent.ACTION_PICK);

        File _picDirect = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String _picPath = _picDirect.getPath();

        //path image
        _imgStr = _picPath;

        Uri uri = Uri.parse(_picPath);

        intent.setDataAndType(uri, "image/*");

        startActivityForResult(intent, IMG_GALLERY_REQUEST);
    }

    //set Photo to fragment_addrecipe
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == getActivity().RESULT_OK){

            if(requestCode == IMG_GALLERY_REQUEST){
                Uri imgUri = data.getData();
                try{
                    Bitmap _bitmap = BitmapFactory.decodeStream(
                            getActivity()
                                    .getContentResolver()
                                    .openInputStream(imgUri)
                    );

                    imageView = getView().findViewById(R.id.add_recipe_img);
                    imageView.setImageBitmap(_bitmap);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "File not Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
