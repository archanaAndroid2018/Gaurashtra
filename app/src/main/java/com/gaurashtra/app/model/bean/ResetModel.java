package com.gaurashtra.app.model.bean;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.ResetModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ResetPresentarInteractor;

public class ResetModel implements ResetModelInteractor,WebServiceResponse{
    private ResetPresentarInteractor resetPresentarInteractor;
    private GlobalUtils globalUtils;

    public ResetModel(ResetPresentarInteractor resetPresentarInteractor){
        this.resetPresentarInteractor = resetPresentarInteractor;
        globalUtils = new GlobalUtils();
    }
    @Override
    public void callResetPasswordAPI(String otp, String pass, String conPass) {
        Map<String, String> map = new HashMap<>();
        map.put("otp",otp);
        map.put("password",pass);
        map.put("conf_pass",conPass);
        new WebServiceHandler().callResetPasswordAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void OnSuccess(Object object) {
        resetPresentarInteractor.sendResponse1(object);
    }

    @Override
    public void OnFailure() {

    }
}
