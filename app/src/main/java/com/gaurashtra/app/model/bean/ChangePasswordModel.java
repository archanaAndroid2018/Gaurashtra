package com.gaurashtra.app.model.bean;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.ChangePassModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ChangePasswordInteractor;

public class ChangePasswordModel implements ChangePassModelInteractor, WebServiceResponse {
    private ChangePasswordInteractor changePasswordInteractor;
    private GlobalUtils globalUtils;
    public ChangePasswordModel(ChangePasswordInteractor changePasswordInteractor){
        this.changePasswordInteractor = changePasswordInteractor;
        globalUtils = new GlobalUtils();
    }

    @Override
    public void callChangePassAPI(String uid, String oldPass, String newPass, String confPass) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        map.put("oldpassword",oldPass);
        map.put("password",newPass);
        map.put("conf_pass",confPass);
        new WebServiceHandler().callChangePasswordAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void OnSuccess(Object object) {
       changePasswordInteractor.sendResponse(object);
    }

    @Override
    public void OnFailure() {

    }
}
