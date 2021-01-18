package com.gaurashtra.app.presentator.presentarInteractor;

public interface ProductListActivityPresentarInteractor {
    public void sendRequest(String uid, String pageNo, String perpageData);

    public void sendResponse(Object object);

    public void sendBestRequest(String uid, String pageNo, String perpageData);

    public void sendBEstResponse(Object object);

    public void sendRecentlyRequest(String uid, String deviceId, String pageNo, String perpageData);

    public void sendRecentlyResponse(Object object);

    public void sendRecommendRequest(String uid, String pageNo, String perpageData);

    public void sendRecommendResponse(Object object);

    public void sendDealsRequest(String uid, String pageNo, String perpageData);
    public void sendDealsResponse(Object object);
}
