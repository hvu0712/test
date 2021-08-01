package com.example.duan1.models;

public class Ricipe {
    String title;
    String imgFood;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgFood() {
        return imgFood;
    }

    public void setImgFood(String imgFood) {
        this.imgFood = imgFood;
    }

    public Ricipe(String title, String imgFood) {
        this.title = title;
        this.imgFood = imgFood;
    }

    public Ricipe() {
    }
}
