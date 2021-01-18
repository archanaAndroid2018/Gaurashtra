package com.gaurashtra.app.model.bean.MyReviewListBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewData {
    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("invoicePrefix")
    @Expose
    private String invoicePrefix;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("paymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("delivered")
    @Expose
    private String delivered;
    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productImage")
    @Expose
    private String productImage;
    @SerializedName("productModel")
    @Expose
    private String productModel;
    @SerializedName("productQuantity")
    @Expose
    private String productQuantity;
    @SerializedName("productPrice")
    @Expose
    private String productPrice;
    @SerializedName("productTotal")
    @Expose
    private String productTotal;
    @SerializedName("productTax")
    @Expose
    private String productTax;
    @SerializedName("productOptionId")
    @Expose
    private String productOptionId;
    @SerializedName("productOptionValueId")
    @Expose
    private String productOptionValueId;
    @SerializedName("productOptionName")
    @Expose
    private String productOptionName;
    @SerializedName("productOptionValueName")
    @Expose
    private String productOptionValueName;
    @SerializedName("productOptionType")
    @Expose
    private String productOptionType;
    @SerializedName("reviewId")
    @Expose
    private String reviewId;
    @SerializedName("reviewText")
    @Expose
    private String reviewText;
    @SerializedName("reviewRating")
    @Expose
    private String reviewRating;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        if(productName.contains("&amp;")){
            productName.replace("amp;","");
        }
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(String productTotal) {
        this.productTotal = productTotal;
    }

    public String getProductTax() {
        return productTax;
    }

    public void setProductTax(String productTax) {
        this.productTax = productTax;
    }

    public String getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(String productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductOptionValueId() {
        return productOptionValueId;
    }

    public void setProductOptionValueId(String productOptionValueId) {
        this.productOptionValueId = productOptionValueId;
    }

    public String getProductOptionName() {
        return productOptionName;
    }

    public void setProductOptionName(String productOptionName) {
        this.productOptionName = productOptionName;
    }

    public String getProductOptionValueName() {
        return productOptionValueName;
    }

    public void setProductOptionValueName(String productOptionValueName) {
        this.productOptionValueName = productOptionValueName;
    }

    public String getProductOptionType() {
        return productOptionType;
    }

    public void setProductOptionType(String productOptionType) {
        this.productOptionType = productOptionType;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }
}
