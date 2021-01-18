package com.gaurashtra.app.model.bean.AddressDeleteBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.gaurashtra.app.model.bean.addresslistbean.UserAddressDatum;

public class Result {
    @SerializedName("userAddressData")
    @Expose
    private List<UserAddressDatum> userAddressData = null;

    public List<UserAddressDatum> getUserAddressData() {
        return userAddressData;
    }

    public void setUserAddressData(List<UserAddressDatum> userAddressData) {
        this.userAddressData = userAddressData;
    }

}
