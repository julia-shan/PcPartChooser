package com.example.pcpartchooser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<Integer> images; //Array for images
    LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context, ArrayList<Integer> images){
        // sets context and array of images
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size(); //returns the number of images
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==  ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        //inflating viewpager_item.xml
        View itemView = layoutInflater.inflate(R.layout.viewpager_item, container, false);

        //assign image view from viewpager_item.xml
        ImageView imageView = (ImageView) itemView.findViewById(R.id.viewPager_image);

        // setting the image in the imageView
        imageView.setImageResource(images.get(position));

        //Adds the view
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //removes the view
        container.removeView((LinearLayout) object);

    }
}
