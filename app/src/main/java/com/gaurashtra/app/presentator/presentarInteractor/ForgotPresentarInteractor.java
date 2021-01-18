package com.gaurashtra.app.presentator.presentarInteractor;

public interface ForgotPresentarInteractor {
    public void validateRequest(String email);
    public void sendResponse(Object object);
}
