package com.gaurashtra.app.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OptionListBean {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private Result result;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("productData")
        @Expose
        private ProductData productData;

        public ProductData getProductData() {
            return productData;
        }

        public void setProductData(ProductData productData) {
            this.productData = productData;
        }
        public class ProductData {

            @SerializedName("data")
            @Expose
            private Data data;
            @SerializedName("option")
            @Expose
            private ArrayList<Option> option = null;

            public Data getData() {
                return data;
            }

            public void setData(Data data) {
                this.data = data;
            }

            public ArrayList<Option> getOption() {
                return option;
            }

            public void setOption(ArrayList<Option> option) {
                this.option = option;
            }
            public class Data {

                @SerializedName("product_id")
                @Expose
                private String productId;
                @SerializedName("category_id")
                @Expose
                private String categoryId;
                @SerializedName("product_code")
                @Expose
                private String productCode;
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
                @SerializedName("product_description")
                @Expose
                private String productDescription;
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

                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

                public String getProductCode() {
                    return productCode;
                }

                public void setProductCode(String productCode) {
                    this.productCode = productCode;
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
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getProductDescription() {
                    return productDescription;
                }

                public void setProductDescription(String productDescription) {
                    this.productDescription = productDescription;
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
            public class Option {

                @SerializedName("option_id")
                @Expose
                private String optionId;
                @SerializedName("option_value_id")
                @Expose
                private String optionValueId;
                @SerializedName("option_name")
                @Expose
                private String optionName;
                @SerializedName("option_quantity")
                @Expose
                private String optionQuantity;
                @SerializedName("option_price")
                @Expose
                private String optionPrice;
                @SerializedName("option_price_prefix")
                @Expose
                private String optionPricePrefix;
                @SerializedName("option_points")
                @Expose
                private String optionPoints;
                @SerializedName("option_points_prefix")
                @Expose
                private String optionPointsPrefix;
                @SerializedName("option_weight")
                @Expose
                private String optionWeight;
                @SerializedName("option_weight_prefix")
                @Expose
                private String optionWeightPrefix;

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

                public String getOptionName() {
                    return optionName;
                }

                public void setOptionName(String optionName) {
                    this.optionName = optionName;
                }

                public String getOptionQuantity() {
                    return optionQuantity;
                }

                public void setOptionQuantity(String optionQuantity) {
                    this.optionQuantity = optionQuantity;
                }

                public String getOptionPrice() {
                    return optionPrice;
                }

                public void setOptionPrice(String optionPrice) {
                    this.optionPrice = optionPrice;
                }

                public String getOptionPricePrefix() {
                    return optionPricePrefix;
                }

                public void setOptionPricePrefix(String optionPricePrefix) {
                    this.optionPricePrefix = optionPricePrefix;
                }

                public String getOptionPoints() {
                    return optionPoints;
                }

                public void setOptionPoints(String optionPoints) {
                    this.optionPoints = optionPoints;
                }

                public String getOptionPointsPrefix() {
                    return optionPointsPrefix;
                }

                public void setOptionPointsPrefix(String optionPointsPrefix) {
                    this.optionPointsPrefix = optionPointsPrefix;
                }

                public String getOptionWeight() {
                    return optionWeight;
                }

                public void setOptionWeight(String optionWeight) {
                    this.optionWeight = optionWeight;
                }

                public String getOptionWeightPrefix() {
                    return optionWeightPrefix;
                }

                public void setOptionWeightPrefix(String optionWeightPrefix) {
                    this.optionWeightPrefix = optionWeightPrefix;
                }

            }
        }
    }
}
