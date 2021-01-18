package com.gaurashtra.app.view.adapter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.SearchProductResult;
import com.gaurashtra.app.view.fragment.SearchFragment;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.SearchViewHolder> {
    Context context;
    List<SearchProductResult.Result.ProductDatum> searchProdList;
    SearchProductInterface searchProductInterface;
    String prodId,prodName;


    public SearchProductAdapter(Activity activity, List<SearchProductResult.Result.ProductDatum> searchProductList, SearchFragment fragment) {
        this.context= activity;
        this.searchProdList=searchProductList;
        this.searchProductInterface= fragment;
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_item_layout,viewGroup,false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int i) {
        String prodName= searchProdList.get(i).getProductName();
        if(prodName.contains("&amp;")){
            prodName = searchProdList.get(i).getProductName().replace("&amp;","&");
        }
        holder.tvProductName.setText(prodName);
        String img= GlobalUtils.IMAGE_BASE_URL+searchProdList.get(i).getProductImage();
        String imageUrl= img.replace(".jpg","-300x300.jpg");
        Glide.with(context).load(imageUrl).placeholder(R.drawable.img_icon).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return searchProdList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName;
        ImageView imgProduct;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName= itemView.findViewById(R.id.tv_search_prod_name);
            imgProduct= itemView.findViewById(R.id.iv_search_prod_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prodId= searchProdList.get(getAdapterPosition()).getProductId();
                    prodName= searchProdList.get(getAdapterPosition()).getProductName();
                    String prodImage= GlobalUtils.IMAGE_BASE_URL+searchProdList.get(getAdapterPosition()).getProductImage();
                    searchProductInterface.onClickProduct(prodId,prodName, prodImage);

                }
            });
        }
    }
    public interface SearchProductInterface{
        void onClickProduct(String prodId, String prodName, String prodImage);
    }
}
