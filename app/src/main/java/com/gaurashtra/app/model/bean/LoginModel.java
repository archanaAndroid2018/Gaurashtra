package com.gaurashtra.app.model.bean;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.LoginModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.AddressPresentarInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.LoginPresentatorInteractor;

public class LoginModel implements LoginModelInteractor,WebServiceResponse {
    private LoginPresentatorInteractor loginPresentatorInteractor;
    private GlobalUtils globalUtils;

    public LoginModel(LoginPresentatorInteractor loginPresentatorInteractor){
        this.loginPresentatorInteractor = loginPresentatorInteractor;
        globalUtils = new GlobalUtils();
    }
    @Override
    public void callLoginApi(String email, String pass, String did) {
        Map<String, String> map = new HashMap<>();
        map.put("email",email);
        map.put("password",pass);
        map.put("devicetype","android");
        map.put("deviceid",did);
        new WebServiceHandler().callLoginAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void OnSuccess(Object object) {
        loginPresentatorInteractor.sendResponse(object);
//        try {
//            JSONObject jsonObject = new JSONObject(object.toString());
//            JSONObject result= jsonObject.get
//            String userId= jsonObject.getString("")
//            addressPresentarInteractor.sendAddressListRequest();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

    }

    @Override
    public void OnFailure() {

    }
}
