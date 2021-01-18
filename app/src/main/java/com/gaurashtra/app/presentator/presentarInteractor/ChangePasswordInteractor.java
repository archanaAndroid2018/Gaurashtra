package com.gaurashtra.app.presentator.presentarInteractor;

public interface ChangePasswordInteractor {
    public void validateRequest(String uid, String oldPass, String newPass, String confPass);
    public void sendResponse(Object object);
}
