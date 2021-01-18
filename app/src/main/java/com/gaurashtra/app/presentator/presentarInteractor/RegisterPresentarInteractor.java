package com.gaurashtra.app.presentator.presentarInteractor;

public interface RegisterPresentarInteractor {
    public void sendRequest(String name, String lastName, String phone, String email, String pass, String conPass, String deviceType, String deviceId, String ipAddress);
    public void sendResponse(Object object);
}
