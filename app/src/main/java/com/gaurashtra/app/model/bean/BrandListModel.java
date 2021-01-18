package com.gaurashtra.app.model.bean;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.api.servicesResponse.CartResponseServices;
import com.gaurashtra.app.model.modelInteractor.BrandListModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.BrandListPresentarInteractor;

public class BrandListModel implements BrandListModelInteractor, WebServiceResponse, CartResponseServices {
    private BrandListPresentarInteractor brandListPresentarInteractor;
    private GlobalUtils globalUtils;

    public BrandListModel(BrandListPresentarInteractor brandListPresentarInteractor) {
        this.brandListPresentarInteractor = brandListPresentarInteractor;
        globalUtils = new GlobalUtils();
    }

    @Override
    public void callCategoryDetailListAPI(String uid, String cid, String pageNo, String perpageData) {
        Map<String, String> map = new HashMap<>();
        map.put("catid", cid);
        map.put("userid",uid);
        map.put("pageNo",pageNo);
        map.put("perpageData",perpageData);
        new WebServiceHandler().callCategoryDetailListAPI(this, globalUtils.getKey(), globalUtils.getapidate(), map);
    }

//    @Override
//    public void callCartAddAPI(String uid, String pid, String qty) {
//        Map<String, String> map = new HashMap<>();
//        map.put("userid", uid);
//        map.put("product_id", pid);
//        new WebServiceHandler().callAddToCartAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
//    }

    @Override
    public void OnSuccess(Object object) {
        brandListPresentarInteractor.sendCategoryResponse(object);
    }

    @Override
    public void OnFailure() {

    }

//    @Override
//    public void cartOnSuccess(Object object) {
//         brandListPresentarInteractor.sendCartResponse(object);
//    }
//
//    @Override
//    public void cartOnFailure() {
//
//    }
}
