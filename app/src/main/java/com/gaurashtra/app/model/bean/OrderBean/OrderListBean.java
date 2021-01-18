package com.gaurashtra.app.model.bean.OrderBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderListBean {
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
        @SerializedName("orderData")
        @Expose
        private List<OrderDatum> orderData = null;

        public List<OrderDatum> getOrderData() {
            return orderData;
        }

        public void setOrderData(List<OrderDatum> orderData) {
            this.orderData = orderData;
        }

        public class OrderDatum {
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
            @SerializedName("productImage")
            @Expose
            private String productImage;
            @SerializedName("orderStatus")
            @Expose
            private String orderStatus;
            @SerializedName("delivered")
            @Expose
            private String delivered;

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

            public String getProductImage() {
                return productImage;
            }

            public void setProductImage(String productImage) {
                this.productImage = productImage;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public String getDelivered() {
                return delivered;
            }

            public void setDelivered(String delivered) {
                this.delivered = delivered;
            }
        }
    }
}
