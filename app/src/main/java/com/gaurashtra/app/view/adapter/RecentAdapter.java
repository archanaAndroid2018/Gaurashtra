package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.os.Build;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaurashtra.app.model.bean.ProductRemainingResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.ProductDetails.Data;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {
    private Context context;
    private List<ProductRemainingResult.Result.RecentlyPurchasedDatum> mrecentdata;
    RecentprodDetailsInterface recentprodDetailsInterface;
    String baseUrl="https://www.gaurashtra.com/image/cache/";
    PrefClass prefClass;
    String currency="";
    double currencyValue=1.000;
    double taxAmount;


    public RecentAdapter(ProductDetailActivity productDetailActivity, List<ProductRemainingResult.Result.RecentlyPurchasedDatum> recentlydata, ProductDetailActivity detailActivity) {
        this.context=productDetailActivity;
        this.mrecentdata=recentlydata;
        this.recentprodDetailsInterface= detailActivity;
    }


    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.relateditem,parent,false);
        RecentViewHolder viewHolder = new RecentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( RecentViewHolder recentViewHolder, int i) {
        prefClass= new PrefClass(context);
        if(!prefClass.getCurrencyDataList().isEmpty()) {
            Type type = new TypeToken<List<GetCurrencyList.Result.CurrencyData>>() {
            }.getType();
            List<GetCurrencyList.Result.CurrencyData> currencyDataList = (List<GetCurrencyList.Result.CurrencyData>) (new Gson()).fromJson(prefClass.getCurrencyDataList(), type);
            for (GetCurrencyList.Result.CurrencyData currencyData : currencyDataList) {
                if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())) {
                    currency = prefClass.getSelectedCurrency();
                    String value = new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                    currencyValue = Float.parseFloat(value);
                    Log.e("currencyValue", ":" + currencyValue);
                    currency = currencyData.getSymbol().trim();
                }
            }
        }
        double tax = 0.00;
        if (mrecentdata.get(i).getTaxRate() != null) {
            if (!mrecentdata.get(i).getTaxRate().isEmpty() && !mrecentdata.get(i).getTaxRate().contains("null")) {
        tax = Double.parseDouble(mrecentdata.get(i).getTaxRate());
        double prodPrice = Double.parseDouble(mrecentdata.get(i).getProductPrice());
        taxAmount = tax * (prodPrice / 100);
            }
        }
        recentViewHolder.itemprice.setId(i);
        ImageView view=recentViewHolder.imageView;
        String image=mrecentdata.get(i).getProductImage();
        String addimage=image.replace(".jpg","-300x400.jpg");
        String TopImageList=baseUrl+addimage;
        Log.e("ImageURL",""+TopImageList);
        Picasso.with(context).load(TopImageList).into(view);
        DecimalFormat df = new DecimalFormat("0.00");
        Double oldprice= Double.parseDouble(mrecentdata.get(i).getProductPrice());
        Double spclprice= Double.parseDouble(mrecentdata.get(i).getSpecialPrice());

        String pName= mrecentdata.get(i).getProductName().replace("amp;","");
        recentViewHolder.itemname.setText(pName);
        recentViewHolder.itemprice.setText(currency+ df.format((oldprice+taxAmount)*currencyValue));
        if(spclprice==0.00) {
            recentViewHolder.crossLine.setVisibility(View.GONE);
            recentViewHolder.itemspecial.setVisibility(View.GONE);
            recentViewHolder.discountPercentLayout.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                recentViewHolder.itemprice.setTextColor(context.getColor(R.color.light_black));
            }
        }else {
            recentViewHolder.crossLine.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                recentViewHolder.itemprice.setTextColor(context.getColor(R.color.gray));
            }
            recentViewHolder.itemspecial.setVisibility(View.VISIBLE);
            recentViewHolder.discountPercentLayout.setVisibility(View.VISIBLE);
            Double discountPercentage= (oldprice-spclprice)/oldprice*100;
            int price= (int) (discountPercentage%1);
            if(price >0) {
                recentViewHolder.tvDiscountPercent.setText((discountPercentage*currencyValue)+"% OFF");
            }else{
                DecimalFormat df2 = new DecimalFormat("0");
                recentViewHolder.tvDiscountPercent.setText(df2.format(discountPercentage*currencyValue)+"% OFF");
            }
            recentViewHolder.itemspecial.setText(currency+ df.format(spclprice*currencyValue));
        }
    }

    @Override
    public int getItemCount() {
        return mrecentdata.size();
    }

    public class RecentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView itemname,itemprice,itemspecial, tvDiscountPercent;
        LinearLayout crossLine, discountPercentLayout;

        public RecentViewHolder(View itemView){
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.ivimage);
            itemname=(TextView)itemView.findViewById(R.id.tvMerchant);
            itemprice=(TextView)itemView.findViewById(R.id.tvoldAmount);
            itemspecial=(TextView)itemView.findViewById(R.id.tvOriginalPrize);
            tvDiscountPercent= (TextView)itemView.findViewById(R.id.tv_discount_percentage);
            crossLine= (LinearLayout)itemView.findViewById(R.id.cross_line);
            discountPercentLayout= (LinearLayout)itemView.findViewById(R.id.dis_percentage_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos= getLayoutPosition();
            String image=mrecentdata.get(pos).getProductImage();
            String addimage=image.replace(".jpg","-300x400.jpg");
            String TopImageList=baseUrl+addimage;
            TextView tvprice= v.findViewById(pos);
            String price= tvprice.getText().toString();
            recentprodDetailsInterface.recentProdDetails(mrecentdata.get(pos).getProductId(), mrecentdata.get(pos).getProductName(),TopImageList, price);
        }
    }
    public interface RecentprodDetailsInterface{
        void recentProdDetails(String productId, String product_name, String product_image, String prod_price);
    }
}
