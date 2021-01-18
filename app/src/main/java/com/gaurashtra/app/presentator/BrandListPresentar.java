package com.gaurashtra.app.presentator;

import com.gaurashtra.app.model.bean.BrandListModel;
import com.gaurashtra.app.model.modelInteractor.BrandListModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.BrandListPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.BrandListActivityIntegractor;

public class BrandListPresentar implements BrandListPresentarInteractor {
    private BrandListActivityIntegractor brandListActivityIntegractor;
    private BrandListModelInteractor brandListModelInteractor;

    public BrandListPresentar(BrandListActivityIntegractor brandListActivityIntegractor){
        this.brandListActivityIntegractor = brandListActivityIntegractor;
        brandListModelInteractor = new BrandListModel(this);
    }
    @Override
    public void validateCategoryRequest(String uid, String cid, String pageNo, String perpageData) {
        if (cid.isEmpty()){
            brandListActivityIntegractor.validationResponse("Category id is empty");
        }else {
            brandListActivityIntegractor.showProgress();
            brandListModelInteractor.callCategoryDetailListAPI(uid,cid,pageNo , perpageData);
        }
    }

    @Override
    public void sendCategoryResponse(Object object) {
         brandListActivityIntegractor.getCategoryResponse(object);
        brandListActivityIntegractor.hideProgress();
    }

    @Override
    public void validateCartRequest(String uid, String pid, String qty) {
       if (uid.length() == 0){
           brandListActivityIntegractor.validationResponse("Something went wrong");
       }else if (pid.length() == 0){
           brandListActivityIntegractor.validationResponse("Something went wrong");
       }else if (qty.length() == 0){
           brandListActivityIntegractor.validationResponse("Something went wrong");
       }else {
           brandListActivityIntegractor.showProgress();
           //brandListModelInteractor.callCartAddAPI(uid,pid,qty);
       }
    }

    @Override
    public void sendCartResponse(Object object) {
        brandListActivityIntegractor.hideProgress();
       //brandListActivityIntegractor.getCartResponse(object);
    }
}
