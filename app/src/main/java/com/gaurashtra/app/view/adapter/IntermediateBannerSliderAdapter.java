package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.HomePagebean.AfterBestOFDatum;
import com.gaurashtra.app.model.bean.HomePagebean.SliderDatum;
import com.gaurashtra.app.view.activity.HomeActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class IntermediateBannerSliderAdapter extends PagerAdapter {

    Context context;
    List<AfterBestOFDatum> sliderlist = new ArrayList<>();
    BannerOnClickInterface onClickInterface;
    private LayoutInflater inflater;
    ArrayList<View> views= new ArrayList<>();
    int TYPE=1;
    final int BEFORE_VIDEO=1, AFTER_BESTOF=2, AFTER_SHOP=3, FOOTER=4;


    public IntermediateBannerSliderAdapter(HomeActivity homeActivity, List<AfterBestOFDatum> slider, HomeActivity activity, int type) {
        this.sliderlist=slider;
        this.context=homeActivity;
        this.TYPE= type;
        this.onClickInterface= activity;
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
        View sliderView= null;
        ImageView imageView= null;
        if(TYPE==BEFORE_VIDEO) {
            sliderView = inflater.inflate(R.layout.banner_slider_item, container, false);
            sliderView.setId(TYPE);
            imageView = sliderView.findViewById(R.id.ivslider);
            String image = sliderlist.get(position).getBannerImage();
            Glide.with(context).load(image).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.1f).placeholder(R.drawable.img_icon).fitCenter().into(imageView);

            ((ViewPager) container).addView(sliderView, position);
        }else if(TYPE == AFTER_BESTOF) {
            sliderView = inflater.inflate(R.layout.banner_slider_item, container, false);
            sliderView.setId(TYPE);
            imageView = sliderView.findViewById(R.id.ivslider);
            String image = sliderlist.get(position).getBannerImage();
            Glide.with(context).load(image).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.1f).placeholder(R.drawable.img_icon).fitCenter().into(imageView);

            ((ViewPager) container).addView(sliderView, position);
        }else if(TYPE== AFTER_SHOP) {
            sliderView = inflater.inflate(R.layout.banner_slider_item, container, false);
            sliderView.setId(TYPE);
            imageView = sliderView.findViewById(R.id.ivslider);
            String image = sliderlist.get(position).getBannerImage();
            Glide.with(context).load(image).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.1f).placeholder(R.drawable.img_icon).fitCenter().into(imageView);

            ((ViewPager) container).addView(sliderView, position);
        }else if(TYPE== FOOTER) {
            sliderView = inflater.inflate(R.layout.banner_slider_item, container, false);
            sliderView.setId(TYPE);
            imageView = sliderView.findViewById(R.id.ivslider);
            String image = sliderlist.get(position).getBannerImage();
            Glide.with(context).load(image).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.1f).placeholder(R.drawable.img_icon).fitCenter().into(imageView);

            ((ViewPager) container).addView(sliderView, position);
        }

        sliderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterface.afterCatBannerClick(position, view.getId());
            }
        });

        return sliderView;




    }
    public interface BannerOnClickInterface{
        void afterCatBannerClick(int pos, int type);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
