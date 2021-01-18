package com.gaurashtra.app.model.bean;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.ForgotModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ForgotPresentarInteractor;

public class ForgotModel implements ForgotModelInteractor, WebServiceResponse {
    private ForgotPresentarInteractor forgotPresentarInteractor;
    private GlobalUtils globalUtils;

    public ForgotModel(ForgotPresentarInteractor forgotPresentarInteractor){
        this.forgotPresentarInteractor = forgotPresentarInteractor;
        globalUtils = new GlobalUtils();
    }
    @Override
    public void callForgotAPI(String email) {
        Map<String, String> map = new HashMap<>();
        map.put("email",email);
        new WebServiceHandler().callForgotPasswordAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void OnSuccess(Object object) {
        forgotPresentarInteractor.sendResponse(object);
    }

    @Override
    public void OnFailure() {

    }
}
