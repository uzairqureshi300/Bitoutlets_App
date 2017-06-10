package com.bitoutlets_app.Singletons;

import com.bitoutlets_app.Model_classes.Category_class;
import com.bitoutlets_app.Model_classes.Product_class;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hassan on 11/16/16.
 */
public class Product_Singletons {
    private static Product_Singletons uniqueInstance = null;
    private List<Product_class> partsListDataList = null;

    private Product_Singletons() {
    }

    public static Product_Singletons getInstance() {
        if(uniqueInstance == null) {
            synchronized (Product_Singletons.class) {
                uniqueInstance = new Product_Singletons();
            }
        }
        return uniqueInstance;
    }

    public List<Product_class> getProduct_list() {
        return partsListDataList;
    }

    public void setProduct_list() {
        this.partsListDataList = new ArrayList<>();
    }

    public void addPart(Product_class data) {
        partsListDataList.add(data);
    }
}
