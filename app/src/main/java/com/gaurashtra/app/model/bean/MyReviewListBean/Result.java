package com.gaurashtra.app.model.bean.MyReviewListBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
@SerializedName("orderData")
@Expose
private OrderDatum orderData;

    public OrderDatum getOrderData() {
        return orderData;
    }

    public void setOrderData(OrderDatum orderData) {
        this.orderData = orderData;
    }
}
