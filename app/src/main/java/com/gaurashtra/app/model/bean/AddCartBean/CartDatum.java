package com.gaurashtra.app.model.bean.AddCartBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartDatum {
    @SerializedName("cart_id")
    @Expose
    private String cartId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("quantity")
    @Expose
    private String quantity;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
