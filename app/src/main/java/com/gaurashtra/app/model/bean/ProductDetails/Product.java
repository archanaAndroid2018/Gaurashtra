package com.gaurashtra.app.model.bean.ProductDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("image")
    @Expose
    private List<Image> image = null;
    @SerializedName("review")
    @Expose
    private Review review;
    @SerializedName("askQuestion")
    @Expose
    private List<AskQuestion> askQuestion = null;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public List<AskQuestion> getAskQuestion() {
        return askQuestion;
    }

    public void setAskQuestion(List<AskQuestion> askQuestion) {
        this.askQuestion = askQuestion;
    }
}
