package com.example.administrator.bottomguide.Model;

class LocalUserDataModel {
    private int uid;
    private String userName;
    private String signName;
    private String userImg;
    private boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }


    public String toString() {
        return "uid :" + uid + " UserName:" + userName + " signName: " + signName + " UserImg: " + userImg;
    }

}