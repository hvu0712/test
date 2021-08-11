package com.example.duan1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1.R;

public class GridViewAdapter extends BaseAdapter {

    Context context;
    String[] foodTitle;
    int[] foodImg;

    public GridViewAdapter(Context context, String[] foodTitle, int[] foodImg) {
        this.context = context;
        this.foodTitle = foodTitle;
        this.foodImg = foodImg;
    }


    @Override
    public int getCount() {
        return foodTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.food_recipe_item, null);
        TextView tvFoodRicipe = convertView.findViewById(R.id.tv_titleFoodRicipe);
        ImageView  imgFoodRicipe = convertView.findViewById(R.id.img_itemFoodRicipe);
        tvFoodRicipe.setText(foodTitle[position]);
        imgFoodRicipe.setImageResource(foodImg[position]);
        return convertView;
    }
}
