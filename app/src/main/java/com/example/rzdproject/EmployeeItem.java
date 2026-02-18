package com.example.rzdproject;

public class EmployeeItem {
    public EmployeeItem(String id, String name, String brigade, String position, String phone, String email) {
        this.email = email;
        this.phone = phone;
        this.brigade = brigade;
        this.position = position;
        this.name = name;
        this.id = id;
    }

    public EmployeeItem(String name, String position, String phone, String email) {
        this.name = name;
        this.position = position;
        this.phone = phone;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrigade() {
        return brigade;
    }

    public void setBrigade(String brigade) {
        this.brigade = brigade;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String id;
    public String name;
    public String position;
    public String brigade;
    public String phone;
    public String email;
}
