package com.angik.collectiondemofragment;

public class ResItemDetailClass {

    private String itemDetail;
    private String itemName;
    private long itemPrice;
    private String itemImageUrl;

    public ResItemDetailClass() {
    }

    public ResItemDetailClass(String itemDetail, String itemName, long itemPrice, String itemImageUrl) {
        this.itemDetail = itemDetail;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemImageUrl = null;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return "" + itemPrice + " TK";
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(String itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }
}
