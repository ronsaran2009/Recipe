package kmitl.it.recipe.recipe.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kmitl.it.recipe.recipe.R;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<String> _dataset = new ArrayList<>();
    private Context mContext;


    public MainAdapter(Context context, ArrayList<String> _dataset){
        mContext = context;
        this._dataset = _dataset;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.fragment_home_item,
                       parent, false);

       ViewHolder vh = new ViewHolder(v);
       return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder viewHolder, final int position) {
        Log.d("MAIN_ADAPTER", "onBindViewHolder: called.");
        viewHolder.mTextView.setText(_dataset.get(position));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MAIN_ADAPTER", "onClick: clicked on: " + _dataset.get(position));
                Toast.makeText(mContext, _dataset.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return _dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;
        public CardView parentLayout;

        public ViewHolder(View itemView){
            super(itemView);
            //mTextView = itemView.findViewById(R.id.title_recipe_item);
            parentLayout = itemView.findViewById(R.id.parentLayout);
        }
    }
}
