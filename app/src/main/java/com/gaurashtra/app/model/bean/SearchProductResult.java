package com.gaurashtra.app.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchProductResult {
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
        private List<ProductDatum> productData = null;

        public List<ProductDatum> getProductData() {
            return productData;
        }

        public void setProductData(List<ProductDatum> productData) {
            this.productData = productData;
        }

        public class ProductDatum {

            @SerializedName("product_id")
            @Expose
            private String productId;
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

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
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
                    productName.replace("&amp;","");
                }
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

        }
    }
}
