package com.gaurashtra.app.presentator.presentarInteractor;

public interface LoginPresentatorInteractor {
    public void validateRequest(String mail, String pass, String id);
    public void sendResponse(Object object);
}
