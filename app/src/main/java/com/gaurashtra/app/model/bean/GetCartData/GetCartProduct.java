package com.gaurashtra.app.model.bean.GetCartData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCartProduct {
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

        @SerializedName("cartSessionId")
        @Expose
        private String cartSessionId;
        @SerializedName("cartData")
        @Expose
        private List<CartDetails> cartData = null;
        @SerializedName("appliedCouponData")
        @Expose
        private AppliedCouponData appliedCouponData;
        @SerializedName("cartInfo")
        @Expose
        private CartInfo cartInfo;
        @SerializedName("couponData")
        @Expose
        private List<CouponDatum> couponData = null;

        public String getCartSessionId() {
            return cartSessionId;
        }

        public void setCartSessionId(String cartSessionId) {
            this.cartSessionId = cartSessionId;
        }

        public List<CartDetails> getCartData() {
            return cartData;
        }

        public void setCartData(List<CartDetails> cartData) {
            this.cartData = cartData;
        }

        public AppliedCouponData getAppliedCouponData() {
            return appliedCouponData;
        }

        public void setAppliedCouponData(AppliedCouponData appliedCouponData) {
            this.appliedCouponData = appliedCouponData;
        }

        public CartInfo getCartInfo() {
            return cartInfo;
        }

        public void setCartInfo(CartInfo cartInfo) {
            this.cartInfo = cartInfo;
        }

        public List<CouponDatum> getCouponData() {
            return couponData;
        }

        public void setCouponData(List<CouponDatum> couponData) {
            this.couponData = couponData;
        }



        public class CartDetails {
            @SerializedName("cart_id")
            @Expose
            private String cartId;
            @SerializedName("cart_product_id")
            @Expose
            private String cartProductId;
            @SerializedName("cart_quantity")
            @Expose
            private String cartQuantity;
            @SerializedName("cart_option")
            @Expose
            private String cartOption;
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
            @SerializedName("product_weight")
            @Expose
            private String productWeight;
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
            @SerializedName("option_type")
            @Expose
            private String optionType;
            @SerializedName("option_name_type")
            @Expose
            private String optionNameType;
            @SerializedName("option_id")
            @Expose
            private String optionId;
            @SerializedName("option_value_id")
            @Expose
            private String optionValueId;
            @SerializedName("option_name")
            @Expose
            private String optionName;
            @SerializedName("option_quantity")
            @Expose
            private String optionQuantity;
            @SerializedName("option_price")
            @Expose
            private String optionPrice;
            @SerializedName("option_price_prefix")
            @Expose
            private String optionPricePrefix;
            @SerializedName("option_points")
            @Expose
            private String optionPoints;
            @SerializedName("option_points_prefix")
            @Expose
            private String optionPointsPrefix;
            @SerializedName("option_weight")
            @Expose
            private String optionWeight;
            @SerializedName("option_weight_prefix")
            @Expose
            private String optionWeightPrefix;
            @SerializedName("cart_product_price")
            @Expose
            private Double cartProductPrice;
            @SerializedName("cart_product_price_second")
            @Expose
            private Double cartProductPriceSecond;
            @SerializedName("cart_product_price_text")
            @Expose
            private String cartProductPriceText;
            @SerializedName("cart_product_price_total")
            @Expose
            private Double cartProductPriceTotal;
            @SerializedName("cart_quantity_exceed")
            @Expose
            private String cartQuantityExceed;
            @SerializedName("cart_product_tax_total")
            @Expose
            private Double cartProductTaxTotal;

            public String getCartId() {
                return cartId;
            }

            public void setCartId(String cartId) {
                this.cartId = cartId;
            }

            public String getCartProductId() {
                return cartProductId;
            }

            public void setCartProductId(String cartProductId) {
                this.cartProductId = cartProductId;
            }

            public String getCartQuantity() {
                return cartQuantity;
            }

            public void setCartQuantity(String cartQuantity) {
                this.cartQuantity = cartQuantity;
            }

            public String getCartOption() {
                return cartOption;
            }

            public void setCartOption(String cartOption) {
                this.cartOption = cartOption;
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

            public String getProductWeight() {
                return productWeight;
            }

            public void setProductWeight(String productWeight) {
                this.productWeight = productWeight;
            }

            public String getProductName() {
                if(productName.contains("&amp;")){
                    productName=productName.replace("amp;","");
                }
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

            public String getOptionType() {
                return optionType;
            }

            public void setOptionType(String optionType) {
                this.optionType = optionType;
            }

            public String getOptionNameType() {
                return optionNameType;
            }

            public void setOptionNameType(String optionNameType) {
                this.optionNameType = optionNameType;
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

            public String getOptionName() {
                return optionName;
            }

            public void setOptionName(String optionName) {
                this.optionName = optionName;
            }

            public String getOptionQuantity() {
                return optionQuantity;
            }

            public void setOptionQuantity(String optionQuantity) {
                this.optionQuantity = optionQuantity;
            }

            public String getOptionPrice() {
                return optionPrice;
            }

            public void setOptionPrice(String optionPrice) {
                this.optionPrice = optionPrice;
            }

            public String getOptionPricePrefix() {
                return optionPricePrefix;
            }

            public void setOptionPricePrefix(String optionPricePrefix) {
                this.optionPricePrefix = optionPricePrefix;
            }

            public String getOptionPoints() {
                return optionPoints;
            }

            public void setOptionPoints(String optionPoints) {
                this.optionPoints = optionPoints;
            }

            public String getOptionPointsPrefix() {
                return optionPointsPrefix;
            }

            public void setOptionPointsPrefix(String optionPointsPrefix) {
                this.optionPointsPrefix = optionPointsPrefix;
            }

            public String getOptionWeight() {
                return optionWeight;
            }

            public void setOptionWeight(String optionWeight) {
                this.optionWeight = optionWeight;
            }

            public String getOptionWeightPrefix() {
                return optionWeightPrefix;
            }

            public void setOptionWeightPrefix(String optionWeightPrefix) {
                this.optionWeightPrefix = optionWeightPrefix;
            }

            public Double getCartProductPrice() {
                return cartProductPrice;
            }

            public void setCartProductPrice(Double cartProductPrice) {
                this.cartProductPrice = cartProductPrice;
            }

            public Double getCartProductPriceSecond() {
                return cartProductPriceSecond;
            }

            public void setCartProductPriceSecond(Double cartProductPriceSecond) {
                this.cartProductPriceSecond = cartProductPriceSecond;
            }

            public String getCartProductPriceText() {
                return cartProductPriceText;
            }

            public void setCartProductPriceText(String cartProductPriceText) {
                this.cartProductPriceText = cartProductPriceText;
            }

            public Double getCartProductPriceTotal() {
                return cartProductPriceTotal;
            }

            public void setCartProductPriceTotal(Double cartProductPriceTotal) {
                this.cartProductPriceTotal = cartProductPriceTotal;
            }

            public String getCartQuantityExceed() {
                return cartQuantityExceed;
            }

            public void setCartQuantityExceed(String cartQuantityExceed) {
                this.cartQuantityExceed = cartQuantityExceed;
            }

            public Double getCartProductTaxTotal() {
                return cartProductTaxTotal;
            }

            public void setCartProductTaxTotal(Double cartProductTaxTotal) {
                this.cartProductTaxTotal = cartProductTaxTotal;
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
            public Coupon coupon;
            @SerializedName("tax")
            @Expose
            private List<Tax> tax = null;
            @SerializedName("wallet")
            @Expose
            private Wallet wallet;
            @SerializedName("cartTotal")
            @Expose
            public CartTotal cartTotal;
            @SerializedName("other")
            @Expose
            private Other other;

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
        }



        public class CouponDatum {
            @SerializedName("coupon_code")
            @Expose
            private String couponCode;
            @SerializedName("coupon_content")
            @Expose
            private String couponContent;
            @SerializedName("coupon_type")
            @Expose
            private String couponType;

            public String getCouponCode() {
                return couponCode;
            }

            public void setCouponCode(String couponCode) {
                this.couponCode = couponCode;
            }

            public String getCouponContent() {
                return couponContent;
            }

            public void setCouponContent(String couponContent) {
                this.couponContent = couponContent;
            }

            public String getCouponType() {
                return couponType;
            }

            public void setCouponType(String couponType) {
                this.couponType = couponType;
            }
        }

        public class AppliedCouponData {
            @SerializedName("coupon_code")
            @Expose
            private String couponCode;
            @SerializedName("coupon_error")
            @Expose
            private String couponError;
            @SerializedName("coupon_message")
            @Expose
            private String couponMessage;

            public String getCouponCode() {
                return couponCode;
            }

            public void setCouponCode(String couponCode) {
                this.couponCode = couponCode;
            }

            public String getCouponError() {
                return couponError;
            }

            public void setCouponError(String couponError) {
                this.couponError = couponError;
            }

            public String getCouponMessage() {
                return couponMessage;
            }

            public void setCouponMessage(String couponMessage) {
                this.couponMessage = couponMessage;
            }

        }
    }
}
