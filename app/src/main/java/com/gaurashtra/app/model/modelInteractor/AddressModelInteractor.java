package com.gaurashtra.app.model.modelInteractor;

public interface AddressModelInteractor {
    public void callAddressCountryAPI();
    public void callAddressDeleteAPI(String uid, String addressId);
    public void callAddressListAPI(String uid);
    public void callAddAddressAPI(String uid, String fname, String lname, String add1, String add2, String city
            , String pin, String country, String state, String daddress, String company, String phone, String addressId);
}
