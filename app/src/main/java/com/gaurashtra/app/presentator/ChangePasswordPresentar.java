package com.gaurashtra.app.presentator;

import com.gaurashtra.app.model.bean.ChangePasswordModel;
import com.gaurashtra.app.model.modelInteractor.ChangePassModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ChangePasswordInteractor;
import com.gaurashtra.app.view.activity.interactor.ChangePasswordActivityInteractor;

public class ChangePasswordPresentar implements ChangePasswordInteractor {
    private ChangePassModelInteractor changePassModelInteractor;
    private ChangePasswordActivityInteractor changePasswordActivityInteractor;

    public ChangePasswordPresentar(ChangePasswordActivityInteractor changePasswordActivityInteractor){
        this.changePasswordActivityInteractor = changePasswordActivityInteractor;
        changePassModelInteractor = new ChangePasswordModel(this);
    }
    @Override
    public void validateRequest(String uid, String oldPass, String newPass, String confPass) {
        if (uid.length() == 0){
            changePasswordActivityInteractor.validationResponse("Something went wrong");
        }else if (oldPass.length() == 0){
            changePasswordActivityInteractor.validationResponse("Please enter old password");
        }else if (newPass.length() == 0){
            changePasswordActivityInteractor.validationResponse("Please enter new password");
        }else if (confPass.length() == 0){
            changePasswordActivityInteractor.validationResponse("Please enter confirm password");
        }else if (!newPass.equals(confPass)){
            changePasswordActivityInteractor.validationResponse("Password does not match");
        }else {
            changePasswordActivityInteractor.showProgress();
            changePassModelInteractor.callChangePassAPI(uid,oldPass,newPass,confPass);
        }
    }

    @Override
    public void sendResponse(Object object) {
         changePasswordActivityInteractor.hideProgress();
         changePasswordActivityInteractor.getResponse(object);
    }
}
