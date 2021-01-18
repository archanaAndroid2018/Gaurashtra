package com.gaurashtra.app.model.bean;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.api.servicesResponse.BestOfHomeResponce;
import com.gaurashtra.app.model.api.servicesResponse.DealResponse;
import com.gaurashtra.app.model.api.servicesResponse.RecentlyHomeResponce;
import com.gaurashtra.app.model.api.servicesResponse.RecommendedResponce;
import com.gaurashtra.app.model.modelInteractor.ProductListActivityModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ProductListActivityPresentarInteractor;

public class ProductListActivityModel implements ProductListActivityModelInteractor,WebServiceResponse, RecentlyHomeResponce, RecommendedResponce,
        BestOfHomeResponce, DealResponse {
    private ProductListActivityPresentarInteractor productListActivityPresentarInteractor;
    private GlobalUtils globalUtils;
    private PrefClass prefClass;
    private SharedPreference sharedPreference;
    String UserId;

    public ProductListActivityModel(ProductListActivityPresentarInteractor productListActivityPresentarInteractor){
        this.productListActivityPresentarInteractor = productListActivityPresentarInteractor;
        globalUtils = new GlobalUtils();
    }

    @Override
    public void callRecommendedAPI(String uid, String pageNo, String perpageData) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        map.put("pageNo",pageNo);
        map.put("perpageData",perpageData);
        new WebServiceHandler().callRecommendedAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void callBestOfAPI(String uid, String pageNo, String perpageData) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        map.put("pageNo",pageNo);
        map.put("perpageData",perpageData);
        new WebServiceHandler().callBestOfAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void callTopSellingAPI(String uid, String pageNo, String perpageData) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        map.put("pageNo",pageNo);
        map.put("perpageData",perpageData);
        new WebServiceHandler().callTopSellingAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);

    }

    @Override
    public void callRecentlyAPI(String uid, String deviceid, String pageNo, String perpageData) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        map.put("deviceid",deviceid);
        map.put("pageNo",pageNo);
        map.put("perpageData",perpageData);
        new WebServiceHandler().callRecentlyAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);

    }

    @Override
    public void callTodayDealAPI(String uid, String pageNo, String perpageData) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        map.put("pageNo",pageNo);
        map.put("perpageData",perpageData);
        new WebServiceHandler().callDealAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void OnSuccess(Object object) {
        productListActivityPresentarInteractor.sendResponse(object);

    }

    @Override
    public void OnFailure() {

    }

    @Override
    public void RecentlyOnSuccess(Object object) {
        productListActivityPresentarInteractor.sendRecentlyResponse(object);
    }

    @Override
    public void RecentlyOnFailure() {

    }

    @Override
    public void RecommenedSuccess(Object object) {
        productListActivityPresentarInteractor.sendRecommendResponse(object);
    }

    @Override
    public void RecommenedFailure() {

    }

    @Override
    public void BestOfSuccess(Object object) {
        productListActivityPresentarInteractor.sendBEstResponse(object);

    }

    @Override
    public void BestOfFailure() {

    }

    @Override
    public void DealOnSuccess(Object object) {
        productListActivityPresentarInteractor.sendDealsResponse(object);
    }

    @Override
    public void DealOnFailure() {

    }
}
