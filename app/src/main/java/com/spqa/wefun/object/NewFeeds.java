package com.spqa.wefun.object;


import java.util.Date;

/**
 * Created by MyPC on 25/04/2017.
 */

public class NewFeeds {
    private  int ID;
    private int TypePost;
    private String Link;
    private String UserID;
    private String Caption;
    private int Likes;
    private int Comments;
    private int Status;

    private Date Created_at;

    public int getID() {
        return ID;
    }

    public int getTypePost() {
        return TypePost;
    }

    public String getLink() {
        return Link;
    }

    public String getUserID() {
        return UserID;
    }

    public String getCaption() {
        return Caption;
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

    public void setTypePost(int typePost) {
        TypePost = typePost;
    }

    public void setLink(String link) {
        Link = link;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setCaption(String caption) {
        Caption = caption;
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

