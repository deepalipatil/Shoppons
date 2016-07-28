package com.shopons.domain;

public class Deals {
    String id;
    String info;

    public Deals() {}

    public Deals(String id,String info)
    {
        this.info=info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
