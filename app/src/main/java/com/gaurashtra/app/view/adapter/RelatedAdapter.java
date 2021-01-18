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
import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.bean.ProductDetails.Data;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

public class RelatedAdapter extends RecyclerView.Adapter<RelatedAdapter.RelatedViewHolder> {
    private Context context;
    private List<ProductRemainingResult.Result.RelatedProductDatum> related=new ArrayList<>();
    ProductDetailsInterface detailsInterface;
    String baseUrl="https://www.gaurashtra.com/image/cache/";
    PrefClass prefClass;
    String currency="";
    double currencyValue=1.000;
    double taxAmount;


    public RelatedAdapter(ProductDetailActivity productDetailActivity, List<ProductRemainingResult.Result.RelatedProductDatum> relateddata, ProductDetailActivity detailActivity) {
        this.context=productDetailActivity;
        this.related=relateddata;
        this.detailsInterface= detailActivity;
    }

    @NonNull
    @Override
    public RelatedViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relateditem,parent,false);
        RelatedViewHolder viewHolder = new RelatedViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( RelatedViewHolder relatedViewHolder, int i) {
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
        double tax = 0.00;
        if (related.get(i).getTaxRate() != null) {
            if (!related.get(i).getTaxRate().isEmpty() && !related.get(i).getTaxRate().contains("null")) {
                tax = Double.parseDouble(related.get(i).getTaxRate());
                double prodPrice = Double.parseDouble(related.get(i).getProductPrice());
                 taxAmount = tax * (prodPrice / 100);
            }
        }
        ImageView view=relatedViewHolder.imageView;
        String image=related.get(i).getProductImage();
        String addimage=image.replace(".jpg","-300x400.jpg");
        String TopImageList=baseUrl+addimage;
        Picasso.with(context).load(TopImageList).into(view);

        DecimalFormat df = new DecimalFormat("0.00");
        Double oldprice= Double.parseDouble(related.get(i).getProductPrice());
        Double spclprice= Double.parseDouble(related.get(i).getSpecialPrice());
        relatedViewHolder.itemname.setText(related.get(i).getProductName());
        if(related.get(i).getProductName().contains("&amp;")) {
            relatedViewHolder.itemname.setText(related.get(i).getProductName().replace("&amp:", " "));
        }
        relatedViewHolder.itemprice.setId(i);
        relatedViewHolder.itemprice.setText(currency+df.format((oldprice+taxAmount)*currencyValue));
        if(spclprice ==0.00) {
            relatedViewHolder.crossLine.setVisibility(View.GONE);
            relatedViewHolder.itemspecial.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                relatedViewHolder.itemprice.setTextColor(context.getColor(R.color.light_black));
            }
        }else{
            relatedViewHolder.crossLine.setVisibility(View.VISIBLE);
            relatedViewHolder.itemspecial.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                relatedViewHolder.itemprice.setTextColor(context.getColor(R.color.gray));
            }
            relatedViewHolder.itemspecial.setVisibility(View.VISIBLE);
            relatedViewHolder.discountPercentLayout.setVisibility(View.VISIBLE);
            Double discountPercentage= (oldprice-spclprice)/oldprice*100;
            int price= (int) (discountPercentage%1);
            if(price >0) {
                relatedViewHolder.tvDiscountPercent.setText(String.valueOf(discountPercentage*currencyValue)+"% OFF");
            }else{
                DecimalFormat df2 = new DecimalFormat("0");
                relatedViewHolder.tvDiscountPercent.setText(df2.format(discountPercentage*currencyValue)+"% OFF");
            }
            relatedViewHolder.itemspecial.setText(currency + " " + df.format((spclprice+taxAmount)*currencyValue));
        }
    }

    @Override
    public int getItemCount() {
        return  related.size();
    }

    public class RelatedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView itemname,itemprice,itemspecial,tvDiscountPercent;
        LinearLayout crossLine,discountPercentLayout;

        public RelatedViewHolder(View itemView){
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
            Log.e("ClickedRelatedProd",""+related.get(getLayoutPosition()).getProductId());
            int position= getLayoutPosition();
            String image=related.get(position).getProductImage();
            String addimage=image.replace(".jpg","-300x400.jpg");
            String TopImageList=baseUrl+addimage;
            TextView tvPrice=(TextView)v.findViewById(position);
            String productPrice= tvPrice.getText().toString();
            detailsInterface.displayDetails(SharedPreference.getUserid(context),
                    related.get(position).getProductId(),
                    related.get(position).getProductName(),
                    TopImageList,productPrice );
        }
    }
    public interface ProductDetailsInterface{
        void displayDetails(String user_id, String prod_id, String product_name, String product_image, String product_price);
    }
}
