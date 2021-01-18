package com.gaurashtra.app.presentator;

import com.gaurashtra.app.model.bean.ProductListActivityModel;
import com.gaurashtra.app.model.modelInteractor.ProductListActivityModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ProductListActivityPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ProductListActivityInteractor;

public class ProductListActivityPresentar implements ProductListActivityPresentarInteractor {
    private ProductListActivityInteractor productListActivityInteractor;
    private ProductListActivityModelInteractor productListActivityModelInteractor;

    public ProductListActivityPresentar(ProductListActivityInteractor productListActivityInteractor){
        this.productListActivityInteractor = productListActivityInteractor;
        productListActivityModelInteractor = new ProductListActivityModel(this);
    }
//    @Override
//    public void sendProductRequest() {
//        productListActivityInteractor.showProgress();
//        //productListActivityModelInteractor.callProductCategoryListAPI();
//    }
//
//    @Override
//    public void sendProductResponse(Object object) {
//       productListActivityInteractor.hideProgress();
//       productListActivityInteractor.getProductCatResponse(object);
//    }

    @Override
    public void sendRequest(String uid, String pageNo, String perpageData) {
        if(pageNo.equalsIgnoreCase("1")) {
            productListActivityInteractor.showProgress();
        }
        productListActivityModelInteractor.callTopSellingAPI(uid, pageNo, perpageData);


    }

    @Override
    public void sendResponse(Object object) {
        productListActivityInteractor.hideProgress();
        productListActivityInteractor.getTopSellingCategoryData(object);
    }

    @Override
    public void sendBestRequest(String uid, String pageNo, String perpageData) {
        if(pageNo.equalsIgnoreCase("1")) {
            productListActivityInteractor.showProgress();
        }
        productListActivityModelInteractor.callBestOfAPI(uid,pageNo, perpageData);
    }

    @Override
    public void sendBEstResponse(Object object) {
        productListActivityInteractor.hideProgress();
        productListActivityInteractor.getBetOfResponse(object);

    }

    @Override
    public void sendRecentlyRequest(String uid, String deviceid, String pageNo, String perpageData) {
        if(pageNo.equalsIgnoreCase("1")) {
            productListActivityInteractor.showProgress();
        }
        productListActivityModelInteractor.callRecentlyAPI(uid, deviceid,pageNo, perpageData);
    }

    @Override
    public void sendRecentlyResponse(Object object) {
        productListActivityInteractor.hideProgress();
        productListActivityInteractor.getRecentlyResponse(object);
    }

    @Override
    public void sendRecommendRequest(String uid, String pageNo, String perpageData) {
        if(pageNo.equalsIgnoreCase("1")) {
            productListActivityInteractor.showProgress();
        }
        productListActivityModelInteractor.callRecommendedAPI(uid,pageNo, perpageData);
    }

    @Override
    public void sendRecommendResponse(Object object) {
        productListActivityInteractor.hideProgress();
        productListActivityInteractor.getRecommendResponse(object);

    }

    @Override
    public void sendDealsRequest(String uid, String pageNo, String perpageData) {
        if(pageNo.equalsIgnoreCase("1")) {
            productListActivityInteractor.showProgress();
        }
        productListActivityModelInteractor.callTodayDealAPI(uid,pageNo,perpageData);
    }

    @Override
    public void sendDealsResponse(Object object) {
       productListActivityInteractor.hideProgress();
       productListActivityInteractor.getDealResponse(object);
    }
}
