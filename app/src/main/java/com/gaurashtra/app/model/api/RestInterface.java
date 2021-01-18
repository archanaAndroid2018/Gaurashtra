package com.gaurashtra.app.model.api;

import java.util.Map;

import com.gaurashtra.app.model.bean.ContactUsResponse;
import com.gaurashtra.app.model.bean.ContactUsResult;
import com.gaurashtra.app.model.bean.ProductDetails.ProductdetailsDTO;
import com.gaurashtra.app.model.bean.RazorpayOrderResponse;
import com.gaurashtra.app.model.bean.UserUpdateModel;
import com.gaurashtra.app.model.bean.AddAddressBean.AddAddressResponseDTO;
import com.gaurashtra.app.model.bean.AddCartBean.AddCartResponseDTO;
import com.gaurashtra.app.model.bean.BestOfBean.BestOfDTO;
import com.gaurashtra.app.model.bean.BrandListBean.BrandListDTO;
import com.gaurashtra.app.model.bean.CartInfoBean.CartInfoDTO;
import com.gaurashtra.app.model.bean.GetCartData.GetCartProduct;
import com.gaurashtra.app.model.bean.GetCurrencyDetail;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.HomePagebean.BrandProductData;
import com.gaurashtra.app.model.bean.HomePagebean.HomepageDTO;
import com.gaurashtra.app.model.bean.LeftMenuBean.LeftMenuContentBean;
import com.gaurashtra.app.model.bean.MyReviewListBean.ReviewResponceDTO;
import com.gaurashtra.app.model.bean.NotificationBean.NotificationDTO;
import com.gaurashtra.app.model.bean.OptionListBean;
import com.gaurashtra.app.model.bean.OrderBean.OrderDetailsBean;
import com.gaurashtra.app.model.bean.OrderBean.OrderListBean;
import com.gaurashtra.app.model.bean.OrderBean.OrderedProductDetails;
import com.gaurashtra.app.model.bean.PaytmChecksumBean;
import com.gaurashtra.app.model.bean.ProductBasicDetailsResult;
import com.gaurashtra.app.model.bean.ProductRemainingResult;
import com.gaurashtra.app.model.bean.Registerbean.RegistrationResponseDTO;
import com.gaurashtra.app.model.bean.ReviewResult;
import com.gaurashtra.app.model.bean.SearchProductResult;
import com.gaurashtra.app.model.bean.TopSellingBean.TopSellingDTO;
import com.gaurashtra.app.model.bean.WalletResponseModel;
import com.gaurashtra.app.model.bean.WishListBean.WishlistDTO;
import com.gaurashtra.app.model.bean.addresslistbean.AddressResponseDTO;
import com.gaurashtra.app.model.bean.categoryDetailListbean.CategoryDetailListResponseDTO;
import com.gaurashtra.app.model.bean.categoryListbean.CategoryListResponseDTO;
import com.gaurashtra.app.model.bean.countrybean.CountryResponseDTO;
import com.gaurashtra.app.model.bean.forgotbean.ForgotResponseDTO;
import com.gaurashtra.app.model.bean.loginbean.LoginResponseDTO;
import com.gaurashtra.app.model.bean.loginbean.SocialLoginResponseDTO;
import com.gaurashtra.app.model.bean.resetbean.ResetResponseDTO;
import com.gaurashtra.app.model.modelInteractor.DeviceLocale;

import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RestInterface {

    @POST("/api/getRegistration")
    @FormUrlEncoded
    Call<RegistrationResponseDTO> callRegistrationService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getForgotPassword")
    @FormUrlEncoded
    Call<ForgotResponseDTO> callForgotPasswordService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getResetPassword")
    @FormUrlEncoded
    Call<ResetResponseDTO> callResetPasswordService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getLogin")
    @FormUrlEncoded
    Call<LoginResponseDTO> callLoginService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getChangePassword")
    @FormUrlEncoded
    Call<ResetResponseDTO> callChangePasswordService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @GET("/api/getCountryList")
    Call<CountryResponseDTO> callCountryListService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate);

    @POST("/api/getCategoryList")
    @FormUrlEncoded
    Call<CategoryListResponseDTO> callCategoryListService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    //subCategoryAPI
    @POST("/api/getCategoryProductList")
    @FormUrlEncoded
    Call<CategoryDetailListResponseDTO> callCategoryDetailListService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getProductDataDetails")
    @FormUrlEncoded
    Call<ProductBasicDetailsResult> callProductDetailsService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getProductOtherDetails")
    @FormUrlEncoded
    Call<ProductRemainingResult> callRemainingDetails(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/manageCart")
    @FormUrlEncoded
    Call<AddCartResponseDTO> callAddToCartService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getAddressList")
    @FormUrlEncoded
    Call<AddressResponseDTO> callAddressListService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getAddEditAddress")
    @FormUrlEncoded
    Call<AddAddressResponseDTO> callAddToAddressService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);
    @POST("/api/getAddressDelete")
    @FormUrlEncoded
    Call<AddressResponseDTO> callAddressDeleteService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getHomePageData")
    @FormUrlEncoded
    Call<HomepageDTO> callHomePageDataService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getHomePageFirstData")
    @FormUrlEncoded
    Call<HomepageDTO> callFirstHomePageData(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);
    @POST("/api/getHomePageSecondData")
    @FormUrlEncoded
    Call<HomepageDTO> callSecondHomePageData(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);
    @POST("/api/getHomePageThirdData")
    @FormUrlEncoded
    Call<HomepageDTO> callThirdHomePageData(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getWishlist")
    @FormUrlEncoded
    Call<WishlistDTO> callMyWishlistService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getBrandList")
    @FormUrlEncoded
    Call<BrandListDTO> callBrandListService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getTopSellingList")
    @FormUrlEncoded
    Call<TopSellingDTO> callTopSellingService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getRecommendedProductList")
    @FormUrlEncoded
    Call<TopSellingDTO> callRecommendedService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getBestOfProductList")
    @FormUrlEncoded
    Call<BestOfDTO> callBestOfService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getRecentlyViewedList")
    @FormUrlEncoded
    Call<TopSellingDTO> callRecentlyService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @POST("/api/getTodayDealList")
    @FormUrlEncoded
    Call<HomepageDTO> callDealsService(@Header("Apikey") String apiKey, @Header("Apidate") String apiDate, @FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("/api/manageWishlist")
    Call<WishlistDTO> addRemoveWishList(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @Field("userid") String userid, @Field("productid") String productid, @Field("actiontype") String actiontype);

    @POST("/api/getCartData")
    @FormUrlEncoded
    Call<GetCartProduct> getCartData(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/applyCoupon")
    @FormUrlEncoded
    Call<GetCartProduct> applyCoupon(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/getBrandProductList")
    @FormUrlEncoded
    Call<BrandProductData> productByBrand(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/setConfirmOrder")
    @FormUrlEncoded
    Call<OrderDetailsBean> setConfirmOrder(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/setPayment")
    @FormUrlEncoded
    Call<OrderDetailsBean> setPaymentSuccess(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate,@FieldMap Map<String,String> param);

    @POST("/api/getOrderList")
    @FormUrlEncoded
    Call<OrderListBean> getOrderList(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/getOrderDetails")
    @FormUrlEncoded
    Call<OrderedProductDetails> getOrderProductDetails(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/setOrderReport")
    @FormUrlEncoded
    Call<OrderedProductDetails> sendProductReport(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/setProductReviewRating")
    @FormUrlEncoded
    Call<ReviewResult> sendProductReviewRating(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/editProfileData")
    @FormUrlEncoded
    Call<LoginResponseDTO> updateUserProfile(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @GET("/api/aboutUs")
    Call<LeftMenuContentBean> getAboutUs(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate);

    @GET("/api/termsConditions")
    Call<LeftMenuContentBean> getTermsCondition(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate);

    @GET("/api/privacyPolicy")
    Call<LeftMenuContentBean> getPrivacyPolicy(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate);

    @GET("/api/refundCancellationPolicy")
    Call<LeftMenuContentBean> getRefundCancellationPolicy(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate);

    @GET("/api/offers")
    Call<LeftMenuContentBean> getOffers(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate);

    @POST("/api/globalSearch")
    @FormUrlEncoded
    Call<SearchProductResult> searchProductList(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/askAQuestion")
    @FormUrlEncoded
    Call<SearchProductResult> askQuestion(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    //Added By Laukendra

    @GET("/api/getCurrencyList")
    Call<GetCurrencyList> getCurrencyList(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate);

    @POST("/api/getCurrencyDetails")
    @FormUrlEncoded
    Call<GetCurrencyDetail> getCurrencyDetail(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    // till this

    @POST("/api/getProductWithReviewRating")
    @FormUrlEncoded
    Call<ReviewResponceDTO> myReviewList(@Header("Apikey") String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("/api/getCartInfo")
    @FormUrlEncoded
    Call<CartInfoDTO> getPaymentMethodsInfo(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @GET("/json")
    Call<DeviceLocale> getLocale(@Query("fields")String ipAddress);

    @POST("api/getNotificationList")
    @FormUrlEncoded
    Call<NotificationDTO> getNotificationList(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("api/deleteNotification")
    @FormUrlEncoded
    Call<NotificationDTO> callDeleteNotification(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate,  @FieldMap Map<String, String> param);

    @POST("api/getSocialLogin")
    @FormUrlEncoded
    Call<SocialLoginResponseDTO> addSocialLogin(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("api/setMobileNumber")
    @FormUrlEncoded
    Call<SocialLoginResponseDTO> addPhoneNumber(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("api/getPaytmChecksum")
    @FormUrlEncoded
    Call<PaytmChecksumBean> getPaytmChecksum(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("api/getPayumoneyHash")
    @FormUrlEncoded
    Call<PaytmChecksumBean> getPayUMoneyChecksum(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> param);

    @POST("api/getProductOptionList")
    @FormUrlEncoded
    Call<OptionListBean> getOptionList(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @Field ("userid") String userId, @Field ("productid") String productid);

    @POST("/api/notifyMe")
    @FormUrlEncoded
    Call<JSONObject> setNotifyMe(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> map);

    @POST("/api/addRecentlyView")
    @FormUrlEncoded
    Call<JSONObject> addToRecentList(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> map);

    @POST("/api/checkUserLoggedin")
    @FormUrlEncoded
    Call<UserUpdateModel> getUserStatus(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @Field ("userid") String userId);

    @GET("/api/getContactUsContent")
    Call<ContactUsResult> getContactUsContent(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate);

    @POST("/api/checkOnlyPincode")
    @FormUrlEncoded
    Call<ProductdetailsDTO> checkPincode(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @Field ("userid") String userId, @Field("postcode") String postCode);

    @POST("/api/contactUs")
    @Multipart
    Call<ContactUsResponse> callSubmitQuery(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @PartMap Map<String, RequestBody> map,  @Part MultipartBody.Part comm_image);

    @POST("/api/getWalletData")
    @FormUrlEncoded
    Call<WalletResponseModel> getWalletData(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @Field ("userid") String userId);

    @POST("/api/generateOrderIdForRazarpay")
    @FormUrlEncoded
    Call<RazorpayOrderResponse> getRazorPayOderId(@Header("Apikey")String Apikey, @Header("Apidate") String Apidate, @FieldMap Map<String, String> map);
}
