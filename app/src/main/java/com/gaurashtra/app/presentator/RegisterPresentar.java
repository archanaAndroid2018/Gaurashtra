package com.gaurashtra.app.presentator;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.RegisterModel;
import com.gaurashtra.app.model.modelInteractor.RegisterModelIntractor;
import com.gaurashtra.app.presentator.presentarInteractor.RegisterPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.RegisterActivityInteractor;

public class RegisterPresentar implements RegisterPresentarInteractor {
    private RegisterActivityInteractor registerActivityInteractor;
    private RegisterModelIntractor registerModelIntractor;
    private GlobalUtils globalUtils;

    public RegisterPresentar(RegisterActivityInteractor registerActivityInteractor){
        this.registerActivityInteractor = registerActivityInteractor;
        registerModelIntractor = new RegisterModel(this);
        globalUtils = new GlobalUtils();
    }
    @Override
    public void sendRequest(String name, String lastName, String phone, String email, String pass, String conpasss, String deviceType, String deviceId, String ipAddress) {
       if (name.length() == 0){
           registerActivityInteractor.validateCredentials("Please enter first name");
       }else if (lastName.length() == 0){
           registerActivityInteractor.validateCredentials("Please enter last name");
       } else if (phone.length() == 0){
           registerActivityInteractor.validateCredentials("Please enter phone number");
       }else if (phone.length() != 10){
           registerActivityInteractor.validateCredentials("Please enter a valid phone number");
       }else if (!globalUtils.isEmailValidate(email)){
           registerActivityInteractor.validateCredentials("Please enter valid email");
       }else if (pass.length() == 0){
           registerActivityInteractor.validateCredentials("Please enter password");
       }else if (!(pass.equals(conpasss))){
           registerActivityInteractor.validateCredentials("Please enter same password");
       }else {
           registerActivityInteractor.showProgress();
           registerModelIntractor.callRegistrationAPI(name,lastName,phone,email,pass, deviceType, deviceId, ipAddress);
       }
    }

    @Override
    public void sendResponse(Object object) {
       registerActivityInteractor.hideProgress();
       registerActivityInteractor.getResponse(object);
    }
}
