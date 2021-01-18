package com.gaurashtra.app.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactUsResult {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private Boolean message;
    @SerializedName("result")
    @Expose
    private Result result;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    public class Result {

        @SerializedName("contactUsContent")
        @Expose
        private ContactUsContent contactUsContent;

        public ContactUsContent getContactUsContent() {
            return contactUsContent;
        }

        public void setContactUsContent(ContactUsContent contactUsContent) {
            this.contactUsContent = contactUsContent;
        }
        public class ContactUsContent {

            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("mobile")
            @Expose
            private String mobile;
            @SerializedName("content")
            @Expose
            private String content;

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

        }
    }
}
