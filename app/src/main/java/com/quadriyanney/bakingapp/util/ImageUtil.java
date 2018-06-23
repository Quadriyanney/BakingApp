package com.quadriyanney.bakingapp.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtil {

    private Context context;

    public ImageUtil(Context context) {
        this.context = context;
    }

    public void loadImage(String imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl).into(imageView);
    }
}
