package org.klimenko.clientserverapps.serverbackend.models;

import org.klimenko.clientserverapps.serverbackend.enums.ItemType;

import java.math.BigDecimal;

public class Product extends DefProd {
    String type;
    String info;
    String additionalInfo;

    public Product(int id, int count, String type, String info, String additionalInfo, String price, ItemType itemType) {
        this.id = id;
        this.count = count;
        this.type = type;
        this.info = info;
        this.price = new BigDecimal(price);
        this.additionalInfo = additionalInfo;
        this.itemType = ItemType.TypeProduct;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
