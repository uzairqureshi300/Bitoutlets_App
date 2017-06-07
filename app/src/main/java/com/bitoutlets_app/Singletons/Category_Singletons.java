package com.bitoutlets_app.Singletons;

import com.bitoutlets_app.Activities.Categories;
import com.bitoutlets_app.Model_classes.Category_class;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by hassan on 11/16/16.
 */
public class Category_Singletons {
    private static Category_Singletons uniqueInstance = null;
    private List<Category_class> partsListDataList = null;

    private Category_Singletons() {
    }

    public static Category_Singletons getInstance() {
        if(uniqueInstance == null) {
            synchronized (Category_Singletons.class) {
                uniqueInstance = new Category_Singletons();
            }
        }
        return uniqueInstance;
    }

    public List<Category_class> getCategory_list() {
        return partsListDataList;
    }

    public void setCategory_list() {
        this.partsListDataList = new ArrayList<>();
    }

    public void addPart(Category_class data) {
        partsListDataList.add(data);
    }
}
