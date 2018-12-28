package com.example.administrator.bottomguide.Model;

public class Health {

    private int imageId;
    private String healthinfo;
    private String healthdata;

    public Health(int imageId, String info,String data) {
      this.imageId=imageId;
      this.healthinfo=info;
      this.healthdata=data;
    }

    public int getImageId() {
        return imageId;
    }

    public String getHealthinfo() {
        return healthinfo;
    }
    public String getHealthdata() {
        return healthdata;
    }

}
