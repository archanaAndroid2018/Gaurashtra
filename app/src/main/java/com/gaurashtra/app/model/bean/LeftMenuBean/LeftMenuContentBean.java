package com.gaurashtra.app.model.bean.LeftMenuBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeftMenuContentBean {
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

        @SerializedName("aboutUs")
        @Expose
        private AboutUs aboutUs;

        public AboutUs getAboutUs() {
            return aboutUs;
        }

        public void setAboutUs(AboutUs aboutUs) {
            this.aboutUs = aboutUs;
        }

        @SerializedName("termsConditions")
        @Expose
        public TermsConditions termsConditions;

        public TermsConditions getTermsConditions() {
            return termsConditions;
        }

        public void setTermsConditions(TermsConditions termsConditions) {
            this.termsConditions = termsConditions;
        }


        @SerializedName("privacyPolicy")
        @Expose
        public PrivacyPolicy privacyPolicy;

        public PrivacyPolicy getPrivacyPolicy() {
            return privacyPolicy;
        }

        public void setPrivacyPolicy(PrivacyPolicy privacyPolicy) {
            this.privacyPolicy = privacyPolicy;
        }


        @SerializedName("refundCancellationPolicy")
        @Expose
        private RefundCancellationPolicy refundCancellationPolicy;

        public RefundCancellationPolicy getRefundCancellationPolicy() {
            return refundCancellationPolicy;
        }

        public void setRefundCancellationPolicy(RefundCancellationPolicy refundCancellationPolicy) {
            this.refundCancellationPolicy = refundCancellationPolicy;
        }

        @SerializedName("offers")
        @Expose
        private Offers offers;

        public Offers getOffers() {
            return offers;
        }

        public void setOffers(Offers offers) {
            this.offers = offers;
        }


        public class AboutUs {

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("description")
            @Expose
            private String description;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

        }

        public class TermsConditions {

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("description")
            @Expose
            private String description;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

        }

        public class PrivacyPolicy {

            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("description")
            @Expose
            private String description;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public class RefundCancellationPolicy {
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("description")
            @Expose
            private String description;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public class Offers {
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("description")
            @Expose
            private String description;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
