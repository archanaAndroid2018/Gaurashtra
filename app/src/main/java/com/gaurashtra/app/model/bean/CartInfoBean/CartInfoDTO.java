package com.gaurashtra.app.model.bean.CartInfoBean;

import com.gaurashtra.app.model.bean.GetCartData.CartTotal;
import com.gaurashtra.app.model.bean.GetCartData.Coupon;
import com.gaurashtra.app.model.bean.GetCartData.Other;
import com.gaurashtra.app.model.bean.GetCartData.Shipping;
import com.gaurashtra.app.model.bean.GetCartData.SubTotal;
import com.gaurashtra.app.model.bean.GetCartData.Tax;
import com.gaurashtra.app.model.bean.GetCartData.Wallet;
import com.gaurashtra.app.model.bean.OrderBean.OrderDetailsBean;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CartInfoDTO {
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
        @SerializedName("paymentInfo")
        @Expose
        private PaymentInfo paymentInfo;
        @SerializedName("paymentGetway")
        @Expose
        private ArrayList<PaymentGateway> paymentGetway = null;
        @SerializedName("cartInfo")
        @Expose
        private CartInfo cartInfo;

        public PaymentInfo getPaymentInfo() {
            return paymentInfo;
        }

        public void setPaymentInfo(PaymentInfo paymentInfo) {
            this.paymentInfo = paymentInfo;
        }

        public ArrayList<PaymentGateway> getPaymentGetway() {
            return paymentGetway;
        }

        public void setPaymentGetway(ArrayList<PaymentGateway> paymentGetway) {
            this.paymentGetway = paymentGetway;
        }

        public CartInfo getCartInfo() {
            return cartInfo;
        }

        public void setCartInfo(CartInfo cartInfo) {
            this.cartInfo = cartInfo;
        }

        public class PaymentGateway {
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("amount")
            @Expose
            private String amount;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public PaymentGateway(String id, String name, String title, String image) {
                this.id = id;
                this.name = name;
                this.title = title;
                this.image = image;
            }
        }

        public class PaymentInfo {

                @SerializedName("cash")
                @Expose
                private String cash;
                @SerializedName("Card")
                @Expose
                private String card;

                public String getCash() {
                    return cash;
                }

                public void setCash(String cash) {
                    this.cash = cash;
                }

                public String getCard() {
                    return card;
                }

                public void setCard(String card) {
                    this.card = card;
                }
        }

        public class CartInfo {
            @SerializedName("subTotal")
            @Expose
            private SubTotal subTotal;
            @SerializedName("shipping")
            @Expose
            private Shipping shipping;
            @SerializedName("coupon")
            @Expose
            private Coupon coupon;
            @SerializedName("tax")
            @Expose
            private ArrayList<Tax> tax = null;
            @SerializedName("storeCredit")
            @Expose
            private Wallet storeCredit;

            @SerializedName("wallet")
            @Expose
            private Wallet wallet;
            @SerializedName("cartTotal")
            @Expose
            private CartTotal cartTotal;
            @SerializedName("other")
            @Expose
            private Other other;

            public SubTotal getSubTotal() {
                return subTotal;
            }

            public void setSubTotal(SubTotal subTotal) {
                this.subTotal = subTotal;
            }

            public Wallet getStoreCredit() {
                return storeCredit;
            }

            public void setStoreCredit(Wallet storeCredit) {
                this.storeCredit = storeCredit;
            }

            public Shipping getShipping() {
                return shipping;
            }

            public void setShipping(Shipping shipping) {
                this.shipping = shipping;
            }

            public Coupon getCoupon() {
                return coupon;
            }

            public void setCoupon(Coupon coupon) {
                this.coupon = coupon;
            }

            public ArrayList<Tax> getTax() {
                return tax;
            }

            public void setTax(ArrayList<Tax> tax) {
                this.tax = tax;
            }

            public Wallet getWallet() {
                return wallet;
            }

            public void setWallet(Wallet wallet) {
                this.wallet = wallet;
            }

            public CartTotal getCartTotal() {
                return cartTotal;
            }

            public void setCartTotal(CartTotal cartTotal) {
                this.cartTotal = cartTotal;
            }

            public Other getOther() {
                return other;
            }

            public void setOther(Other other) {
                this.other = other;
            }

        }
    }

}
