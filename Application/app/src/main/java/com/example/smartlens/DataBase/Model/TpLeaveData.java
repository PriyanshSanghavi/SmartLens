package com.example.smartlens.DataBase.Model;

public class TpLeaveData {
    int l_id,tp_id;
    String type,reason,from_date,to_date,day_type,permission;

    public TpLeaveData(int l_id, int tp_id, String type, String reason, String from_date, String to_date, String day_type, String permission) {
        this.l_id = l_id;
        this.tp_id = tp_id;
        this.type = type;
        this.reason = reason;
        this.from_date = from_date;
        this.to_date = to_date;
        this.day_type = day_type;
        this.permission = permission;
    }

    public TpLeaveData(int tp_id, String type, String reason, String from_date, String to_date, String day_type) {
        this.tp_id = tp_id;
        this.type = type;
        this.reason = reason;
        this.from_date = from_date;
        this.to_date = to_date;
        this.day_type = day_type;
    }

    public TpLeaveData(int l_id, String type, String from_date, String to_date, String permission) {
        this.l_id = l_id;
        this.type = type;
        this.from_date = from_date;
        this.to_date = to_date;
        this.permission = permission;
    }

    public TpLeaveData(int tp_id, String type, String reason, String from_date, String to_date, String day_type, String permission) {
        this.tp_id = tp_id;
        this.type = type;
        this.reason = reason;
        this.from_date = from_date;
        this.to_date = to_date;
        this.day_type = day_type;
        this.permission = permission;
    }

    public int getL_id() {
        return l_id;
    }

    public void setL_id(int l_id) {
        this.l_id = l_id;
    }

    public int getTp_id() {
        return tp_id;
    }

    public void setTp_id(int tp_id) {
        this.tp_id = tp_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getDay_type() {
        return day_type;
    }

    public void setDay_type(String day_type) {
        this.day_type = day_type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
