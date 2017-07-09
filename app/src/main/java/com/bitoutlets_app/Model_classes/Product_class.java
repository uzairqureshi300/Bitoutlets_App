package com.bitoutlets_app.Model_classes;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Product_class {
    private long id;
    private String product_id;
    private String title;
    private String price;
    private String shipping_cost;
    private String features;
    private String tags;
    private String current_stock;
    private String  no_of_views;
    private String discount;
    private String tax;
    private String description;
    private String image;
    private String unit;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShipping_cost() {
        return shipping_cost;
    }

    public void setShipping_cost(String shipping_cost) {
        this.shipping_cost = shipping_cost;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCurrent_stock() {
        return current_stock;
    }

    public void setCurrent_stock(String current_stock) {
        this.current_stock = current_stock;
    }

    public String getNo_of_views() {
        return no_of_views;
    }

    public void setNo_of_views(String no_of_views) {
        this.no_of_views = no_of_views;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
