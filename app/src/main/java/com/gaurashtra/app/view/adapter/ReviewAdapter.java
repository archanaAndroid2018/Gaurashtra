package com.gaurashtra.app.view.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.ProductDetails.Datum;
import com.gaurashtra.app.model.bean.ProductRemainingResult;
import com.gaurashtra.app.view.activity.AllReviewsActivity;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolderClass> {
    Context context;
    ArrayList<ProductRemainingResult.Result.Product.Review.Datum> reviewList;
    int listCount=0;


    public ReviewAdapter(AllReviewsActivity allReviewsActivity, ArrayList<ProductRemainingResult.Result.Product.Review.Datum> reviewList) {
        this.context=allReviewsActivity;
        this.reviewList= reviewList;
        listCount=0;
    }

    public ReviewAdapter(ProductDetailActivity productDetailActivity, List<ProductRemainingResult.Result.Product.Review.Datum> reviewList, int limit) {
        this.context=productDetailActivity;
        this.reviewList= (ArrayList<ProductRemainingResult.Result.Product.Review.Datum>) reviewList;
        listCount=limit;
    }

    @NonNull
    @Override
    public ReviewViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_reviews_item,viewGroup,false);
        ReviewViewHolderClass viewHolder = new ReviewViewHolderClass(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolderClass holder, int i) {
        holder.tvUsername.setText(reviewList.get(i).getUserName());

//---converting date and time format from "2019-05-20 13:36:34"  to "20/05/2019 01:36:34"

        String orignalDate= reviewList.get(i).getReviewDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = null;
        String output = null;
        try{
            date= df.parse(orignalDate);
            output = outputformat.format(date);
            System.out.println(output);
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        String[] dateTime= output.split("\\s+");
        String strDate= dateTime[0];
        holder.tvDate.setText(strDate);
        holder.tvReview.setText(reviewList.get(i).getReviewContent());
        int rating= Integer.parseInt(reviewList.get(i).getRating());

//----------- ratings---------------------

        if(rating ==2){
            holder.imgStar2.setVisibility(View.VISIBLE);
        }else if(rating ==3){
            holder.imgStar2.setVisibility(View.VISIBLE);
            holder.imgStar3.setVisibility(View.VISIBLE);
        }else if(rating ==4){
            holder.imgStar2.setVisibility(View.VISIBLE);
            holder.imgStar3.setVisibility(View.VISIBLE);
            holder.imgStar4.setVisibility(View.VISIBLE);
        }else if(rating ==5){
            holder.imgStar2.setVisibility(View.VISIBLE);
            holder.imgStar3.setVisibility(View.VISIBLE);
            holder.imgStar4.setVisibility(View.VISIBLE);
            holder.imgStar5.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if(listCount==0) {
            return reviewList.size();
        }else{
            return listCount;
        }
    }

    public class ReviewViewHolderClass extends RecyclerView.ViewHolder {
        TextView tvUsername,tvDate, tvReview;
        ImageView imgStar1,imgStar2,imgStar3,imgStar4,imgStar5;
        public ReviewViewHolderClass(@NonNull View itemView) {
            super(itemView);
            tvUsername= itemView.findViewById(R.id.tv_username);
            tvDate= itemView.findViewById(R.id.tv_date);
            tvReview= itemView.findViewById(R.id.tv_review);
            imgStar1= itemView.findViewById(R.id.rev_star1);
            imgStar2= itemView.findViewById(R.id.rev_star2);
            imgStar3= itemView.findViewById(R.id.rev_star3);
            imgStar4= itemView.findViewById(R.id.rev_star4);
            imgStar5= itemView.findViewById(R.id.rev_star5);
        }
    }
}
