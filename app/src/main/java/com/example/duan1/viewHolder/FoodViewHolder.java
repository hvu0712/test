package com.example.duan1.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.R;

public class FoodViewHolder extends RecyclerView.ViewHolder {


    public TextView foodTitle, tv_ngLieu, tv_cachLam;
    public ImageView foodImg, img_detail;
    public View view;
    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;
        foodImg = (ImageView) itemView.findViewById(R.id.img_itemFoodRicipe);
        foodTitle = (TextView) itemView.findViewById(R.id.tv_titleFoodRicipe);
        img_detail = (ImageView) itemView.findViewById(R.id.img_detail);
        tv_cachLam = (TextView) itemView.findViewById(R.id.tv_cachLam);
        tv_ngLieu = (TextView) itemView.findViewById(R.id.tv_nglieu);
    }
}
