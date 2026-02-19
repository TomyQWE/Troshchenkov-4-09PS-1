package com.example.rzdproject;

import java.util.ArrayList;

public class Team {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Team(String name, String department,ArrayList<EmployeeItem> employees) {
        this.name = name;
        this.employees = employees;
        this.department = department;

    }

    public String name;
    public String department;
    public ArrayList<EmployeeItem> employees;
}
