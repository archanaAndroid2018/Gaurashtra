package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.ProductBasicDetailsResult;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class OfferDiscountAdapter extends RecyclerView.Adapter<OfferDiscountAdapter.ViewHolder> {
    ArrayList<ProductBasicDetailsResult.Result.StaticContentDatum> offerList;
    Context context;

    public OfferDiscountAdapter(ProductDetailActivity activity, ArrayList<ProductBasicDetailsResult.Result.StaticContentDatum> staticData) {
      this.context = activity;
      this.offerList = staticData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.offers_row_layout,parent,false);
        OfferDiscountAdapter.ViewHolder vh= new OfferDiscountAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.tvOfferTitle.setText(offerList.get(position).getTitle());
      holder.tvOfferContent.setText(offerList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return offerList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOfferTitle, tvOfferContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOfferTitle = itemView.findViewById(R.id.tv_offer_title);
            tvOfferContent = itemView.findViewById(R.id.tv_offer_text);
        }
    }
}
