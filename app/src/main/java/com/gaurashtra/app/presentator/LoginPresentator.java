package com.gaurashtra.app.presentator;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.LoginModel;
import com.gaurashtra.app.model.modelInteractor.LoginModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.LoginPresentatorInteractor;
import com.gaurashtra.app.view.activity.interactor.LoginActivityInteractor;

public class LoginPresentator implements LoginPresentatorInteractor {
    private LoginActivityInteractor loginActivityInteractor;
    private LoginModelInteractor loginModelInteractor;
    private GlobalUtils globalUtils;

    public LoginPresentator(LoginActivityInteractor loginActivityInteractor){
        this.loginActivityInteractor = loginActivityInteractor;
        loginModelInteractor = new LoginModel(this);
        globalUtils = new GlobalUtils();
    }
    @Override
    public void validateRequest(String mail, String pass, String dId) {
        if (!globalUtils.isEmailValidate(mail)){
            loginActivityInteractor.validateCredential("Please enter valid email");
        }else if (pass.length() == 0){
            loginActivityInteractor.validateCredential("Please enter password");
        }else {
            loginActivityInteractor.showProgress();
            loginModelInteractor.callLoginApi(mail,pass,dId);
        }
    }

    @Override
    public void sendResponse(Object object) {
       // loginActivityInteractor.hideProgress();
        loginActivityInteractor.getResponse(object);
    }
}
