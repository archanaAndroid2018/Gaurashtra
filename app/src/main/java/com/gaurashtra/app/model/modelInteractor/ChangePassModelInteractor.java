package com.gaurashtra.app.model.modelInteractor;

public interface ChangePassModelInteractor {
    public void callChangePassAPI(String uid, String oldPass, String newPass, String confPass);
}
