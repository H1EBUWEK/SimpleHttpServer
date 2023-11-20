package org.klimenko.clientserverapps.serverbackend.sdfsdf;

public class DesignComponentWrapper {
    int id;
    String url;
    String description;

    public void setId(int id){
        this.id = id;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getUrl(){
        return url;
    }

    public String getDescription(){
        return description;
    }

    public DesignComponentWrapper(int id, String url, String description){
        this.id = id;
        this.url = url;
        this.description = description;
    }

}
