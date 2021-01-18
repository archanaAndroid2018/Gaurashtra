package com.gaurashtra.app.presentator.presentarInteractor;

public interface AddressPresentarInteractor {
    public void sendCountryRequest();
    public void sendCountryResponse(Object object);
    public void sendListResponse(Object object);
    public void sendAddressListRequest(String uid);
    public void sendAddressDeleteRequest(String uid, String addressId);
    public void sendAddressDeleteResponce(Object object);
    public void validateAddressData(String addressId, String uid, String fname, String lname, String add1, String add2, String city
            , String pin, String country, String state, Boolean isStateSelected, Boolean isCountrySelected, String daddress, String company, String phone);
    public void sendValidationResponse(Object object);
}
