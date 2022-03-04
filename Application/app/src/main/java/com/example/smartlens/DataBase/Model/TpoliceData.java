package com.example.smartlens.DataBase.Model;

public class TpoliceData {
    int tp_id,ps_id;
    String name,password,birthdate,joindate,gender,branch,email,contact_no,address,city,state,photo;

    public TpoliceData(int tp_id, int ps_id, String name, String password, String birthdate, String joindate, String gender, String branch, String email, String contact_no, String address, String city, String state, String photo) {
        this.tp_id = tp_id;
        this.ps_id = ps_id;
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.joindate = joindate;
        this.gender = gender;
        this.branch = branch;
        this.email = email;
        this.contact_no = contact_no;
        this.address = address;
        this.city = city;
        this.state = state;
        this.photo = photo;
    }

    public TpoliceData(String name, String birthdate, String gender, String email, String contact_no, String address, String city, String state) {
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
        this.contact_no = contact_no;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public TpoliceData(int ps_id, String name, String password, String birthdate, String joindate, String gender, String branch, String email, String contact_no, String address, String city, String state, String photo) {
        this.ps_id = ps_id;
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.joindate = joindate;
        this.gender = gender;
        this.branch = branch;
        this.email = email;
        this.contact_no = contact_no;
        this.address = address;
        this.city = city;
        this.state = state;
        this.photo = photo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getTp_id() {
        return tp_id;
    }

    public void setTp_id(int tp_id) {
        this.tp_id = tp_id;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}