package com.example.smartlens.DataBase.Model;

public class RatingData {
    int r_id,star,user_id;
    String date,msg;

    public RatingData(int r_id, int user_id, int star, String date, String msg) {
        this.r_id = r_id;
        this.user_id = user_id;
        this.star = star;
        this.date = date;
        this.msg = msg;
    }

    public RatingData(int user_id, int star, String msg) {
        this.user_id = user_id;
        this.star = star;
        this.msg = msg;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
