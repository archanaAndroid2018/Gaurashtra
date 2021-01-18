package com.gaurashtra.app.model.bean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCurrencyList {

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

        @SerializedName("currencyData")
        @Expose
        private List<CurrencyData> currencyDataList;


        public List<CurrencyData> getCurrencyDataList() {
            return currencyDataList;
        }

        public void setCurrencyDataList(List<CurrencyData> currencyDataList) {
            this.currencyDataList = currencyDataList;
        }
        public class CurrencyData{

            @SerializedName("title")
            @Expose
            private String title;

            @SerializedName("code")
            @Expose
            private String code;

            @SerializedName("value")
            @Expose
            private String value;

            @SerializedName("symbol")
            @Expose
            private String symbol;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }
        }
    }

}
