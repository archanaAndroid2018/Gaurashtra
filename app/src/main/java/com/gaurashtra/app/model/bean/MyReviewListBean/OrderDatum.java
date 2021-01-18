package com.gaurashtra.app.model.bean.MyReviewListBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderDatum {

    @SerializedName("myReview")
    @Expose
    private ArrayList<ReviewData> myReview = null;
    @SerializedName("toBeReviewed")
    @Expose
    private ArrayList<ReviewData> toBeReviewed = null;

    public ArrayList<ReviewData> getMyReview() {
        return myReview;
    }

    public void setMyReview(ArrayList<ReviewData> myReview) {
        this.myReview = myReview;
    }

    public ArrayList<ReviewData> getToBeReviewed() {
        return toBeReviewed;
    }

    public void setToBeReviewed(ArrayList<ReviewData> toBeReviewed) {
        this.toBeReviewed = toBeReviewed;
    }
}
