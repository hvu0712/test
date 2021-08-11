package com.example.duan1.models;

import androidx.recyclerview.widget.RecyclerView;

public class Ricipe  {
    String title, tv_cachLam, tv_ngLieu;
    String imgFoodLink;
    String img_detail;

    public Ricipe(String title, String tv_cachLam, String tv_ngLieu, String imgFoodLink, String img_detail) {
        this.title = title;
        this.tv_cachLam = tv_cachLam;
        this.tv_ngLieu = tv_ngLieu;
        this.imgFoodLink = imgFoodLink;
        this.img_detail = img_detail;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgFoodLink() {
        return imgFoodLink;
    }

    public void setImgFoodLink(String imgFoodLink) {
        this.imgFoodLink = imgFoodLink;
    }

    public String getTv_cachLam() {
        return tv_cachLam;
    }

    public void setTv_cachLam(String tv_cachLam) {
        this.tv_cachLam = tv_cachLam;
    }

    public String getTv_ngLieu() {
        return tv_ngLieu;
    }

    public void setTv_ngLieu(String tv_ngLieu) {
        this.tv_ngLieu = tv_ngLieu;
    }

    public String getImg_detail() {
        return img_detail;
    }

    public void setImg_detail(String img_detail) {
        this.img_detail = img_detail;
    }

    public Ricipe() {
    }
}
