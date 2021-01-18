package com.gaurashtra.app.model.bean.countrybean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryDatum {
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
