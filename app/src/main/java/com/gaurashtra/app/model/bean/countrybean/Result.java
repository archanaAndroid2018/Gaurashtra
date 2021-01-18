package com.gaurashtra.app.model.bean.countrybean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("countryData")
    @Expose
    private List<CountryDatum> countryData = null;

    public List<CountryDatum> getCountryData() {
        return countryData;
    }

    public void setCountryData(List<CountryDatum> countryData) {
        this.countryData = countryData;
    }

}
