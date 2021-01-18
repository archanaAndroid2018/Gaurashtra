package com.gaurashtra.app.view.activity.interactor;

public interface AddressActivityInteractor {
    public void showProgress();
    public void hideProgress();
    public void validateResponse(String msg);
    public void getListResponse(Object object);
    public void getResponse(Object object);
    public void getAddressResponse(Object object);
    public void getAddressDeleteResponce(Object object);
}
