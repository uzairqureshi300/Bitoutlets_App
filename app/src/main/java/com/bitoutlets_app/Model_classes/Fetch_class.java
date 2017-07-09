package com.bitoutlets_app.Model_classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by uzair on 06/06/2017.
 */

public class Fetch_class {
    private String id;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
