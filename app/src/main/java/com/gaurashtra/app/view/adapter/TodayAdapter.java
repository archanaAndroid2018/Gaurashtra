package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.HomePagebean.TodayProduct;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

public class TodayAdapter extends RecyclerView.Adapter<TodayAdapter.TodayViewHolderClass> {
    private GlobalUtils globalUtils;
    private Context context;
    PrefClass prefClass;
    String currency="",currencySymbol="\u20B9",spclPrice="",prodPrice="",oldPrice="";
    float currencyValue= (float) 1.000;
    private List<TodayProduct> todayList;

    public TodayAdapter(Context context, List<TodayProduct> todayList){
        this.context = context;
        this.todayList = todayList;
    }

    @NonNull
    @Override
    public TodayViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comonproductitem,parent,false);
        return new TodayViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayViewHolderClass holder, int position) {
        prefClass= new PrefClass(context);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                currency=prefClass.getSelectedCurrency();
                String value= new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                currencyValue= Float.parseFloat(value);
                Log.e("currencyValue",":"+currencyValue);
                currencySymbol= currencyData.getSymbol().trim();
            }
        }
        String imagebaseurl= GlobalUtils.IMAGE_BASE_URL;
        ImageView view=holder.imageView;
        String imageURL=todayList.get(position).getProduct_image();
        Log.e("SellingURl",""+imageURL);
        String addimage=imageURL.replace(".jpg","-300x400.jpg");
        String image=imagebaseurl+addimage;
        Picasso.with(context).load(image).into(view);
        String prodName=todayList.get(position).getProduct_name();
        if(prodName.contains("&amp;")){
            prodName.replace("amp;","");
        }
        holder.textproduct.setText(prodName);
        spclPrice= todayList.get(position).getSpecial_price();
//        if(todayList.get(position).getOptionId().equalsIgnoreCase("0")){
//            holder.tvOptionAvail.setVisibility(View.GONE);
//        }else{
//            holder.tvOptionAvail.setVisibility(View.VISIBLE);
//        }
        if(Double.parseDouble(spclPrice)==0.0000) {
            prodPrice = String.valueOf(todayList.get(position).getProduct_price());
            double tax= Double.parseDouble(todayList.get(position).getTax_rate());
            double taxPrice= Double.parseDouble(prodPrice)*(tax/100);
            double netPrice= Double.parseDouble(new DecimalFormat("0.00").format(Float.parseFloat(prodPrice)))+taxPrice;
            holder.oldPriceLayout.setVisibility(View.GONE);
//            holder.tvOffer.setVisibility(View.GONE);
            holder.productprice.setText(currencySymbol+new DecimalFormat("0.00").format(netPrice*currencyValue));
        }else {
            oldPrice = todayList.get(position).getProduct_price();
            prodPrice = todayList.get(position).getSpecial_price();
            double tax= Double.parseDouble(todayList.get(position).getTax_rate());
            double taxPrice= Double.parseDouble(oldPrice)*(tax/100);
            double netPriceOld= Double.parseDouble(oldPrice)+taxPrice;

            double taxPriceSpcl= Double.parseDouble(prodPrice)*(tax/100);
            double netPriceSpcl= Double.parseDouble(prodPrice)+taxPriceSpcl;
            // offerPercent= String.valueOf(Double.parseDouble(oldPrice)-Double.parseDouble(prodPrice)*100/Double.parseDouble(oldPrice))+"% OFF";
            holder.oldPriceLayout.setVisibility(View.VISIBLE);
            holder.tvoldPrice.setVisibility(View.VISIBLE);
//            holder.tvOffer.setText(todayList.get(position).getCartProductPriceText());
            holder.tvoldPrice.setText(currencySymbol +new DecimalFormat("0.00").format(netPriceOld*currencyValue));
            holder.productprice.setText(currencySymbol+new DecimalFormat("0.00").format(netPriceSpcl*currencyValue));
        }

        holder.productprice.setText(prodPrice);
        holder.prod_discount.setText(todayList.get(position).getDiscount_price());
    }

    @Override
    public int getItemCount() {
        return todayList.size();
    }


    public class TodayViewHolderClass extends RecyclerView.ViewHolder {
        ImageView imageView;
        FrameLayout frameLayout,oldPriceLayout;
        TextView textproduct,productprice,prod_discount,tvoldPrice;
        private LinearLayout cartAddButton;
        public TodayViewHolderClass(View view){
            super(view);
            frameLayout = itemView.findViewById(R.id.fldata);
            imageView=(ImageView)itemView.findViewById(R.id.ivProductImage);
            oldPriceLayout= itemView.findViewById(R.id.fldiscount);
            tvoldPrice= itemView.findViewById(R.id.tvoldAmount);
            textproduct=(TextView)itemView.findViewById(R.id.tvProductName);
            productprice=(TextView)itemView.findViewById(R.id.Product_price);
            cartAddButton  = (LinearLayout) itemView.findViewById(R.id.lladdtocart);
            prod_discount=(TextView)itemView.findViewById(R.id.tvoldAmount);

            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                int pos = getAdapterPosition();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra(Constants.PreferenceConstants.PRODUCTID,todayList.get(pos).getProduct_id());
                intent.putExtra(Constants.PreferenceConstants.PRODUCTID,todayList.get(pos).getProduct_name());
                context.startActivity(intent);
                }
            });
        }
    }
}
