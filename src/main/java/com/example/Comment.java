package com.example;

/**
 * Created by Administrator on 2016-10-05.
 */
import java.time.LocalDateTime;

public class Comment {

    public final long id;
    public final long blogId;
    public final long pledged;
    public final String title;
    public final String message;
    public final String name;
    public final String body;
    public final LocalDateTime Date;


    public Comment(long id, long blogId, long pledged, String title, String message, String name, String body, LocalDateTime date) {
        this.id = id;
        this.blogId = blogId;
        this.pledged = pledged;
        this.title = title;
        this.message = message;
        this.name = name;
        this.body = body;
        Date = date;
    }
}
