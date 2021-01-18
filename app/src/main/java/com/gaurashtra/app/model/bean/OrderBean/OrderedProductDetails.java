package com.gaurashtra.app.model.bean.OrderBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderedProductDetails {
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
        private OrderData orderData;

        public OrderData getOrderData() {
            return orderData;
        }

        public void setOrderData(OrderData orderData) {
            this.orderData = orderData;
        }

        public class OrderData {

            @SerializedName("general")
            @Expose
            private General general;
            @SerializedName("product")
            @Expose
            private List<Product> product = null;
            @SerializedName("info")
            @Expose
            private List<Info> info = null;

            public General getGeneral() {
                return general;
            }

            public void setGeneral(General general) {
                this.general = general;
            }

            public List<Product> getProduct() {
                return product;
            }

            public void setProduct(List<Product> product) {
                this.product = product;
            }

            public List<Info> getInfo() {
                return info;
            }

            public void setInfo(List<Info> info) {
                this.info = info;
            }

            public class General {
                @SerializedName("orderId")
                @Expose
                private String orderId;
                @SerializedName("invoicePrefix")
                @Expose
                private String invoicePrefix;
                @SerializedName("customerId")
                @Expose
                private String customerId;
                @SerializedName("customerName")
                @Expose
                private String customerName;
                @SerializedName("customerEmail")
                @Expose
                private String customerEmail;
                @SerializedName("customerPhone")
                @Expose
                private String customerPhone;
                @SerializedName("paymentName")
                @Expose
                private String paymentName;
                @SerializedName("paymentCompany")
                @Expose
                private String paymentCompany;
                @SerializedName("paymentAddress1")
                @Expose
                private String paymentAddress1;
                @SerializedName("paymentAddress2")
                @Expose
                private String paymentAddress2;
                @SerializedName("paymentCity")
                @Expose
                private String paymentCity;
                @SerializedName("paymentPostcode")
                @Expose
                private String paymentPostcode;
                @SerializedName("paymentCountry")
                @Expose
                private String paymentCountry;
                @SerializedName("paymentZone")
                @Expose
                private String paymentZone;
                @SerializedName("shippingName")
                @Expose
                private String shippingName;
                @SerializedName("shippingCompany")
                @Expose
                private String shippingCompany;
                @SerializedName("shippingAddress1")
                @Expose
                private String shippingAddress1;
                @SerializedName("shippingAddress2")
                @Expose
                private String shippingAddress2;
                @SerializedName("shippingCity")
                @Expose
                private String shippingCity;
                @SerializedName("shippingPostcode")
                @Expose
                private String shippingPostcode;
                @SerializedName("shippingCountry")
                @Expose
                private String shippingCountry;
                @SerializedName("shippingZone")
                @Expose
                private String shippingZone;
                @SerializedName("shippingPhone")
                @Expose
                private String shippingPhone;
                @SerializedName("shippingMethod")
                @Expose
                private String shippingMethod;
                @SerializedName("shippingCode")
                @Expose
                private String shippingCode;
                @SerializedName("comments")
                @Expose
                private String comments;
                @SerializedName("amount")
                @Expose
                private String amount;
                @SerializedName("orderDate")
                @Expose
                private String orderDate;
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
                @SerializedName("tracking_url")
                @Expose
                private String trackingUrl;

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

                public String getCustomerId() {
                    return customerId;
                }

                public void setCustomerId(String customerId) {
                    this.customerId = customerId;
                }

                public String getCustomerName() {
                    return customerName;
                }

                public void setCustomerName(String customerName) {
                    this.customerName = customerName;
                }

                public String getCustomerEmail() {
                    return customerEmail;
                }

                public void setCustomerEmail(String customerEmail) {
                    this.customerEmail = customerEmail;
                }

                public String getCustomerPhone() {
                    return customerPhone;
                }

                public void setCustomerPhone(String customerPhone) {
                    this.customerPhone = customerPhone;
                }

                public String getPaymentName() {
                    return paymentName;
                }

                public void setPaymentName(String paymentName) {
                    this.paymentName = paymentName;
                }

                public String getPaymentCompany() {
                    return paymentCompany;
                }

                public void setPaymentCompany(String paymentCompany) {
                    this.paymentCompany = paymentCompany;
                }

                public String getPaymentAddress1() {
                    return paymentAddress1;
                }

                public void setPaymentAddress1(String paymentAddress1) {
                    this.paymentAddress1 = paymentAddress1;
                }

                public String getPaymentAddress2() {
                    return paymentAddress2;
                }

                public void setPaymentAddress2(String paymentAddress2) {
                    this.paymentAddress2 = paymentAddress2;
                }

                public String getPaymentCity() {
                    return paymentCity;
                }

                public void setPaymentCity(String paymentCity) {
                    this.paymentCity = paymentCity;
                }

                public String getPaymentPostcode() {
                    return paymentPostcode;
                }

                public void setPaymentPostcode(String paymentPostcode) {
                    this.paymentPostcode = paymentPostcode;
                }

                public String getPaymentCountry() {
                    return paymentCountry;
                }

                public void setPaymentCountry(String paymentCountry) {
                    this.paymentCountry = paymentCountry;
                }

                public String getPaymentZone() {
                    return paymentZone;
                }

                public void setPaymentZone(String paymentZone) {
                    this.paymentZone = paymentZone;
                }

                public String getShippingName() {
                    return shippingName;
                }

                public void setShippingName(String shippingName) {
                    this.shippingName = shippingName;
                }

                public String getShippingCompany() {
                    return shippingCompany;
                }

                public void setShippingCompany(String shippingCompany) {
                    this.shippingCompany = shippingCompany;
                }

                public String getShippingAddress1() {
                    return shippingAddress1;
                }

                public void setShippingAddress1(String shippingAddress1) {
                    this.shippingAddress1 = shippingAddress1;
                }

                public String getShippingAddress2() {
                    return shippingAddress2;
                }

                public void setShippingAddress2(String shippingAddress2) {
                    this.shippingAddress2 = shippingAddress2;
                }

                public String getShippingCity() {
                    return shippingCity;
                }

                public void setShippingCity(String shippingCity) {
                    this.shippingCity = shippingCity;
                }

                public String getShippingPostcode() {
                    return shippingPostcode;
                }

                public void setShippingPostcode(String shippingPostcode) {
                    this.shippingPostcode = shippingPostcode;
                }

                public String getShippingCountry() {
                    return shippingCountry;
                }

                public void setShippingCountry(String shippingCountry) {
                    this.shippingCountry = shippingCountry;
                }

                public String getShippingZone() {
                    return shippingZone;
                }

                public void setShippingZone(String shippingZone) {
                    this.shippingZone = shippingZone;
                }

                public String getShippingMethod() {
                    return shippingMethod;
                }

                public void setShippingMethod(String shippingMethod) {
                    this.shippingMethod = shippingMethod;
                }

                public String getShippingCode() {
                    return shippingCode;
                }

                public void setShippingCode(String shippingCode) {
                    this.shippingCode = shippingCode;
                }

                public String getShippingPhone() {
                    return shippingPhone;
                }

                public void setShippingPhone(String shippingPhone) {
                    this.shippingPhone = shippingPhone;
                }

                public String getComments() {
                    return comments;
                }

                public void setComments(String comments) {
                    this.comments = comments;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getOrderDate() {
                    return orderDate;
                }

                public void setOrderDate(String orderDate) {
                    this.orderDate = orderDate;
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

                public String getTrackingUrl() {
                    return trackingUrl;
                }

                public void setTrackingUrl(String trackingUrl) {
                    this.trackingUrl = trackingUrl;
                }
            }

            public class Info {

                @SerializedName("code")
                @Expose
                private String code;
                @SerializedName("title")
                @Expose
                private String title;
                @SerializedName("value")
                @Expose
                private String value;
                @SerializedName("sort_order")
                @Expose
                private String sortOrder;

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

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getSortOrder() {
                    return sortOrder;
                }

                public void setSortOrder(String sortOrder) {
                    this.sortOrder = sortOrder;
                }

            }

            public class Product {

                @SerializedName("productId")
                @Expose
                private String productId;
                @SerializedName("productName")
                @Expose
                private String productName;
                @SerializedName("productImage")
                @Expose
                private String productImage;
                @SerializedName("productModel")
                @Expose
                private String productModel;
                @SerializedName("productQuantity")
                @Expose
                private String productQuantity;
                @SerializedName("productPrice")
                @Expose
                private String productPrice;
                @SerializedName("productTotal")
                @Expose
                private String productTotal;
                @SerializedName("productTax")
                @Expose
                private String productTax;
                @SerializedName("productOptionId")
                @Expose
                private String productOptionId;
                @SerializedName("productOptionValueId")
                @Expose
                private String productOptionValueId;
                @SerializedName("productOptionName")
                @Expose
                private String productOptionName;
                @SerializedName("productOptionValueName")
                @Expose
                private String productOptionValueName;
                @SerializedName("productOptionType")
                @Expose
                private String productOptionType;

                public String getProductId() {
                    return productId;
                }

                public void setProductId(String productId) {
                    this.productId = productId;
                }

                public String getProductName() {
                    if(productName.contains("&amp;")){
                        productName = productName.replace("&amp;","");
                    }
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getProductImage() {
                    return productImage;
                }

                public void setProductImage(String productImage) {
                    this.productImage = productImage;
                }

                public String getProductModel() {
                    return productModel;
                }

                public void setProductModel(String productModel) {
                    this.productModel = productModel;
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

                public String getProductTotal() {
                    return productTotal;
                }

                public void setProductTotal(String productTotal) {
                    this.productTotal = productTotal;
                }

                public String getProductTax() {
                    return productTax;
                }

                public void setProductTax(String productTax) {
                    this.productTax = productTax;
                }

                public String getProductOptionId() {
                    return productOptionId;
                }

                public void setProductOptionId(String productOptionId) {
                    this.productOptionId = productOptionId;
                }

                public String getProductOptionValueId() {
                    return productOptionValueId;
                }

                public void setProductOptionValueId(String productOptionValueId) {
                    this.productOptionValueId = productOptionValueId;
                }

                public String getProductOptionName() {
                    return productOptionName;
                }

                public void setProductOptionName(String productOptionName) {
                    this.productOptionName = productOptionName;
                }

                public String getProductOptionValueName() {
                    return productOptionValueName;
                }

                public void setProductOptionValueName(String productOptionValueName) {
                    this.productOptionValueName = productOptionValueName;
                }

                public String getProductOptionType() {
                    return productOptionType;
                }

                public void setProductOptionType(String productOptionType) {
                    this.productOptionType = productOptionType;
                }

            }
        }
    }
}
