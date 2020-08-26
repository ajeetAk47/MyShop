package com.codingprotocols.myshop.models;

public class CartItemModel {

    public static final int  CART_ITEM=0;
    public static final int  TOTAL_AMOUNT=0;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    // Cart Model
    private int productImage;
    private String productTitle;
    private String productPrice;
    private String oldPrice;
    private int quantity;

    public CartItemModel(int type, int productImage, String productTitle, String productPrice, String oldPrice, int quantity) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.oldPrice = oldPrice;
        this.quantity = quantity;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    // Final Amount Model
    private int totalItems;
    private String totalAmount;
    private String deliveryPrice;
    private String savedAmount;

    public CartItemModel(int type, int totalItems, String totalAmount, String deliveryPrice, String savedAmount) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalAmount = totalAmount;
        this.deliveryPrice = deliveryPrice;
        this.savedAmount = savedAmount;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }
}
