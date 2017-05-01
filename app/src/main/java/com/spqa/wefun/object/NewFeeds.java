package com.spqa.wefun.object;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MyPC on 25/04/2017.
 */

public class NewFeeds {
    private  int ID;
    private int TypeFeed;
    private String Link;
    private String UserID;
    private String Content;
    private int Likes;
    private int Comments;
    private int Status;

    private Date Created_at;

    public int getID() {
        return ID;
    }

    public int getTypeFeed() {
        return TypeFeed;
    }

    public String getLink() {
        return Link;
    }

    public String getUserID() {
        return UserID;
    }

    public String getContent() {
        return Content;
    }

    public int getLikes() {
        return Likes;
    }

    public int getComments() {
        return Comments;
    }

    public int getStatus() {
        return Status;
    }

    public Date getCreated_at() {
        return Created_at;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTypeFeed(int typeFeed) {
        TypeFeed = typeFeed;
    }

    public void setLink(String link) {
        Link = link;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setContent(String content) {
        Content = content;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public void setComments(int comments) {
        Comments = comments;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setCreated_at(Date created_at) {
        Created_at = created_at;
    }
}

