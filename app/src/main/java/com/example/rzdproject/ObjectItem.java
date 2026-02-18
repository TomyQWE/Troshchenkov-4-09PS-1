package com.example.rzdproject;

public class ObjectItem {
    public String id;
    public String title;
    public String type;
    public String address;
    public String brigade;

    public ObjectItem(String id, String title, String type, String address, String brigade) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.address = address;
        this.brigade = brigade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrigade() {
        return brigade;
    }

    public void setBrigade(String brigade) {
        this.brigade = brigade;
    }
}

