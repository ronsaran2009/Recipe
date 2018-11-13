package kmitl.it.recipe.recipe.AddRecipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.io.File;
import java.io.FileNotFoundException;

import kmitl.it.recipe.recipe.R;

public class AddRecipeFragment extends Fragment{

    final  static int IMG_GALLERY_REQUEST = 1;
    ImageView imageView;
    EditText _nameFood, _descFood, _typeFood, _timeCook, _ingre;
    Button _imgBtn, _nextBtn;
    Spinner _typeSpin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //click Add Photo
        _imgBtn = getView().findViewById(R.id.add_recipe_img_btn);
        _imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGalleryClick(_imgBtn);
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
                Toast.makeText(getActivity(), "Select = "+_typeArray[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
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

            }
        });
    }

    //get Photo's path
    public void onGalleryClick(View v){
        Intent intent = new Intent(Intent.ACTION_PICK);

        File _picDirect = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String _picPath = _picDirect.getPath();

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
