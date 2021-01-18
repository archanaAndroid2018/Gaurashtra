package com.gaurashtra.app.model.bean.WishListBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WishlistDatum {
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
    @SerializedName("tax_rate")
    @Expose
    private String taxRate;
    @SerializedName("total_count")
    @Expose
    private String totalCount;
    @SerializedName("total_rating")
    @Expose
    private Object totalRating;
    @SerializedName("option_id")
    @Expose
    private String optionId;
    @SerializedName("option_value_id")
    @Expose
    private String optionValueId;
    @SerializedName("option_type")
    @Expose
    private String optionType;

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

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public Object getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Object totalRating) {
        this.totalRating = totalRating;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionValueId() {
        return optionValueId;
    }

    public void setOptionValueId(String optionValueId) {
        this.optionValueId = optionValueId;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }
}
