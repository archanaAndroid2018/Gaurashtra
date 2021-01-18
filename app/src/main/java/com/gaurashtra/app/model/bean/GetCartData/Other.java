package com.gaurashtra.app.model.bean.GetCartData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Other {
    @SerializedName("totalSaving")
    @Expose
    private Integer totalSaving;
    @SerializedName("totalWeight")
    @Expose
    private Integer totalWeight;
    @SerializedName("totalTax")
    @Expose
    private Double totalTax;
    @SerializedName("cartError")
    @Expose
    private String cartError;
    @SerializedName("cartErrorMessage")
    @Expose
    private String cartErrorMessage;

    public Integer getTotalSaving() {
        return totalSaving;
    }

    public void setTotalSaving(Integer totalSaving) {
        this.totalSaving = totalSaving;
    }

    public Integer getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Integer totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public String getCartError() {
        return cartError;
    }

    public void setCartError(String cartError) {
        this.cartError = cartError;
    }

    public String getCartErrorMessage() {
        return cartErrorMessage;
    }

    public void setCartErrorMessage(String cartErrorMessage) {
        this.cartErrorMessage = cartErrorMessage;
    }
}
