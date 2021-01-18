package com.gaurashtra.app.model.bean;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.ListofbrandInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ListofBrandPresentarInteractor;

public class ListOfBrandModel implements ListofbrandInteractor, WebServiceResponse {
    private GlobalUtils globalUtils;
    private ListofBrandPresentarInteractor listofBrandPresentarInteractor;
    private ListofbrandInteractor listofbrandInteractor;


    public ListOfBrandModel(ListofBrandPresentarInteractor listofBrandPresentarInteractor){
        this.listofBrandPresentarInteractor=listofBrandPresentarInteractor;
        this.globalUtils=new GlobalUtils();

    }


    @Override
    public void callBrandListAPI(String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        Log.e("UserId",uid);
        new WebServiceHandler().callBrandListAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }
    @Override
    public void OnSuccess(Object object) {
        listofBrandPresentarInteractor.getResponse(object);

    }

    @Override
    public void OnFailure() {

    }


}
