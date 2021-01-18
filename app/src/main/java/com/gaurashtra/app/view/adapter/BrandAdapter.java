package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.model.bean.HomePagebean.ShopByBrandDatum;
import com.gaurashtra.app.view.activity.BrandActivity;
import com.gaurashtra.app.view.activity.ProductByBrand;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private Context context;
    private List<ShopByBrandDatum> list=new ArrayList<>();


    public BrandAdapter(BrandActivity brandActivity, List<ShopByBrandDatum> brandlist) {
        this.context=brandActivity;
        this.list=brandlist;
    }

    @Override
    public BrandViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branditems,parent,false);
        BrandViewHolder viewHolder = new BrandViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( BrandViewHolder holder, int position) {
        if (position%2 != 0){
            holder.items.setBackgroundColor(Color.WHITE);
        }
        holder.textView.setText(list.get(position).getBrandName().replace("&amp;", "&"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class BrandViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout items;
        private TextView textView;
        private ImageView arrow;
        public BrandViewHolder(View view){
            super(view);
            items = itemView.findViewById(R.id.llitems);
            arrow = itemView.findViewById(R.id.ivarrow);
            textView=itemView.findViewById(R.id.tvCatName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, ProductByBrand.class);
                    intent.putExtra(Constants.PreferenceConstants.BRANDID, list.get(getLayoutPosition()).getBrandId());
                    intent.putExtra(Constants.PreferenceConstants.BRANDNAME, list.get(getLayoutPosition()).getBrandName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
