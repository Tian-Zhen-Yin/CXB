package com.example.administrator.bottomguide.Model;

public class Rank {

    private String nub;
    private int image_src;
    private String userId;
    private String userTime;

    public Rank( String nub,int image_src,String userId,String userTime) {
        this.nub=nub;
        this.image_src=image_src;
        this.userId=userId;
        this.userTime=userTime;

    }


    public String getNub() {
        return nub;
    }
    public int getImage_src() {
        return image_src;
    }
    public String getUserId() {
        return userId;
    }
    public String getUserTime(){
        return userTime;
    }
}
