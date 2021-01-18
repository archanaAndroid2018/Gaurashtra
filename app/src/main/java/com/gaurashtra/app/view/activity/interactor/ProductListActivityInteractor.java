package com.gaurashtra.app.view.activity.interactor;

public interface ProductListActivityInteractor {
    public void showProgress();
    public void hideProgress();
    public void getTopSellingCategoryData(Object object);
    public void getBetOfResponse(Object object);
    public void getRecentlyResponse(Object object);
    public void getRecommendResponse(Object object);
    public void getDealResponse(Object object);
}
