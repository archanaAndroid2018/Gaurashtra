package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaurashtra.app.view.fragment.MyReviewFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.MyReviewListBean.ReviewData;
import com.gaurashtra.app.view.fragment.ToBeReviewFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MyReviewsAdapter extends RecyclerView.Adapter<MyReviewsAdapter.MyViewHolder >{
    private Context context;
    private ArrayList<ReviewData> myReviewList = new ArrayList<>();
    ImageView Rating1,Rating2,Rating3,Rating4,Rating5;
    PrefClass prefClass;
    String currency="", currencySymbol="\u20B9";
    float currencyValue= (float) 1.000;
    Float actualCurrValue;
    MyReviewInterface reviewInterface;
    String reviewText="";
    int listType=0;
    final int MYREVIEW=1, TOBEREVIEWED=2;

    public MyReviewsAdapter(FragmentActivity activity, ArrayList<ReviewData> reviewList, MyReviewFragment myReviewFragmentClass) {
        this.context = activity;
        this.myReviewList = reviewList;
        this.reviewInterface= myReviewFragmentClass;
        this.listType= MYREVIEW;
    }

    public MyReviewsAdapter(FragmentActivity activity, ArrayList<ReviewData> reviewList, ToBeReviewFragment toBeReviewFragment) {
        this.context = activity;
        this.myReviewList = reviewList;
        this.reviewInterface= toBeReviewFragment;
        listType= TOBEREVIEWED;
    }


    @Override
    public MyReviewsAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myreview,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder( MyReviewsAdapter.MyViewHolder holder, int position) {
        prefClass = new PrefClass(context);
        try {
            if(!prefClass.getCurrencyDataList().isEmpty()) {
                Type type = new TypeToken<List<GetCurrencyList.Result.CurrencyData>>() {
                }.getType();
                List<GetCurrencyList.Result.CurrencyData> currencyDataList = (List<GetCurrencyList.Result.CurrencyData>) (new Gson()).fromJson(prefClass.getCurrencyDataList(), type);
                for (GetCurrencyList.Result.CurrencyData currencyData : currencyDataList) {
                    if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())) {
                        currency = prefClass.getSelectedCurrency();
                        actualCurrValue = Float.parseFloat(currencyData.getValue());
                        String value = new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                        currencyValue = Float.parseFloat(value);
                        Log.e("currencyValue", ":" + currencyValue);
                        currencySymbol = currencyData.getSymbol().trim();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
            String imagePath = myReviewList.get(position).getProductImage().replace(".jpg", "-300x400.jpg");

            reviewText= myReviewList.get(position).getReviewText();
            String prodName = myReviewList.get(position).getProductName();
            if(prodName.contains("&amp;")){
                prodName.replace("&amp;", "&");
            }
//            double prodPrice = Double.parseDouble(myReviewList.get(position).getProductPrice());
//            double tax = Double.parseDouble(myReviewList.get(position).getProductTax());
//            double netPrice= prodPrice+tax;
//            holder.tvPaymentMethod.setText(myReviewList.get(position).getPaymentMethod());
//            holder.tvProductPrice.setText(currencySymbol+new DecimalFormat("0").format(netPrice*currencyValue));
//            holder.tvOrderId.setText(myReviewList.get(position).getOrderId());
            holder.tvProductName.setText(prodName);
            if(!myReviewList.get(position).getDelivered().isEmpty()) {
                holder.tvDeliveryStatus.setText("Delivered on: " + myReviewList.get(position).getDelivered());
            }else{
                holder.tvDeliveryStatus.setText("Ordered on: "+myReviewList.get(position).getOrderDate());
            }
            double count = Double.valueOf(myReviewList.get(position).getReviewRating());
            reviewText = myReviewList.get(position).getReviewText();
            if (listType== MYREVIEW) {
                holder.writeReview.setVisibility(View.GONE);
                if(!reviewText.isEmpty()){
                    holder.btnEdit.setVisibility(View.VISIBLE);
                    holder.revieTextlayout.setVisibility(View.VISIBLE);
                    holder.tvReviewText.setText(reviewText);
                }else{
                    holder.btnEdit.setVisibility(View.GONE);
                    holder.revieTextlayout.setVisibility(View.GONE);
                }
                ShowRating(count);

            } else if (listType == TOBEREVIEWED) {
                holder.btnEdit.setVisibility(View.GONE);
                holder.revieTextlayout.setVisibility(View.GONE);
                holder.writeReview.setVisibility(View.VISIBLE);
            }
            if (!imagePath.isEmpty()) {
                Glide.with(context).load(GlobalUtils.IMAGE_BASE_URL+imagePath).skipMemoryCache(true).into(holder.ivProductImg);
            }
    }

    @Override
    public int getItemCount() {
        return myReviewList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvProductName, tvBrandName, tvProductPrice, tvDeliveryStatus,
                tvPaymentMethod, tvReviewText, tvOrderId;
        LinearLayout writeReview, revieTextlayout;
        ImageButton btnEdit ;
        ImageView ivProductImg;
        public MyViewHolder( View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tv_Product_Name);
            tvDeliveryStatus = itemView.findViewById(R.id.delivered_date);
            tvOrderId = itemView.findViewById(R.id.tv_order_no);
            writeReview= itemView.findViewById(R.id.ll_write_review);
            btnEdit= itemView.findViewById(R.id.ibtn_edit);
            ivProductImg= itemView.findViewById(R.id.iv_review_prod_image);
            revieTextlayout= itemView.findViewById(R.id.review_layout);

            Rating1 = itemView.findViewById(R.id.rating1);
            Rating2 = itemView.findViewById(R.id.rating2);
            Rating3 = itemView.findViewById(R.id.rating3);
            Rating4 = itemView.findViewById(R.id.rating4);
            Rating5 = itemView.findViewById(R.id.rating5);
            tvReviewText = itemView.findViewById(R.id.tv_review);
            btnEdit.setOnClickListener(this);
            writeReview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String action="";
            if(view.getId()== R.id.ibtn_edit){
                action="edit";
            }else{
                action="add";
            }
            int adapterPosition= getAdapterPosition();
            String productId= myReviewList.get(getAdapterPosition()).getProductId();
            reviewInterface.openWriteReview(adapterPosition,productId,action);
        }
    }


    private void ShowRating(double count) {
        if (count == 1.00) {
            Rating1.setVisibility(View.VISIBLE);
        } else if (count == 2.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
        } else if (count == 3.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
        } else if (count == 4.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
            Rating4.setVisibility(View.VISIBLE);
        } else if (count == 5.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
            Rating4.setVisibility(View.VISIBLE);
            Rating5.setVisibility(View.VISIBLE);
        } else if (count > 0.00 && count < 1.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating1.setImageResource(R.drawable.halfstar);
        } else if (count > 1.00 && count < 2.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating1.setImageResource(R.drawable.star);
            Rating2.setVisibility(View.VISIBLE);
            Rating2.setImageResource(R.drawable.halfstar);
        } else if (count > 2.00 && count < 3.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating2.setImageResource(R.drawable.star);
            Rating3.setVisibility(View.VISIBLE);
            Rating3.setImageResource(R.drawable.halfstar);
        } else if (count > 3.00 && count < 4.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
            Rating3.setImageResource(R.drawable.star);
            Rating4.setVisibility(View.VISIBLE);
            Rating4.setImageResource(R.drawable.halfstar);
        } else if (count > 4.00 && count < 5.00) {
            Rating1.setVisibility(View.VISIBLE);
            Rating2.setVisibility(View.VISIBLE);
            Rating3.setVisibility(View.VISIBLE);
            Rating4.setVisibility(View.VISIBLE);
            Rating4.setImageResource(R.drawable.star);
            Rating5.setVisibility(View.VISIBLE);
            Rating5.setImageResource(R.drawable.halfstar);
        }
    }
    public interface MyReviewInterface {
        void openWriteReview(int adapterPosition, String productId, String action);
    }
}
