package org.klimenko.clientserverapps.serverbackend.models;

import org.joda.time.DateTime;


public class Token {

    private int userId;
    private String name;
    private DateTime issued;
    private DateTime expires;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIssued(DateTime issued) {
        this.issued = issued;
    }

    public void setExpires(DateTime expires) {
        this.expires = expires;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public DateTime getIssued() {
        return issued;
    }

    public DateTime getExpires() {
        return expires;
    }
}
