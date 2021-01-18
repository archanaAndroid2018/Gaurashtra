package com.gaurashtra.app.model.bean;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.ProductDetailsModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ProductDetailsPresentarInteractor;

public class ProductDetailsModel implements ProductDetailsModelInteractor, WebServiceResponse {
    private GlobalUtils globalUtils;
    private ProductDetailsPresentarInteractor productDetailsPresentarInteractor;

    public ProductDetailsModel(ProductDetailsPresentarInteractor productDetailsPresentarInteractor){
        this.productDetailsPresentarInteractor=productDetailsPresentarInteractor;
        this.globalUtils=new GlobalUtils();

    }

    @Override
    public void callProductDetailsAPI(String uid, String pid, String optionid) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        map.put("productid",pid);
        map.put("optionvalueid",optionid);
        new WebServiceHandler().callProductDetailsAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);

    }

    @Override
    public void OnSuccess(Object object) {
        productDetailsPresentarInteractor.getProductDetailsResponse(object);

    }

    @Override
    public void OnFailure() {

    }


}
