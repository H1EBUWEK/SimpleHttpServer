package org.klimenko.clientserverapps.serverbackend.models;

import org.klimenko.clientserverapps.serverbackend.enums.ItemType;

public class ProductTrayWrapper {
    int id;
    int productID;
    int count;
    ItemType itemType;

    public ProductTrayWrapper(int id, int productID, int count, String itemType) {
        this.id = id;
        this.productID = productID;
        this.count = count;
        this.itemType = ItemType.valueOf(itemType);
    }

    public ProductTrayWrapper(int productID, int count, String itemType) {
        this.productID = productID;
        this.count = count;
        this.itemType = ItemType.valueOf(itemType);
    }

    public int getId() {
        return id;
    }

    public int getProductID() {
        return productID;
    }

    public int getCount() {
        return count;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setItemType(String itemType) {
        this.itemType = ItemType.valueOf(itemType);
    }

    public String getItemType() {
        return itemType.toString();
    }
}
