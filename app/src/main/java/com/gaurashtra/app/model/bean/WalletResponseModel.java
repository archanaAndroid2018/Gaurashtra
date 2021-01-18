package com.gaurashtra.app.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WalletResponseModel {
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

        @SerializedName("walletData")
        @Expose
        private ArrayList<WalletDatum> walletData = null;

        public ArrayList<WalletDatum> getWalletData() {
            return walletData;
        }

        public void setWalletData(ArrayList<WalletDatum> walletData) {
            this.walletData = walletData;
        }

        public class WalletDatum {

            @SerializedName("order_id")
            @Expose
            private String orderId;
            @SerializedName("description")
            @Expose
            private String description;
            @SerializedName("amount")
            @Expose
            private String amount;
            @SerializedName("date_added")
            @Expose
            private String dateAdded;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
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

            public String getDateAdded() {
                return dateAdded;
            }

            public void setDateAdded(String dateAdded) {
                this.dateAdded = dateAdded;
            }

        }
    }
}
