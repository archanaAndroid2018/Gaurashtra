package com.gaurashtra.app.view.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.HomePagebean.SliderDatum;
import com.gaurashtra.app.view.activity.HomeActivity;

public class FirstPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<SliderDatum> sliderlist=new ArrayList<>();
    SliderClickInterface sliderClickInterface;

    public  FirstPagerAdapter(HomeActivity homeActivity, List<SliderDatum> slider, HomeActivity activity) {
        this.sliderlist=slider;
        this.context=homeActivity;
        this.sliderClickInterface= activity;
        if (context != null)
            inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return sliderlist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View sliderView = inflater.inflate(R.layout.slideritem,container,false);
        ImageView imageView = sliderView.findViewById(R.id.ivslider);

        String image = sliderlist.get(position).getSliderImage();
        Glide.with(context).load(image).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.1f).placeholder(R.drawable.img_icon).fitCenter().into(imageView);

        ((ViewPager) container).addView(sliderView, position);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliderClickInterface.bannerClick(position);
            }
        });

//        if(position==1){
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i=new Intent(context, CategoryFragment.class);
//                    context.startActivity(i);
//                }
//            });


        //}
        return sliderView;




    }
    public interface SliderClickInterface{
        void bannerClick(int pos);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
