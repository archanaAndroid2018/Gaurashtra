package com.gaurashtra.app.model.modelInteractor;

public interface ProductListActivityModelInteractor {
    public void callRecommendedAPI(String uid, String pageNo, String perpageData);
    public void callBestOfAPI(String uid, String pageNo, String perpageData);
    public void callTopSellingAPI(String uid, String pageNo, String perpageData);
    public void callRecentlyAPI(String uid, String deviceid, String pageNo, String perpageData);
    public void callTodayDealAPI(String uid, String pageNo, String perpageData);

}
