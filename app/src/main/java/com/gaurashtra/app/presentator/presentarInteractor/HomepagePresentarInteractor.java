package com.gaurashtra.app.presentator.presentarInteractor;

public interface HomepagePresentarInteractor {
    public void sendHomeRequest(String userid, String deviceId);
    public void getHomeResponse(Object object);
}
