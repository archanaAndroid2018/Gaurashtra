package com.gaurashtra.app.model.bean.addresslistbean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
