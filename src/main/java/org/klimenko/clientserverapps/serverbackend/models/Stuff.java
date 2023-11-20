package org.klimenko.clientserverapps.serverbackend.models;

import java.math.BigDecimal;

public class Stuff extends DefProd {

    String name;
    String description;

    public Stuff(int id, String name, String description, String price, int count) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = new BigDecimal(price);
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

