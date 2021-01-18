package com.gaurashtra.app.model.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductBasicDetailsResult {

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

        @SerializedName("offerText")
        @Expose
        private OfferText offerText;
        @SerializedName("product")
        @Expose
        private Product product;
        @SerializedName("staticContentData")
        @Expose
        private ArrayList<StaticContentDatum> staticContentData = null;

        public OfferText getOfferText() {
            return offerText;
        }

        public void setOfferText(OfferText offerText) {
            this.offerText = offerText;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public ArrayList<StaticContentDatum> getStaticContentData() {
            return staticContentData;
        }

        public void setStaticContentData(ArrayList<StaticContentDatum> staticContentData) {
            this.staticContentData = staticContentData;
        }
        public class OfferText {

            @SerializedName("content")
            @Expose
            private String content;
            @SerializedName("linkType")
            @Expose
            private String linkType;
            @SerializedName("linkId")
            @Expose
            private String linkId;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLinkType() {
                return linkType;
            }

            public void setLinkType(String linkType) {
                this.linkType = linkType;
            }

            public String getLinkId() {
                return linkId;
            }

            public void setLinkId(String linkId) {
                this.linkId = linkId;
            }
        }
        public class Product {

            @SerializedName("data")
            @Expose
            private Data data;
            @SerializedName("image")
            @Expose
            private ArrayList<Image> image = null;
            @SerializedName("review")
            @Expose
            private Review review;

            public Data getData() {
                return data;
            }

            public void setData(Data data) {
                this.data = data;
            }

            public ArrayList<Image> getImage() {
                return image;
            }

            public void setImage(ArrayList<Image> image) {
                this.image = image;
            }

            public Review getReview() {
                return review;
            }

            public void setReview(Review review) {
                this.review = review;
            }

            public class Data {

                @SerializedName("product_id")
                @Expose
                private String productId;
                @SerializedName("category_id")
                @Expose
                private String categoryId;
                @SerializedName("product_code")
                @Expose
                private String productCode;
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
                @SerializedName("product_description")
                @Expose
                private String productDescription;
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
                @SerializedName("seo_url")
                @Expose
                private String seoUrl;
                @SerializedName("addedWishlist")
                @Expose
                private String addedWishlist;

                public String getProductId() {
                    return productId;
                }

                public void setProductId(String productId) {
                    this.productId = productId;
                }

                public String getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(String categoryId) {
                    this.categoryId = categoryId;
                }

                public String getProductCode() {
                    return productCode;
                }

                public void setProductCode(String productCode) {
                    this.productCode = productCode;
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

                public String getProductDescription() {
                    return productDescription;
                }

                public void setProductDescription(String productDescription) {
                    this.productDescription = productDescription;
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

                public String getAddedWishlist() {
                    return addedWishlist;
                }

                public void setAddedWishlist(String addedWishlist) {
                    this.addedWishlist = addedWishlist;
                }

                public String getSeoUrl() {
                    return seoUrl;
                }

                public void setSeoUrl(String seoUrl) {
                    this.seoUrl = seoUrl;
                }
            }
            public class Image {

                @SerializedName("image_id")
                @Expose
                private String imageId;
                @SerializedName("image_url")
                @Expose
                private String imageUrl;
                @SerializedName("image_order")
                @Expose
                private String imageOrder;

                public String getImageId() {
                    return imageId;
                }

                public void setImageId(String imageId) {
                    this.imageId = imageId;
                }

                public String getImageUrl() {
                    return imageUrl;
                }

                public void setImageUrl(String imageUrl) {
                    this.imageUrl = imageUrl;
                }

                public String getImageOrder() {
                    return imageOrder;
                }

                public void setImageOrder(String imageOrder) {
                    this.imageOrder = imageOrder;
                }
            }

            public class Review {

                @SerializedName("count")
                @Expose
                private String count;
                @SerializedName("average")
                @Expose
                private String average;

                public String getCount() {
                    return count;
                }

                public void setCount(String count) {
                    this.count = count;
                }

                public String getAverage() {
                    return average;
                }

                public void setAverage(String average) {
                    this.average = average;
                }

            }
        }
        public class StaticContentDatum {

            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("content")
            @Expose
            private String content;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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
