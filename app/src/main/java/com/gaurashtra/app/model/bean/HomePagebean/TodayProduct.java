package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodayProduct {
    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("product_model")
    @Expose
    private String product_model;
    @SerializedName("product_image")
    @Expose
    private String product_image;
    @SerializedName("product_quantity")
    @Expose
    private String product_quantity;
    @SerializedName("product_price")
    @Expose
    private String product_price;
    @SerializedName("product_name")
    @Expose
    private String product_name;
    @SerializedName("special_price")
    @Expose
    private String special_price;
    @SerializedName("discount_price")
    @Expose
    private String discount_price;
    @SerializedName("brand_id")
    @Expose
    private String brand_id;
    @SerializedName("brand_name")
    @Expose
    private String brand_name;
    @SerializedName("tax_rate")
    @Expose
    private String tax_rate;
    @SerializedName("option_id")
    @Expose
    private String option_id;
    @SerializedName("option_value_id")
    @Expose
    private String option_value_id;
    @SerializedName("option_type")
    @Expose
    private String option_type;

    @SerializedName("product_in_stock")
    @Expose
    private String productInStock;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSpecial_price() {
        return special_price;
    }

    public void setSpecial_price(String special_price) {
        this.special_price = special_price;
    }

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(String tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getOption_value_id() {
        return option_value_id;
    }

    public void setOption_value_id(String option_value_id) {
        this.option_value_id = option_value_id;
    }

    public String getOption_type() {
        return option_type;
    }

    public void setOption_type(String option_type) {
        this.option_type = option_type;
    }

    public String getProductInStock() {
        return productInStock;
    }

    public void setProductInStock(String productInStock) {
        this.productInStock = productInStock;
    }
}
