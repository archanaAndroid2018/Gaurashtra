package com.gaurashtra.app.model.api;

import android.util.Log;

import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.servicesResponse.AddAddressResponse;
import com.gaurashtra.app.model.api.servicesResponse.AddressListResponse;
import com.gaurashtra.app.model.api.servicesResponse.BestOfHomeResponce;
import com.gaurashtra.app.model.api.servicesResponse.CartResponseServices;
import com.gaurashtra.app.model.api.servicesResponse.DealResponse;
import com.gaurashtra.app.model.api.servicesResponse.DeleteAddressResponse;
import com.gaurashtra.app.model.api.servicesResponse.RecentlyHomeResponce;
import com.gaurashtra.app.model.api.servicesResponse.RecommendedResponce;
import com.gaurashtra.app.model.bean.AddAddressBean.AddAddressResponseDTO;
import com.gaurashtra.app.model.bean.BestOfBean.BestOfDTO;
import com.gaurashtra.app.model.bean.BrandListBean.BrandListDTO;
import com.gaurashtra.app.model.bean.HomePagebean.HomepageDTO;
import com.gaurashtra.app.model.bean.ProductBasicDetailsResult;
import com.gaurashtra.app.model.bean.ProductDetails.ProductdetailsDTO;
import com.gaurashtra.app.model.bean.Registerbean.RegistrationResponseDTO;
import com.gaurashtra.app.model.bean.TopSellingBean.TopSellingDTO;
import com.gaurashtra.app.model.bean.WishListBean.WishlistDTO;
import com.gaurashtra.app.model.bean.addresslistbean.AddressResponseDTO;
import com.gaurashtra.app.model.bean.categoryDetailListbean.CategoryDetailListResponseDTO;
import com.gaurashtra.app.model.bean.categoryListbean.CategoryListResponseDTO;
import com.gaurashtra.app.model.bean.countrybean.CountryResponseDTO;
import com.gaurashtra.app.model.bean.forgotbean.ForgotResponseDTO;
import com.gaurashtra.app.model.bean.loginbean.LoginResponseDTO;
import com.gaurashtra.app.model.bean.resetbean.ResetResponseDTO;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServiceHandler {

    private RestInterface restInterface;
    private WebServiceResponse webServiceResponse;
    private AddressListResponse addressListResponse;
    private CartResponseServices cartResponseServices;
    private AddAddressResponse addAddressResponse;
    private DeleteAddressResponse deleteAddressResponse;
    private RecentlyHomeResponce recentlyHomeResponce;
    private RecommendedResponce recommendedResponce;
    private BestOfHomeResponce bestOfResponce;
    private DealResponse dealResponse;
    GlobalUtils globalUtils;

    public WebServiceHandler(){
        restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
    }

    public void callRegistrationAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<RegistrationResponseDTO> call = restInterface.callRegistrationService(apiKey, apiDate, regData);
        call.enqueue(new Callback<RegistrationResponseDTO>() {
            @Override
            public void onResponse(Call<RegistrationResponseDTO> call, Response<RegistrationResponseDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<RegistrationResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });
    }

    public void callForgotPasswordAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<ForgotResponseDTO> call = restInterface.callForgotPasswordService(apiKey, apiDate, regData);
        call.enqueue(new Callback<ForgotResponseDTO>() {
            @Override
            public void onResponse(Call<ForgotResponseDTO> call, Response<ForgotResponseDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ForgotResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });
    }

    public void callResetPasswordAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<ResetResponseDTO> call = restInterface.callResetPasswordService(apiKey, apiDate, regData);
        call.enqueue(new Callback<ResetResponseDTO>() {
            @Override
            public void onResponse(Call<ResetResponseDTO> call, Response<ResetResponseDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResetResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });
    }

    public void callLoginAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<LoginResponseDTO> call = restInterface.callLoginService(apiKey, apiDate, regData);
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });
    }


    public void callChangePasswordAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<ResetResponseDTO> call = restInterface.callChangePasswordService(apiKey, apiDate, regData);
        call.enqueue(new Callback<ResetResponseDTO>() {
            @Override
            public void onResponse(Call<ResetResponseDTO> call, Response<ResetResponseDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResetResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });
    }


    public void callCountryListAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate) {
        this.webServiceResponse = webServiceResponse;
        Call<CountryResponseDTO> call = restInterface.callCountryListService(apiKey, apiDate);
        call.enqueue(new Callback<CountryResponseDTO>() {
            @Override
            public void onResponse(Call<CountryResponseDTO> call, Response<CountryResponseDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<CountryResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });
    }


    public void callAddressListdAPI(final AddressListResponse addressListResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.addressListResponse = addressListResponse;
        Call<AddressResponseDTO> call = restInterface.callAddressListService(apiKey, apiDate, regData);
        call.enqueue(new Callback<AddressResponseDTO>() {
            @Override
            public void onResponse(Call<AddressResponseDTO> call, Response<AddressResponseDTO> response) {
                addressListResponse.addressSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AddressResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
               addressListResponse.addressFailure();
            }
        });
    }
    public void callAddressDeleteAPI(final DeleteAddressResponse deleteAddressResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.deleteAddressResponse = deleteAddressResponse;
        Call<AddressResponseDTO> call = restInterface.callAddressDeleteService(apiKey, apiDate, regData);
        call.enqueue(new Callback<AddressResponseDTO>() {
            @Override
            public void onResponse(Call<AddressResponseDTO> call, Response<AddressResponseDTO> response) {
                deleteAddressResponse.addressDeleteSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AddressResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                deleteAddressResponse.addressDeleteFailure();
            }
        });
    }

    public void callAddToAddresstAPI(final AddAddressResponse addAddressResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.addAddressResponse = addAddressResponse;
        Call<AddAddressResponseDTO> call = restInterface.callAddToAddressService(apiKey, apiDate, regData);
        call.enqueue(new Callback<AddAddressResponseDTO>() {
            @Override
            public void onResponse(Call<AddAddressResponseDTO> call, Response<AddAddressResponseDTO> response) {
                addAddressResponse.onAddAddressSuccess(response.body());
            }

            @Override
            public void onFailure(Call<AddAddressResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                addAddressResponse.onAddAddressFailure();
            }
        });
    }

    public void callCategoryListAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> map) {
        this.webServiceResponse = webServiceResponse;
        Call<CategoryListResponseDTO> call = restInterface.callCategoryListService(apiKey, apiDate,map );

        call.enqueue(new Callback<CategoryListResponseDTO>() {
            @Override
            public void onResponse(Call<CategoryListResponseDTO> call, Response<CategoryListResponseDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<CategoryListResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });
    }

//    public void callProductCategoryListAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate,final Map<String, String> map) {
//        this.webServiceResponse = webServiceResponse;
//        Call<CategoryListResponseDTO> call = restInterface.callCategoryListService(apiKey, apiDate, map);
//        call.enqueue(new Callback<CategoryListResponseDTO>() {
//            @Override
//            public void onResponse(Call<CategoryListResponseDTO> call, Response<CategoryListResponseDTO> response) {
//                webServiceResponse.OnSuccess(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<CategoryListResponseDTO> call, Throwable t) {
//                Log.e(getClass().getSimpleName(), t.toString());
//                webServiceResponse.OnFailure();
//            }
//        });
//    }

///SubCategoryAPI
    public void callCategoryDetailListAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<CategoryDetailListResponseDTO> call = restInterface.callCategoryDetailListService(apiKey, apiDate, regData);
        call.enqueue(new Callback<CategoryDetailListResponseDTO>() {
            @Override
            public void onResponse(Call<CategoryDetailListResponseDTO> call, Response<CategoryDetailListResponseDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }
            @Override
            public void onFailure(Call<CategoryDetailListResponseDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
               webServiceResponse.OnFailure();
            }
        });

    }
    //Details of subcategory

    public void callProductDetailsAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<ProductBasicDetailsResult> call = restInterface.callProductDetailsService(apiKey, apiDate, regData);
        call.enqueue(new Callback<ProductBasicDetailsResult>() {
            @Override
            public void onResponse(Call<ProductBasicDetailsResult> call, Response<ProductBasicDetailsResult> response) {
                webServiceResponse.OnSuccess(response.body());
                Log.e("ResponseProdDetails",":"+response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ProductBasicDetailsResult> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });

    }


    public void callHomePageAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<HomepageDTO> call = restInterface.callHomePageDataService(apiKey, apiDate, regData);
        call.enqueue(new Callback<HomepageDTO>() {
            @Override
            public void onResponse(Call<HomepageDTO> call, Response<HomepageDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<HomepageDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });

    }


    public void callWishListAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<WishlistDTO> call = restInterface.callMyWishlistService(apiKey, apiDate, regData);
        call.enqueue(new Callback<WishlistDTO>() {
            @Override
            public void onResponse(Call<WishlistDTO> call, Response<WishlistDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<WishlistDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });

    }

    public void callBrandListAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<BrandListDTO> call = restInterface.callBrandListService(apiKey, apiDate, regData);
        call.enqueue(new Callback<BrandListDTO>() {
            @Override
            public void onResponse(Call<BrandListDTO> call, Response<BrandListDTO> response) {
                webServiceResponse.OnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BrandListDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                webServiceResponse.OnFailure();
            }
        });

    }
    public void callTopSellingAPI(final WebServiceResponse webServiceResponse, String apiKey, String apiDate, final Map<String, String> regData) {
        this.webServiceResponse = webServiceResponse;
        Call<TopSellingDTO> call = restInterface.callTopSellingService(apiKey, apiDate, regData);
        call.enqueue(new Callback<TopSellingDTO>() {
            @Override
            public void onResponse(Call<TopSellingDTO> call, Response<TopSellingDTO> response) {
                webServiceResponse.OnSuccess(response.body());
                Log.e("ResponseTopSelling",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<TopSellingDTO> call, Throwable t) {
                Log.e("TopSellingExc",""+ t.toString());
                webServiceResponse.OnFailure();
            }
        });

    }

    public void callBestOfAPI(final BestOfHomeResponce bestOfResponce, String apiKey, String apiDate, final Map<String, String> regData) {
        this.bestOfResponce = bestOfResponce;
        Call<BestOfDTO> call = restInterface.callBestOfService(apiKey, apiDate, regData);
        call.enqueue(new Callback<BestOfDTO>() {
            @Override
            public void onResponse(Call<BestOfDTO> call, Response<BestOfDTO> response) {
                bestOfResponce.BestOfSuccess(response.body());
            }

            @Override
            public void onFailure(Call<BestOfDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                bestOfResponce.BestOfFailure();
            }
        });

    }
    public void callRecentlyAPI(final RecentlyHomeResponce recentlyHomeResponce, String apiKey, String apiDate, final Map<String, String> regData) {
        this.recentlyHomeResponce = recentlyHomeResponce;
        Call<TopSellingDTO> call = restInterface.callRecentlyService(apiKey, apiDate, regData);
        call.enqueue(new Callback<TopSellingDTO>() {
            @Override
            public void onResponse(Call<TopSellingDTO> call, Response<TopSellingDTO> response) {
                recentlyHomeResponce.RecentlyOnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<TopSellingDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                recentlyHomeResponce.RecentlyOnFailure();
            }
        });

    }
    public void callRecommendedAPI(final RecommendedResponce recommendedResponce, String apiKey, String apiDate, final Map<String, String> regData) {
        this.recommendedResponce = recommendedResponce;
        Call<TopSellingDTO> call = restInterface.callRecommendedService(apiKey, apiDate, regData);
        call.enqueue(new Callback<TopSellingDTO>() {
            @Override
            public void onResponse(Call<TopSellingDTO> call, Response<TopSellingDTO> response) {
                recommendedResponce.RecommenedSuccess(response.body());
            }

            @Override
            public void onFailure(Call<TopSellingDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());
                recommendedResponce.RecommenedFailure();
            }
        });

    }

    public void callDealAPI(final DealResponse dealResponse, String apiKey, String apiDate, final Map<String, String> dealData){
        this.dealResponse = dealResponse;
        Call<HomepageDTO>call = restInterface.callDealsService(apiKey,apiDate,dealData);
        call.enqueue(new Callback<HomepageDTO>() {
            @Override
            public void onResponse(Call<HomepageDTO> call, Response<HomepageDTO> response) {
                Log.e("checkResponse",response.body()+"");
                dealResponse.DealOnSuccess(response.body());
            }

            @Override
            public void onFailure(Call<HomepageDTO> call, Throwable t) {
                Log.e(getClass().getSimpleName(), t.toString());

                dealResponse.DealOnFailure();
            }
        });
    }

}
