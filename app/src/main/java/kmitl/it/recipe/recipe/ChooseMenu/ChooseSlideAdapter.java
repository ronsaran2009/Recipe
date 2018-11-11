package kmitl.it.recipe.recipe.ChooseMenu;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kmitl.it.recipe.recipe.R;


public class ChooseSlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    public ChooseSlideAdapter(Context context) {
        this.context = context;
    }

    //Array
    public int[] list_images = {

            0,
            R.drawable.cat_boiled,
            R.drawable.cat_boiled,
            R.drawable.cat_boiled
    };

    public String[] list_title = {

            "เมนูแนะนำ",
            "แกงส้ม",
            "แกงส้ม",
            "แกงส้ม"
    };

    public String[] list_description = {

            "เลื่อน >",
            "แกงเขียวหวานนั่นแสนอร่อย",
            "แกงเขียวหวานนั่นแสนอร่อย",
            "แกงเขียวหวานนั่นแสนอร่อย"
    };


    @Override
    public int getCount() {
        return list_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_choose_menu, container, false);

        LinearLayout linearLayout = view.findViewById(R.id.choosei_slide);
        ImageView img = view.findViewById(R.id.choosei_img);
        TextView txt1 = view.findViewById(R.id.choosei_title);
        TextView txt2 = view.findViewById(R.id.choosei_desc);

        if (position == 0) {
            txt1.setText(list_title[position]);
            txt2.setText(list_description[position]);
            linearLayout.setBackgroundColor(Color.rgb(252, 214, 44));
            img.setImageResource(list_images[position]);
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);

        } else {
            txt1.setText(list_title[position]);
            txt2.setText(list_description[position]);
            img.setImageResource(list_images[position]);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }


        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}