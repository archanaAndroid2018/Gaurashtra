package com.gaurashtra.app.view.activity.interactor;

public interface BrandListActivityIntegractor {
    public void showProgress();
    public void hideProgress();
    public void validationResponse(String msg);
    public void getCategoryResponse(Object object);
    //public void getCartResponse(Object object);

}
