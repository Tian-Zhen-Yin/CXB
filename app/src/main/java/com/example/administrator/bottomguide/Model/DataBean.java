package com.example.administrator.bottomguide.Model;

import java.util.ArrayList;
import java.util.List;

//数据类
public class DataBean {
     public List<Integer> heart_data;//心率
     private List<Float> bp_data;//血压
     private List<Float> bsuger_data;//血脂
     private List<Float> heat_data;//体温

     public void setHeart_data(List<Integer> heart_data)
     {

         this.heart_data=heart_data;

     }

     public List<Integer> getHeart_data() {

          return heart_data;
     }

     public void setBp_data(List<Float> bp_data) {
          this.bp_data = bp_data;
     }

     public List<Float> getBp_data() {
          return bp_data;
     }

     public void setBsuger_data(List<Float> bsuger_data) {
          this.bsuger_data = bsuger_data;
     }

     public List<Float> getBsuger_data() {
          return bsuger_data;
     }

     public void setHeat_data(List<Float> heat_data) {
          this.heat_data = heat_data;
     }

     public List<Float> getHeat_data() {
          return heat_data;
     }
}
