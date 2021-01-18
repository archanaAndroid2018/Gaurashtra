package com.gaurashtra.app.model.modelInteractor;

public interface RegisterModelIntractor {
    public void callRegistrationAPI(String name, String lastName, String phone, String mail, String pass, String deviceType, String deviceId, String ipAddress);
}
