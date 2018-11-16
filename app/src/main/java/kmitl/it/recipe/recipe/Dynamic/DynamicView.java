package kmitl.it.recipe.recipe.Dynamic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import kmitl.it.recipe.recipe.R;

public class DynamicView {

    private Context context;
    private int id = 0;

    public DynamicView(Context context) {
        this.context = context;
    }

    public TextView descriptionTextView(Context context, String text){
        //des item
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(context);
        textView.setLayoutParams(lparams);
        textView.setTextSize(25);
        textView.setTextColor(context.getResources().getColor(R.color.colorFont));
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/lamoon.ttf"));
        textView.setText(" "+text+" ");
        textView.setMaxEms(8);
        return textView;
    }

    public EditText recieveQuantityEdittext(Context context){
        final ViewGroup.LayoutParams lparams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(context);
        editText.setMinEms(2);
        editText.setTextColor(Color.rgb(0,0,0));
        editText.setId(++id);
        editText.setTextSize(25);
        editText.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/lamoon.ttf"));
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setBackgroundResource(R.drawable.rounded);
        Log.d("DYNAMIC", "id : "+id);
        return editText;
    }

    public int getTotal(){
        return id;
    }
}
