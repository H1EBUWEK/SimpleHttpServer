package org.klimenko.clientserverapps.serverbackend.models;

public class Response {
    int statusType;
    Object body;

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public int getStatusType() {
        return statusType;
    }

    public Object getBody() {
        return body;
    }

    public Response(int statusType, Object body) {
        this.statusType = statusType;
        this.body = body;
    }
}
