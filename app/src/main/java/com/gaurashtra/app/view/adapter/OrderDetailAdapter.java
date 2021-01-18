package com.gaurashtra.app.view.adapter;

import android.content.Context;

import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaurashtra.app.view.activity.ProductDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.OrderBean.OrderedProductDetails;
import com.gaurashtra.app.view.activity.OrderDetailActivity;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.DetailViewHolder> {
    private Context context;
    ArrayList<OrderedProductDetails.Result.OrderData.Product> deliveredProducts;
    ReviewInterface reviewInterface;
    PrefClass prefClass;
    String currency="";
    HashMap<Integer, String> priceListMap= new HashMap<>();
    float currencyValue= (float) 1.000;

    public OrderDetailAdapter(OrderDetailActivity orderDetailActivity, ArrayList<OrderedProductDetails.Result.OrderData.Product> productList, OrderDetailActivity detailActivity) {
          this.context= orderDetailActivity;
          this.deliveredProducts= productList;
          this.reviewInterface= detailActivity;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_list_item,parent,false);
        DetailViewHolder viewHolder = new DetailViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, final int i) {
        prefClass= new PrefClass(context);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                currency=prefClass.getSelectedCurrency();
                String value= new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                currencyValue= Float.parseFloat(value);
                Log.e("currencyValue",":"+currencyValue);
                currency= currencyData.getSymbol().trim();
            }
        }
        String prodName=deliveredProducts.get(i).getProductName();
        if(deliveredProducts.get(i).getProductName().contains("&amp;")) {
            prodName= prodName.replace("amp;", "");
        }
        holder.tvProductName.setText(prodName);
        Double price= Double.parseDouble(deliveredProducts.get(i).getProductPrice());
        Double tax= Double.parseDouble(deliveredProducts.get(i).getProductTax());
        Double netprice= price+tax;
        holder.tvNetPrice.setText(currency+new DecimalFormat("0.00").format(netprice*currencyValue));
        priceListMap.put(i,currency+new DecimalFormat("0.00").format(netprice*currencyValue));
        holder.tvProdQty.setText(deliveredProducts.get(i).getProductQuantity());
        final String url= GlobalUtils.IMAGE_BASE_URL+deliveredProducts.get(i).getProductImage().replace(".jpg","-300x400.jpg");
        Glide.with(context).asBitmap().load(url).into(holder.ivProduct);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, ProductDetailActivity.class);
                intent.putExtra(Constants.PreferenceConstants.PRODUCTID, deliveredProducts.get(i).getProductId());
                intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, deliveredProducts.get(i).getProductName());
                intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG, url);
                context.startActivity(intent);
            }
        });
        if(deliveredProducts.get(i).getProductOptionId().isEmpty() || deliveredProducts.get(i).getProductOptionId().equalsIgnoreCase("0")){
            holder.optionNameLayout.setVisibility(View.GONE);
        }else{
            holder.optionNameLayout.setVisibility(View.VISIBLE);
            holder.tvOptionValue.setText(deliveredProducts.get(i).getProductOptionValueName().replace("&amp;","&"));
            holder.tvOptionLabel.setText(deliveredProducts.get(i).getProductOptionName().replace("&amp;","&"));
        }
    }

    @Override
    public int getItemCount() {
        return deliveredProducts.size();
    }


    public class DetailViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;
        private LinearLayout quantity, oldPriceLayout, writeReview,optionNameLayout;
        public TextView tvProductName, tvoldAmount, tvDiscPercent, tvNetPrice, tvProdQty, tvOptionLabel, tvOptionValue;
        public DetailViewHolder(View itemView){
            super(itemView);
           quantity = itemView.findViewById(R.id.llquantity);
           tvProductName= itemView.findViewById(R.id.tvProductName);
           tvoldAmount= itemView.findViewById(R.id.tvoldAmount);
           tvDiscPercent= itemView.findViewById(R.id.tv_dis_percent);
           tvNetPrice= itemView.findViewById(R.id.tv_net_price);
           tvProdQty= itemView.findViewById(R.id.tv_product_quantity);
           ivProduct= itemView.findViewById(R.id.ivProductImage);
           oldPriceLayout= itemView.findViewById(R.id.ll_old_price);
           writeReview= itemView.findViewById(R.id.ll_write_review);
            optionNameLayout = itemView.findViewById(R.id.option_name_layout);
            tvOptionLabel = itemView.findViewById(R.id.tv_option_type);
            tvOptionValue = itemView.findViewById(R.id.tv_option_value);
           quantity.setVisibility(View.VISIBLE);
           oldPriceLayout.setVisibility(View.GONE);
           writeReview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   reviewInterface.openWriteReview(getAdapterPosition(), deliveredProducts.get(getAdapterPosition()).getProductId());
               }
           });
        }
    }

    public interface ReviewInterface {
        void openWriteReview(int adapterPosition, String productId);
    }
}
