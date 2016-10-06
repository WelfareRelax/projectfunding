package com.example;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016-10-05.
 */
public class Project {

    public long id;
    public long projectID;
    public String title;
    public long User_ID;
    public String Body;
    public long Requested;
    public long Total_fund;


    public Project(long id, String title, long user_ID, String body, long requested, long total_fund) {
        this.id = id;
        this.title = title;
        this.User_ID = user_ID;
        this.Body = body;
        this.Requested =  requested;
        this.Total_fund = total_fund;
    }
    public Project(){}

    public long getId() {
        return id;
    }

    public long getProjectID() {
        return projectID;
    }

    public String getTitle() {
        return title;
    }

    public long getUser_ID() {
        return User_ID;
    }

    public String getBody() {
        return Body;
    }

    public long getRequested() {
        return Requested;
    }

    public long getTotal_fund() {
        return Total_fund;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser_ID(long user_ID) {
        User_ID = user_ID;
    }

    public void setBody(String body) {
        Body = body;
    }

    public void setRequested(long requested) {
        Requested = requested;
    }

    public void setTotal_fund(long total_fund) {
        Total_fund = total_fund;
    }
}
