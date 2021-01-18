package com.gaurashtra.app.model.bean.OrderBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsBean {
    @SerializedName("success")
    @Expose
    public Integer success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("result")
    @Expose
    public Result result;

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
        public PaymentInfo paymentInfo;
        @SerializedName("orderId")
        @Expose
        public Integer orderId;
        @SerializedName("cartInfo")
        @Expose
        public CartInfo cartInfo;

        public PaymentInfo getPaymentInfo() {
            return paymentInfo;
        }

        public void setPaymentInfo(PaymentInfo paymentInfo) {
            this.paymentInfo = paymentInfo;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public CartInfo getCartInfo() {
            return cartInfo;
        }

        public void setCartInfo(CartInfo cartInfo) {
            this.cartInfo = cartInfo;
        }

        public class PaymentInfo {
            @SerializedName("cash")
            @Expose
            public String cash;
            @SerializedName("Card")
            @Expose
            public String card;

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
            public SubTotal subTotal;
            @SerializedName("shipping")
            @Expose
            public Shipping shipping;
            @SerializedName("coupon")
            @Expose
            public Coupon coupon;
            @SerializedName("tax")
            @Expose
            public List<Tax> tax = null;
            @SerializedName("wallet")
            @Expose
            public Wallet wallet;
            @SerializedName("cartTotal")
            @Expose
            public CartTotal cartTotal;
            @SerializedName("other")
            @Expose
            public Other other;

            public SubTotal getSubTotal() {
                return subTotal;
            }

            public void setSubTotal(SubTotal subTotal) {
                this.subTotal = subTotal;
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

            public List<Tax> getTax() {
                return tax;
            }

            public void setTax(List<Tax> tax) {
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

            public class SubTotal {
                @SerializedName("code")
                @Expose
                public String code;
                @SerializedName("title")
                @Expose
                public String title;
                @SerializedName("value")
                @Expose
                public Double value;
                @SerializedName("order")
                @Expose
                public Integer order;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public Double getValue() {
                    return value;
                }

                public void setValue(Double value) {
                    this.value = value;
                }

                public Integer getOrder() {
                    return order;
                }

                public void setOrder(Integer order) {
                    this.order = order;
                }
            }

            public class Shipping {
                @SerializedName("code")
                @Expose
                public String code;
                @SerializedName("title")
                @Expose
                public String title;
                @SerializedName("value")
                @Expose
                public Integer value;
                @SerializedName("order")
                @Expose
                public Integer order;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public Integer getValue() {
                    return value;
                }

                public void setValue(Integer value) {
                    this.value = value;
                }

                public Integer getOrder() {
                    return order;
                }

                public void setOrder(Integer order) {
                    this.order = order;
                }
            }

            public class Coupon {
                @SerializedName("code")
                @Expose
                public String code;
                @SerializedName("title")
                @Expose
                public String title;
                @SerializedName("value")
                @Expose
                public Integer value;
                @SerializedName("order")
                @Expose
                public Integer order;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public Integer getValue() {
                    return value;
                }

                public void setValue(Integer value) {
                    this.value = value;
                }

                public Integer getOrder() {
                    return order;
                }

                public void setOrder(Integer order) {
                    this.order = order;
                }
            }
            public class Tax {

                @SerializedName("code")
                @Expose
                public String code;
                @SerializedName("title")
                @Expose
                public String title;
                @SerializedName("value")
                @Expose
                public Double value;
                @SerializedName("order")
                @Expose
                public Integer order;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public Double getValue() {
                    return value;
                }

                public void setValue(Double value) {
                    this.value = value;
                }

                public Integer getOrder() {
                    return order;
                }

                public void setOrder(Integer order) {
                    this.order = order;
                }
            }

            public class Wallet {
                @SerializedName("code")
                @Expose
                public String code;
                @SerializedName("title")
                @Expose
                public String title;
                @SerializedName("value")
                @Expose
                public Integer value;
                @SerializedName("order")
                @Expose
                public Integer order;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public Integer getValue() {
                    return value;
                }

                public void setValue(Integer value) {
                    this.value = value;
                }

                public Integer getOrder() {
                    return order;
                }

                public void setOrder(Integer order) {
                    this.order = order;
                }
            }

            public class CartTotal{
                @SerializedName("code")
                @Expose
                public String code;
                @SerializedName("title")
                @Expose
                public String title;
                @SerializedName("value")
                @Expose
                public Double value;
                @SerializedName("order")
                @Expose
                public Integer order;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public Double getValue() {
                    return value;
                }

                public void setValue(Double value) {
                    this.value = value;
                }

                public Integer getOrder() {
                    return order;
                }

                public void setOrder(Integer order) {
                    this.order = order;
                }

            }
            public class Other{
                @SerializedName("totalSaving")
                @Expose
                public Integer totalSaving;
                @SerializedName("totalWeight")
                @Expose
                public Integer totalWeight;
                @SerializedName("totalTax")
                @Expose
                public Double totalTax;
                @SerializedName("cartError")
                @Expose
                public String cartError;
                @SerializedName("cartErrorMessage")
                @Expose
                public String cartErrorMessage;

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
        }
    }
}
