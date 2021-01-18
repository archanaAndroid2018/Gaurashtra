package com.gaurashtra.app.presentator;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.ProductDetailsModel;
import com.gaurashtra.app.model.modelInteractor.ProductDetailsModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ProductDetailsPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ProductDetalisInteractor;

public class ProductDetailsPresentar implements ProductDetailsPresentarInteractor {
    private ProductDetalisInteractor productDetalisInteractor;
    private GlobalUtils globalUtils;
    private ProductDetailsModelInteractor productDetailsModelInteractor;

    public ProductDetailsPresentar(ProductDetalisInteractor productDetalisInteractor){
        this.productDetalisInteractor=productDetalisInteractor;
        productDetailsModelInteractor=new ProductDetailsModel(this);
        globalUtils=new GlobalUtils();

    }
    @Override
    public void sendProductDetailsRequest(String userid, String Pid, String optionId) {
        productDetailsModelInteractor.callProductDetailsAPI(userid,Pid, optionId);

    }

    @Override
    public void getProductDetailsResponse(Object object) {
        productDetalisInteractor.getProductDataResponse(object);

    }
}
