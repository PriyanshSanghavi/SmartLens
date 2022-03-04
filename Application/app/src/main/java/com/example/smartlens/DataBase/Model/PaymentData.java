package com.example.smartlens.DataBase.Model;

public class PaymentData {
    int p_id,m_id;
    String type,date,time;

    public PaymentData(int p_id, int m_id, String type, String date, String time) {
        this.p_id = p_id;
        this.m_id = m_id;
        this.type = type;
        this.date = date;
        this.time = time;
    }

    public PaymentData(int m_id, String type, String date, String time) {
        this.m_id = m_id;
        this.type = type;
        this.date = date;
        this.time = time;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
