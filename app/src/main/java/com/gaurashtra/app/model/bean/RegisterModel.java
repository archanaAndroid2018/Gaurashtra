package com.gaurashtra.app.model.bean;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.RegisterModelIntractor;
import com.gaurashtra.app.presentator.presentarInteractor.RegisterPresentarInteractor;

public class RegisterModel implements RegisterModelIntractor, WebServiceResponse {
    private RegisterPresentarInteractor registerPresentarInteractor;
    private GlobalUtils globalUtils;

    public RegisterModel(RegisterPresentarInteractor registerPresentarInteractor){
        this.registerPresentarInteractor = registerPresentarInteractor;
        globalUtils = new GlobalUtils();
    }
    @Override
    public void callRegistrationAPI(String name, String lastName , String phone, String mail, String pass, String deviceType, String deviceId, String ipaddress) {
        Map<String, String> map = new HashMap<>();
        map.put("firstname",name);
        map.put("lastname",lastName);
        map.put("telephone",phone);
        map.put("email",mail);
        map.put("password",pass);
        map.put("devicetype",deviceType);
        map.put("deviceid",deviceId);
        map.put("ipaddress",ipaddress);
        new WebServiceHandler().callRegistrationAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void OnSuccess(Object object) {
        registerPresentarInteractor.sendResponse(object);
    }

    @Override
    public void OnFailure() {

    }

}
