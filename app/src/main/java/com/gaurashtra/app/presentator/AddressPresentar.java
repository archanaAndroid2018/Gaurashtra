package com.gaurashtra.app.presentator;

import com.gaurashtra.app.model.bean.AddressModel;
import com.gaurashtra.app.model.modelInteractor.AddressModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.AddressPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.AddressActivityInteractor;

public class AddressPresentar implements AddressPresentarInteractor {
    private AddressActivityInteractor addressActivityInteractor;
    private AddressModelInteractor addressModelInteractor;

    public AddressPresentar(AddressActivityInteractor addressActivityInteractor){
        this.addressActivityInteractor = addressActivityInteractor;
        addressModelInteractor = new AddressModel(this);
    }
    @Override
    public void sendCountryRequest() {
        addressActivityInteractor.showProgress();
        addressModelInteractor.callAddressCountryAPI();
    }

    @Override
    public void sendCountryResponse(Object object) {
        addressActivityInteractor.hideProgress();
        addressActivityInteractor.getResponse(object);
    }

    @Override
    public void sendListResponse(Object object) {
        addressActivityInteractor.hideProgress();
        addressActivityInteractor.getListResponse(object);
    }

    @Override
    public void sendAddressListRequest(String uid) {
        addressActivityInteractor.showProgress();
        addressModelInteractor.callAddressListAPI(uid);
    }

    @Override
    public void sendAddressDeleteRequest(String uid, String addressId) {
        addressModelInteractor.callAddressDeleteAPI(uid,addressId);

    }

    @Override
    public void sendAddressDeleteResponce(Object object) {
        addressActivityInteractor.getAddressDeleteResponce(object);
    }

    @Override
    public void validateAddressData(String addressId, String uid, String fname, String lname, String add1, String add2, String city, String pin, String country, String state, Boolean isStateSelected, Boolean isCountrySelected, String daddress, String company, String phone) {
        try {

            if (uid.length() == 0) {
                addressActivityInteractor.validateResponse("Something went wrong");
            } else if (fname.length() == 0) {
                addressActivityInteractor.validateResponse("First name is empty");
            } else if (lname.length() == 0) {
                addressActivityInteractor.validateResponse("Last name is empty");
            } else if (add1.length() == 0) {
                addressActivityInteractor.validateResponse("Address is empty");
            } else if (city.length() == 0) {
                addressActivityInteractor.validateResponse("City is empty");
            } else if (pin.length() == 0) {
                addressActivityInteractor.validateResponse("Postal Code is empty");
            } else if (!isCountrySelected) {
                addressActivityInteractor.validateResponse("Country name is empty");
            } else if (!isStateSelected) {
                addressActivityInteractor.validateResponse("State name is empty");
            } else if (daddress.equals("")) {
                addressActivityInteractor.validateResponse("Default address is empty");
            } else {
                addressActivityInteractor.showProgress();
                addressModelInteractor.callAddAddressAPI(uid, fname, lname, add1, add2, city, pin, country, state, daddress, company, phone, addressId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendValidationResponse(Object object) {
         addressActivityInteractor.hideProgress();
         addressActivityInteractor.getAddressResponse(object);
    }
}
