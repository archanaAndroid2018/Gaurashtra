package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AfterSlider implements Serializable {
    @SerializedName("background")
    @Expose
    private Background background;
    @SerializedName("data")
    @Expose
    private List<Datum> data;

    public AfterSlider() {
        data = null;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
}
