package kmitl.it.recipe.recipe.AddMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends ArrayAdapter {

    Context context;
    ArrayList<String> list = new ArrayList();

    public StepAdapter(@NonNull Context context, int resource,@NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
