package com.example;

/**
 * Created by Administrator on 2016-10-05.
 */
public class Project {

    public long id;
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
        this.Requested = requested;
        this.Total_fund = total_fund;
    }
}
