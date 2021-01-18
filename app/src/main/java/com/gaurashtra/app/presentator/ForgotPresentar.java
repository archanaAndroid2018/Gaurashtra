package com.gaurashtra.app.presentator;

import android.util.Log;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.ForgotModel;
import com.gaurashtra.app.model.modelInteractor.ForgotModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ForgotPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ForgotActivityInteractor;
import com.google.gson.GsonBuilder;

public class ForgotPresentar implements ForgotPresentarInteractor {
    private ForgotModelInteractor forgotModelInteractor;
    private ForgotActivityInteractor forgotActivityInteractor;
    private GlobalUtils globalUtils;

    public ForgotPresentar(ForgotActivityInteractor forgotActivityInteractor){
       this.forgotActivityInteractor = forgotActivityInteractor;
       forgotModelInteractor = new ForgotModel(this);
       globalUtils = new GlobalUtils();
    }
    @Override
    public void validateRequest(String email) {
         if (!globalUtils.isEmailValidate(email)){
             forgotActivityInteractor.validateCredential("Please enter valid email");
         }else {
             forgotActivityInteractor.showProgress();
             forgotModelInteractor.callForgotAPI(email);
         }
    }

    @Override
    public void sendResponse(Object object) {
         forgotActivityInteractor.hideProgress();
         forgotActivityInteractor.getResponse(object);
         Log.e("ForgotPwdRes",":"+new GsonBuilder().setPrettyPrinting().create().toJson(object));
    }
}
