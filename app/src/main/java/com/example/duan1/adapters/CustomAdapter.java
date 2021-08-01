package com.example.duan1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;
import com.example.duan1.models.Ricipe;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<Ricipe> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomAdapter(List<Ricipe> listData,  Context aContext) {
        this.listData = listData;
        this.context = aContext;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.food_recipe_item, null);
            holder = new ViewHolder();
            holder.foodImg = (ImageView) convertView.findViewById(R.id.img_itemFoodRicipe);
            holder.tv_foodTille = (TextView) convertView.findViewById(R.id.tv_titleFoodRicipe);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Ricipe ricipe = this.listData.get(position);
        holder.tv_foodTille.setText(ricipe.getTitle());
        holder.foodImg.setImageResource(R.drawable.ricardo_profile);


        return convertView;
    }

    static class ViewHolder{
        ImageView foodImg;
        TextView tv_foodTille;
    }
}
