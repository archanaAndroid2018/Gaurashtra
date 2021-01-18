package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.content.Intent;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.model.bean.HomePagebean.TopSellingDatum;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

public class FirstHorizontalHomeAdapter extends RecyclerView.Adapter<FirstHorizontalHomeAdapter.HorizontalViewHolder> {
    private Context context;
    private List<TopSellingDatum> mtoplist = new ArrayList<>();
    private List<TopSellingDatum> recolist = new ArrayList<>();
    private List<TopSellingDatum> relist = new ArrayList<>();
    private final int Topselling = 1;
    private final int Todaydeal = 2;
    private final int HairCare = 3;
    private final int recentlyviewed = 4;
    private final int recommended = 5;
    int Type = 0;
    Boolean TYPE = false;
    String productPrice;
    String baseUrl = "https://www.gaurashtra.com/image/cache/";
    PrefClass prefClass;
    String currency="";
    float currencyValue= (float) 1.000;

    public FirstHorizontalHomeAdapter(HomeActivity homeActivity, List<TopSellingDatum> datum, int listType) {
        this.context = homeActivity;
        this.mtoplist = datum;
        this.recolist = datum;
        this.Type = listType;

    }


    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontalitem, parent, false);
        HorizontalViewHolder viewHolder = new HorizontalViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, final int i) {
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

        String prodName= mtoplist.get(i).getProductName();
        if(prodName.contains("&amp;")){
            prodName = mtoplist.get(i).getProductName().replace("&amp;","&");
        }
        holder.productname.setText(prodName);

        holder.originalprice.setId(i);
        holder.comonView.setMinimumHeight(100);
        holder.itemView.setMinimumHeight(100);
        if (Type == Topselling) {
            String image = mtoplist.get(i).getProductImage();
            String addimage = image.replace(".jpg", "-300x300.jpg");
            String TopImageList = baseUrl + addimage;
            Log.e("ImageURL", "" + TopImageList);

            String S_price = mtoplist.get(i).getSpecialPrice();
            String price = mtoplist.get(i).getProductPrice();
            double originalprice = Float.parseFloat(price);
            double SpecialPrice = Float.parseFloat(S_price);
            float tax= Float.parseFloat(mtoplist.get(i).getTaxRate());
            Log.e("SpecialPrice", "" + S_price);

            if (SpecialPrice == 0.00) {
                double taxAmount= ((tax / 100) * originalprice);
                String netPrice= new DecimalFormat("##.##").format((originalprice+taxAmount)*currencyValue);
                holder.originalprice.setText(currency+(netPrice));
                holder.discountPrice.setVisibility(View.GONE);
            } else if (SpecialPrice > 0.00) {
                double forice = originalprice-SpecialPrice;
                double fprice = ((forice/originalprice)*100)*currencyValue;
                String p1 = mtoplist.get(i).getProductPrice();
                String p2 = mtoplist.get(i).getSpecialPrice();
                double pprice = Double.parseDouble(p1);
                double oprice = Double.parseDouble(p2);
                double taxAmount= oprice*(tax/100);
                holder.price.setText(new DecimalFormat("##.##").format(pprice*currencyValue));
                holder.originalprice.setText(currency+new DecimalFormat("##.##").format((oprice+taxAmount)*currencyValue));
                holder.productoff.setText(new DecimalFormat("##.##").format(fprice)+"% OFF");
            }
            //String percent_discount=String.valueOf(f);
            Glide.with(context).load(TopImageList).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.1f).placeholder(R.drawable.img_icon).fitCenter().into(holder.productimage);
        } else if (Type == recommended) {
            ImageView view = holder.productimage;
            String image = mtoplist.get(i).getProductImage();
            String addimage = image.replace(".jpg", "-300x300.jpg");
            String TopImageList = baseUrl + addimage;
            Log.e("ImageURL2", "" + TopImageList);
//            holder.productname.setText(mtoplist.get(i).getProductName());

            String S_price = mtoplist.get(i).getSpecialPrice();
            String price = mtoplist.get(i).getProductPrice();
            double originalprice = Double.parseDouble(price);
            double SpecialPrice = Double.parseDouble(S_price);
            double tax=0;
            if (mtoplist!=null) {
                if (mtoplist.get(i).getTaxRate()!=null) {
                    if (!mtoplist.get(i).getTaxRate().isEmpty())
                        tax = Double.parseDouble(mtoplist.get(i).getTaxRate());
                }
            }
            Log.e("SpecialPrice", "" + S_price);
            if (SpecialPrice == 0.0000) {
                double taxAmount= originalprice * (tax/100);
                String netPrice= new DecimalFormat("##.##").format((originalprice+taxAmount)*currencyValue);
                holder.originalprice.setText(currency+(netPrice));
                holder.discountPrice.setVisibility(View.GONE);
            } else if (SpecialPrice > 0.00) {
                holder.originalprice.setVisibility(View.VISIBLE);
                double forice = originalprice-SpecialPrice;
                double fprice = ((forice/originalprice)*100)*currencyValue;
                String p1 = mtoplist.get(i).getProductPrice();
                String p2 = mtoplist.get(i).getSpecialPrice();
                double pprice = Double.parseDouble(p1);
                double oprice = Double.parseDouble(p2);
                double taxAmount= oprice*(tax/100);
                double oldTaxAmount= pprice*(tax/100);
                holder.price.setText(currency+new DecimalFormat("##.##").format((pprice+oldTaxAmount)*currencyValue));
                holder.originalprice.setText(currency+new DecimalFormat("##.##").format((oprice+taxAmount)*currencyValue));
                holder.productoff.setText(new DecimalFormat("##.##").format(fprice)+"% OFF");
            }
            Glide.with(context).load(TopImageList).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.5f).placeholder(R.drawable.img_icon).fitCenter().into(view);
//            Picasso.with(context).load(TopImageList).into(view);

        } else if (Type == HairCare) {
            ImageView view = holder.productimage;
            String baseUrl = "https://www.gaurashtra.com/image/cache/";
            String image = mtoplist.get(i).getProductImage();
            String addimage = image.replace(".jpg", "-300x300.jpg");
            String TopImageList = baseUrl + addimage;
//            holder.productname.setText(mtoplist.get(i).getProductName());

            String S_price = mtoplist.get(i).getSpecialPrice();
            String price = mtoplist.get(i).getProductPrice();
            double tax= Double.parseDouble(mtoplist.get(i).getTaxRate());
            double originalprice = Double.parseDouble(price);
            float SpecialPrice = Float.parseFloat(S_price);
            Log.e("SpecialPrice", "" + S_price);
            if (SpecialPrice == 0.0000) {
                double taxAmount= originalprice*(tax/100);
                String netPrice= new DecimalFormat("##.##").format((originalprice+taxAmount)*currencyValue);
                holder.originalprice.setText(currency+(netPrice));
                holder.discountPrice.setVisibility(View.GONE);
            } else if (SpecialPrice > 0.00) {
                double forice = originalprice-SpecialPrice;
                double fprice = ((forice/originalprice)*100)*currencyValue;
                String p1 = mtoplist.get(i).getProductPrice();
                String p2 = mtoplist.get(i).getSpecialPrice();
                double pprice = Double.parseDouble(p1);
                double oprice = Double.parseDouble(p2);
                double taxAmount= pprice*(tax/100);
                holder.price.setText(new DecimalFormat("##.##").format(pprice*currencyValue));
                holder.originalprice.setText(currency+new DecimalFormat("##.##").format((oprice+taxAmount)*currencyValue));
                holder.productoff.setText(new DecimalFormat("##.##").format(fprice)+"% OFF");
            }
            Glide.with(context).load(TopImageList).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.5f).placeholder(R.drawable.img_icon).fitCenter().into(view);

        } else if (Type == recentlyviewed) {
            ImageView view = holder.productimage;
            String baseUrl = "https://www.gaurashtra.com/image/cache/";
            String image = mtoplist.get(i).getProductImage();
            String addimage = image.replace(".jpg", "-300x300.jpg");
            String TopImageList = baseUrl + addimage;
//            holder.productname.setText(mtoplist.get(i).getProductName());

            String S_price = mtoplist.get(i).getSpecialPrice();
            String price = mtoplist.get(i).getProductPrice();
            float originalprice = Float.parseFloat(price);
            float SpecialPrice = Float.parseFloat(S_price);
            float tax= Float.parseFloat(mtoplist.get(i).getTaxRate());
            Log.e("SpecialPrice", "" + S_price);
            if (SpecialPrice == 0.00) {
                double taxAmount= (tax / 100) * originalprice;
                String netPrice= new DecimalFormat("##.##").format((originalprice+taxAmount)*currencyValue);
                holder.originalprice.setText(currency+(netPrice));
                holder.discountPrice.setVisibility(View.GONE);
            } else if (SpecialPrice > 0.00) {
                float forice = originalprice-SpecialPrice;
                float fprice = ((forice/originalprice)*100)*currencyValue;
                String p1 = mtoplist.get(i).getProductPrice();
                String p2 = mtoplist.get(i).getSpecialPrice();
                double pprice = Double.parseDouble(p1);
                double oprice = Double.parseDouble(p2);
                holder.price.setText(new DecimalFormat("##.##").format(pprice*currencyValue));
                double taxAmount= oprice*(tax/100);
                holder.originalprice.setText(currency+new DecimalFormat("##.##").format((oprice+taxAmount)*currencyValue));
                holder.productoff.setText(new DecimalFormat("##.##").format(fprice)+"% OFF");
            }
            Glide.with(context).load(TopImageList).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.5f).placeholder(R.drawable.img_icon).fitCenter().into(view);
        }
    }

    @Override
    public int getItemCount() {
        return mtoplist.size();
    }


    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView productimage;
        CardView comonView;
        LinearLayout discountPrice, mainlayout;
        TextView productname, originalprice, productoff, price;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
            mainlayout= itemView.findViewById(R.id.main_layout);
            comonView = itemView.findViewById(R.id.cvlist);
            productimage = (ImageView) itemView.findViewById(R.id.ivimage);
            productname = (TextView) itemView.findViewById(R.id.tvMerchant);
            productoff = (TextView) itemView.findViewById(R.id.tvAmount);
            originalprice = (TextView) itemView.findViewById(R.id.tvOriginalPrize);
            price = (TextView) itemView.findViewById(R.id.tvoldAmount);
            discountPrice = itemView.findViewById(R.id.llLineOff);
            comonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    TextView tvPrice=(TextView)v.findViewById(pos);
                    String image = mtoplist.get(getAdapterPosition()).getProductImage();
                    String addimage = image.replace(".jpg", "-300x400.jpg");
                    String TopImageList = baseUrl + addimage;
                    productPrice= tvPrice.getText().toString();
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID,mtoplist.get(pos).getProductId());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME,mtoplist.get(pos).getProductName());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG,TopImageList);
                    intent.putExtra(Constants.PreferenceConstants.PRODNETPRICE, productPrice);
                    context.startActivity(intent);
                }
            });
        }
    }
}
