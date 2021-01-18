package com.gaurashtra.app.model.bean;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.HomepageModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.HomepagePresentarInteractor;

public class HomePageModel implements HomepageModelInteractor, WebServiceResponse {
    private HomepagePresentarInteractor homepagePresentarInteractor;
    private GlobalUtils globalUtils;


    public HomePageModel(HomepagePresentarInteractor homepagePresentarInteractor) {
        this.homepagePresentarInteractor=homepagePresentarInteractor;
        this.globalUtils=new GlobalUtils();
    }

    @Override
    public void callHomePageAPI(String uid, String deviceId) {
        Map<String, String> map = new HashMap<>();
        try {
            if (!uid.isEmpty()) {
                map.put("userid", uid);
            }
            map.put("deviceid", deviceId);
            new WebServiceHandler().callHomePageAPI(this, globalUtils.getKey(), globalUtils.getapidate(), map);
        }catch (Exception e){
            Log.e("EXCinHomedata",":"+e);
        }

    }
    @Override
    public void OnSuccess(Object object) {
        homepagePresentarInteractor.getHomeResponse(object);
    }

    @Override
    public void OnFailure() {

    }

    
}
