package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.model.bean.HomePagebean.HomepageDTO;
import com.gaurashtra.app.model.bean.HomePagebean.SliderDatum;
import com.gaurashtra.app.view.activity.BrandListActivity;
import com.gaurashtra.app.view.activity.ProductDetailActivity;
import com.smarteist.autoimageslider.SliderViewAdapter;
//import com.smarteist.imageslider.Model.SliderItem;
import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<SliderDatum> mSliderItems = new ArrayList<>();

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderDatum> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(SliderDatum sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderDatum sliderItem = mSliderItems.get(position);

//        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getSliderImage())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
        viewHolder.itemView.setId(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linkId = mSliderItems.get(position).getLinkId();
//                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
                try {
                        if (mSliderItems.get(position).getLinkType().equalsIgnoreCase("Product")) {
                            Intent intentPro = new Intent(context, ProductDetailActivity.class);
                            intentPro.putExtra(Constants.PreferenceConstants.PRODUCTID, linkId);
                            intentPro.putExtra(Constants.PreferenceConstants.PRODUCTNAME, "");
                            context.startActivity(intentPro);
                        } else if (mSliderItems.get(position).getLinkType().equalsIgnoreCase("Category")) {
                            Intent intent = new Intent(context, BrandListActivity.class);
                            intent.putExtra(Constants.PreferenceConstants.CATEGORYID, linkId);
                            intent.putExtra(Constants.PreferenceConstants.CATEGORYNAME, " ");
                            context.startActivity(intent);
                        }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}
