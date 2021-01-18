package com.gaurashtra.app.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductRemainingResult {
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

        @SerializedName("product")
        @Expose
        private Product product;
        @SerializedName("relatedProductData")
        @Expose
        private ArrayList<RelatedProductDatum> relatedProductData = null;
        @SerializedName("recentlyPurchasedData")
        @Expose
        private ArrayList<RecentlyPurchasedDatum> recentlyPurchasedData = null;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public ArrayList<RelatedProductDatum> getRelatedProductData() {
            return relatedProductData;
        }

        public void setRelatedProductData(ArrayList<RelatedProductDatum> relatedProductData) {
            this.relatedProductData = relatedProductData;
        }

        public ArrayList<RecentlyPurchasedDatum> getRecentlyPurchasedData() {
            return recentlyPurchasedData;
        }

        public void setRecentlyPurchasedData(ArrayList<RecentlyPurchasedDatum> recentlyPurchasedData) {
            this.recentlyPurchasedData = recentlyPurchasedData;
        }

        public class Product {

            @SerializedName("review")
            @Expose
            private Review review;
            @SerializedName("askQuestion")
            @Expose
            private ArrayList<AskQuestion> askQuestion = null;

            public Review getReview() {
                return review;
            }

            public void setReview(Review review) {
                this.review = review;
            }

            public ArrayList<AskQuestion> getAskQuestion() {
                return askQuestion;
            }

            public void setAskQuestion(ArrayList<AskQuestion> askQuestion) {
                this.askQuestion = askQuestion;
            }
            public class Review {

                @SerializedName("data")
                @Expose
                private ArrayList<Datum> data = null;

                public ArrayList<Datum> getData() {
                    return data;
                }

                public void setData(ArrayList<Datum> data) {
                    this.data = data;
                }

                public class Datum {

                    @SerializedName("review_id")
                    @Expose
                    private String reviewId;
                    @SerializedName("user_id")
                    @Expose
                    private String userId;
                    @SerializedName("user_name")
                    @Expose
                    private String userName;
                    @SerializedName("review_content")
                    @Expose
                    private String reviewContent;
                    @SerializedName("rating")
                    @Expose
                    private String rating;
                    @SerializedName("review_date")
                    @Expose
                    private String reviewDate;

                    public String getReviewId() {
                        return reviewId;
                    }

                    public void setReviewId(String reviewId) {
                        this.reviewId = reviewId;
                    }

                    public String getUserId() {
                        return userId;
                    }

                    public void setUserId(String userId) {
                        this.userId = userId;
                    }

                    public String getUserName() {
                        return userName;
                    }

                    public void setUserName(String userName) {
                        this.userName = userName;
                    }

                    public String getReviewContent() {
                        return reviewContent;
                    }

                    public void setReviewContent(String reviewContent) {
                        this.reviewContent = reviewContent;
                    }

                    public String getRating() {
                        return rating;
                    }

                    public void setRating(String rating) {
                        this.rating = rating;
                    }

                    public String getReviewDate() {
                        return reviewDate;
                    }

                    public void setReviewDate(String reviewDate) {
                        this.reviewDate = reviewDate;
                    }

                }
            }
            public class AskQuestion {

                @SerializedName("questioner_name")
                @Expose
                private String questionerName;
                @SerializedName("questioner_content")
                @Expose
                private String questionerContent;
                @SerializedName("questioner_date")
                @Expose
                private String questionerDate;
                @SerializedName("replayer_name")
                @Expose
                private String replayerName;
                @SerializedName("replayer_content")
                @Expose
                private String replayerContent;
                @SerializedName("replayer_date")
                @Expose
                private String replayerDate;

                public String getQuestionerName() {
                    return questionerName;
                }

                public void setQuestionerName(String questionerName) {
                    this.questionerName = questionerName;
                }

                public String getQuestionerContent() {
                    return questionerContent;
                }

                public void setQuestionerContent(String questionerContent) {
                    this.questionerContent = questionerContent;
                }

                public String getQuestionerDate() {
                    return questionerDate;
                }

                public void setQuestionerDate(String questionerDate) {
                    this.questionerDate = questionerDate;
                }

                public String getReplayerName() {
                    return replayerName;
                }

                public void setReplayerName(String replayerName) {
                    this.replayerName = replayerName;
                }

                public String getReplayerContent() {
                    return replayerContent;
                }

                public void setReplayerContent(String replayerContent) {
                    this.replayerContent = replayerContent;
                }

                public String getReplayerDate() {
                    return replayerDate;
                }

                public void setReplayerDate(String replayerDate) {
                    this.replayerDate = replayerDate;
                }
            }
        }

        public class RecentlyPurchasedDatum {

            @SerializedName("product_id")
            @Expose
            private String productId;
            @SerializedName("product_model")
            @Expose
            private String productModel;
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

            public String getProductModel() {
                return productModel;
            }

            public void setProductModel(String productModel) {
                this.productModel = productModel;
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

        public class RelatedProductDatum {

            @SerializedName("product_id")
            @Expose
            private String productId;
            @SerializedName("product_model")
            @Expose
            private String productModel;
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

            public String getProductModel() {
                return productModel;
            }

            public void setProductModel(String productModel) {
                this.productModel = productModel;
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
    }
}
