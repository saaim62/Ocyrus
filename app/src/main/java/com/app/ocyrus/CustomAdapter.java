package com.app.ocyrus;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.app.ocyrus.utills.DashbordResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;

public class CustomAdapter extends PagerAdapter {

    private final Activity activity;
    private final ArrayList<DashbordResponse.Advertisement> data;
    private String[] namesArray;

    public CustomAdapter(Activity activity, ArrayList<DashbordResponse.Advertisement> data){

        this.activity = activity;
        this.data = data;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = (activity).getLayoutInflater();
        //creating  xml file for custom viewpager
        View viewItem = inflater.inflate(R.layout.content_custom, container, false);
        //finding id
        ImageView imageView =  viewItem.findViewById(R.id.imageView);
        //setting data
//        imageView.setBackgroundResource(data[position]);

        if (data.get(position).getImage_full_path() != null) {
            Glide.with(activity)
                .setDefaultRequestOptions(
                    new RequestOptions()
                            .centerCrop()
                            .placeholder(R.drawable.inner_logo)
                            .error(R.drawable.inner_logo)
            )
                    .load(data.get(position).getImage_full_path()).into(imageView);
        }


        container.addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        container.removeView((View) object);
    }
}