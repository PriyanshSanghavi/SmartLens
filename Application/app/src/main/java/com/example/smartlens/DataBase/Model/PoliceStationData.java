package com.example.smartlens.DataBase.Model;

public class PoliceStationData {
    int ps_id;
    String name,password,address,city,State,email,contact_no;

    public PoliceStationData(int ps_id, String name, String password, String address, String city, String state, String email, String contact_no) {
        this.ps_id = ps_id;
        this.name = name;
        this.password = password;
        this.address = address;
        this.city = city;
        State = state;
        this.email = email;
        this.contact_no = contact_no;
    }

    public PoliceStationData(String name, String password, String address, String city, String state, String email, String contact_no) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.city = city;
        State = state;
        this.email = email;
        this.contact_no = contact_no;
    }

    public int getPs_id() {
        return ps_id;
    }

    public void setPs_id(int ps_id) {
        this.ps_id = ps_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }
}
