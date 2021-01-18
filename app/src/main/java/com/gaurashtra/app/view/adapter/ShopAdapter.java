package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.model.bean.HomePagebean.ShopByBrandDatum;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.ProductByBrand;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopDataViewHolder> {
    private Context context;
    private List<ShopByBrandDatum> shopBrand=new ArrayList<>();

//    public ShopAdapter(Context context){
//        this.context = context;
//    }

    public ShopAdapter(HomeActivity homeActivity, List<ShopByBrandDatum> shapBrand) {
        this.context=homeActivity;
        this.shopBrand=shapBrand;
    }

    @NonNull
    @Override
    public ShopDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopitemlist,parent,false);
        ShopDataViewHolder viewHolder = new ShopDataViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopDataViewHolder holder, int position) {
        holder.textView.setText(shopBrand.get(position).getBrandName().replace("&amp;","&"));

    }

    @Override
    public int getItemCount() {
        return shopBrand.size();
    }


    public class ShopDataViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ShopDataViewHolder(View itemView){
            super(itemView);

            textView=(TextView)itemView.findViewById(R.id.TextBrand);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, ProductByBrand.class);
                    intent.putExtra(Constants.PreferenceConstants.BRANDID, shopBrand.get(getLayoutPosition()).getBrandId());
                    intent.putExtra(Constants.PreferenceConstants.BRANDNAME, shopBrand.get(getLayoutPosition()).getBrandName());
                    context.startActivity(intent);
                }
            });
        }
    }
}
