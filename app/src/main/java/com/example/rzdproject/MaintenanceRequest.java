package com.example.rzdproject;

import java.io.Serializable;

public class MaintenanceRequest implements Serializable {


    public MaintenanceRequest(String id, String objectName, String workType, String team, String date) {
        this.id = id;
        this.isExpanded = isExpanded;
        this.date = date;
        this.workType = workType;
        this.team = team;
        this.objectName = objectName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    private String id;
    private String objectName;
    private String team;
    private String workType;
    private String date;
    private boolean isExpanded;

    public String getAllSearchText() {
        return objectName + " " + team + " " + workType + " " + date;
    }

    // Метод для отображения в ListView
    @Override
    public String toString() {
        return objectName + "\nБригада: " + team;
    }
}
