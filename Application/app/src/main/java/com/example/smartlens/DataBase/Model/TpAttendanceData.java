package com.example.smartlens.DataBase.Model;

public class TpAttendanceData {
    int a_id,tp_id;
    String date,in_time,out_time;

    public TpAttendanceData(int a_id, int tp_id, String date, String in_time, String out_time) {
        this.a_id = a_id;
        this.tp_id = tp_id;
        this.date = date;
        this.in_time = in_time;
        this.out_time = out_time;
    }

    public TpAttendanceData(int tp_id, String date, String in_time, String out_time) {
        this.tp_id = tp_id;
        this.date = date;
        this.in_time = in_time;
        this.out_time = out_time;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }

    public int getTp_id() {
        return tp_id;
    }

    public void setTp_id(int tp_id) {
        this.tp_id = tp_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }
}
