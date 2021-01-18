package com.gaurashtra.app.model.bean;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.api.servicesResponse.AddAddressResponse;
import com.gaurashtra.app.model.api.servicesResponse.AddressListResponse;
import com.gaurashtra.app.model.api.servicesResponse.DeleteAddressResponse;
import com.gaurashtra.app.model.modelInteractor.AddressModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.AddressPresentarInteractor;
import com.gaurashtra.app.view.activity.AddressActivity;

public class AddressModel implements AddressModelInteractor,WebServiceResponse,AddressListResponse, DeleteAddressResponse,AddAddressResponse {
    private AddressPresentarInteractor addressPresentarInteractor;
    private GlobalUtils globalUtils;
    public AddressModel(AddressPresentarInteractor addressPresentarInteractor){
        this.addressPresentarInteractor = addressPresentarInteractor;
        globalUtils = new GlobalUtils();
    }
    @Override
    public void callAddressCountryAPI() {
       new WebServiceHandler().callCountryListAPI(this,globalUtils.getKey(),globalUtils.getapidate());
    }

    @Override
    public void callAddressDeleteAPI(String uid, String addressId) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        map.put("address_id",addressId);
        new WebServiceHandler().callAddressDeleteAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void callAddressListAPI(String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        new WebServiceHandler().callAddressListdAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void callAddAddressAPI(String uid, String fname, String lname, String add1, String add2, String city, String pin, String country, String state, String daddress, String company, String phone, String addressId) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        map.put("firstname",fname);
        map.put("lastname",lname);
        map.put("address1",add1);
        map.put("address2",add2);
        map.put("city",city);
        map.put("postcode",pin);
        map.put("country",country);
        map.put("state",state);
        map.put("def_address",daddress);
        map.put("address_id",addressId);
        map.put("company",company);
        map.put("telephone",phone);
        new WebServiceHandler().callAddToAddresstAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);
    }

    @Override
    public void OnSuccess(Object object) {
         addressPresentarInteractor.sendCountryResponse(object);
    }

    @Override
    public void OnFailure() {

    }


    @Override
    public void addressDeleteSuccess(Object object) {
        addressPresentarInteractor.sendAddressDeleteResponce(object);
    }

    @Override
    public void addressDeleteFailure() {

    }

    @Override
    public void addressSuccess(Object object) {
        addressPresentarInteractor.sendListResponse(object);
    }

    @Override
    public void addressFailure() {

    }

    @Override
    public void onAddAddressSuccess(Object object) {
         addressPresentarInteractor.sendValidationResponse(object);
    }

    @Override
    public void onAddAddressFailure() {

    }
}
