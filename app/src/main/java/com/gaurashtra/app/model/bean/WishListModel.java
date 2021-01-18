package com.gaurashtra.app.model.bean;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.WishListModelinteractor;
import com.gaurashtra.app.presentator.presentarInteractor.WishListPresentarInteractor;

public class WishListModel implements WishListModelinteractor, WebServiceResponse {
    private WishListPresentarInteractor wishListPresentarInteractor;
    private GlobalUtils globalUtils;


    public WishListModel(WishListPresentarInteractor wishListPresentarInteractor){
        this.wishListPresentarInteractor=wishListPresentarInteractor;
        globalUtils=new GlobalUtils();

    }

    @Override
    public void callWishListAPI(String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);

        new WebServiceHandler().callWishListAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }



    @Override
    public void OnSuccess(Object object) {
        wishListPresentarInteractor.getResponse(object);
    }

    @Override
    public void OnFailure() {

    }


}
