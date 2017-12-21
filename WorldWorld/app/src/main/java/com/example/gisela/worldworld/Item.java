package com.example.gisela.worldworld;

import android.graphics.drawable.Drawable;

/**
 * Created by Gisela on 1/19/17.
 */

public class Item {
    private  Drawable image;
    private  String title;

    public Item(Drawable image, String title){
        this.image = image;
        this.title = title;
    }

    public Drawable getImage() {
         return image;
     }

    public String getTitle(){
        return title;
    }
}
