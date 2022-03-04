package com.example.smartlens.DataBase.Model;

public class MemoData {
    int m_id,tp_id,user_id,p_status;
    String vehical_no,title,amount,date,time,area,city,contact_no;

    public MemoData(int m_id, int tp_id, int user_id, int p_status, String vehical_no, String title, String amount, String date, String time, String area, String city) {
        this.m_id = m_id;
        this.tp_id = tp_id;
        this.user_id = user_id;
        this.p_status = p_status;
        this.vehical_no = vehical_no;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.area = area;
        this.city = city;
    }

    public MemoData(int tp_id, String vehical_no, String title, String amount, String area, String city, String contact_no) {
        this.tp_id = tp_id;
        this.vehical_no = vehical_no;
        this.title = title;
        this.amount = amount;
        this.area = area;
        this.city = city;
        this.contact_no = contact_no;
    }

    public MemoData(int tp_id, int user_id, int p_status, String vehical_no, String title, String amount, String date, String time, String area, String city) {
        this.tp_id = tp_id;
        this.user_id = user_id;
        this.p_status = p_status;
        this.vehical_no = vehical_no;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.area = area;
        this.city = city;
    }

    public MemoData(int m_id, int p_status, String title, String amount, String date) {
        this.m_id = m_id;
        this.p_status = p_status;
        this.title = title;
        this.amount = amount;
        this.date = date;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public int getP_status() {
        return p_status;
    }

    public void setP_status(int p_status) {
        this.p_status = p_status;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public int getTp_id() {
        return tp_id;
    }

    public void setTp_id(int tp_id) {
        this.tp_id = tp_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getVehical_no() {
        return vehical_no;
    }

    public void setVehical_no(String vehical_no) {
        this.vehical_no = vehical_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
