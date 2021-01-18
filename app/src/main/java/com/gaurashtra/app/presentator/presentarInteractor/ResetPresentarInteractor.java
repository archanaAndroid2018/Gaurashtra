package com.gaurashtra.app.presentator.presentarInteractor;

public interface ResetPresentarInteractor {
    public void validateRequest(String otp, String pass, String conPass);
    public void sendResponse1(Object object);
}
