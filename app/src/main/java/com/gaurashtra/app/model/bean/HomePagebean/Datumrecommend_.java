package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datumrecommend_ {
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_model")
    @Expose
    private String productModel;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_quantity")
    @Expose
    private String productQuantity;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("special_price")
    @Expose
    private String specialPrice;
    @SerializedName("discount_quantity")
    @Expose
    private String discountQuantity;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("brand_id")
    @Expose
    private String brandId;
    @SerializedName("brand_name")
    @Expose
    private String brandName;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    public String getProductName() {
        if(productName.contains("&amp;")){
            productName.replace("amp;","");
        }
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(String specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getDiscountQuantity() {
        return discountQuantity;
    }

    public void setDiscountQuantity(String discountQuantity) {
        this.discountQuantity = discountQuantity;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
