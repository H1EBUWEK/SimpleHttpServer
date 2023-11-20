package org.klimenko.clientserverapps.serverbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.klimenko.clientserverapps.serverbackend.enums.ItemType;

import java.math.BigDecimal;

public class DefProd {

    protected BigDecimal price;
    protected int count;
    protected int id;

    @JsonIgnore
    protected ItemType itemType;

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setPrice(String price) {
        this.price = new BigDecimal(price);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public String getPrice() {
        return price.toString();
    }

}
