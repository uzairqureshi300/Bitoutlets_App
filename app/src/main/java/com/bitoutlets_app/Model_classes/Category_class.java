package com.bitoutlets_app.Model_classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by uzair on 06/06/2017.
 */

public class Category_class {
    private String name;
    private String image;
    private String id;
    @SerializedName("sub")
    private List<SubCategory_Class> sub_category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SubCategory_Class> getSub_category() {
        return sub_category;
    }

    public void setSub_category(List<SubCategory_Class> sub_category) {
        this.sub_category = sub_category;
    }
}
