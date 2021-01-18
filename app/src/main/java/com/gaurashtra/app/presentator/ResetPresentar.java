package com.gaurashtra.app.presentator;

import com.gaurashtra.app.model.bean.ResetModel;
import com.gaurashtra.app.model.modelInteractor.ResetModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ResetPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ResetActivityInteractor;

public class ResetPresentar implements ResetPresentarInteractor {
    private ResetModelInteractor resetModelInteractor;
    private ResetActivityInteractor resetActivityInteractor;

    public ResetPresentar(ResetActivityInteractor resetActivityInteractor){
        this.resetActivityInteractor = resetActivityInteractor;
        resetModelInteractor = new ResetModel(this);
    }
    @Override
    public void validateRequest(String otp, String pass, String conPass) {
        if (otp.length() == 0){
            resetActivityInteractor.validationResponse("Please enter otp");
        }else if (otp.length() != 6){
            resetActivityInteractor.validationResponse("Please enter valid otp");
        }else if (pass.length() == 0){
            resetActivityInteractor.validationResponse("Please enter new password");
        }else if (conPass.length() == 0){
            resetActivityInteractor.validationResponse("Please enter confirm password");
        }else if (!pass.equals(conPass)){
            resetActivityInteractor.validationResponse("Please enter same password");
        }else {
            resetActivityInteractor.showProgress();
            resetModelInteractor.callResetPasswordAPI(otp,pass,conPass);
        }
    }

    @Override
    public void sendResponse1(Object object) {
        // resetActivityInteractor.hideProgress();
         resetActivityInteractor.getResponse(object);
    }
}
