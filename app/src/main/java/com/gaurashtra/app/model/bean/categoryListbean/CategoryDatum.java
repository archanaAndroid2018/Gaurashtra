package com.gaurashtra.app.model.bean.categoryListbean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryDatum {
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("cat_image")
    @Expose
    private String catImage;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

//    @SerializedName("category_id")
//    @Expose
//    private String categoryId;
//    @SerializedName("cat_image")
//    @Expose
//    private String catImage;
//    @SerializedName("parent_id")
//    @Expose
//    private String parentId;
//@SerializedName("name")
//@Expose
