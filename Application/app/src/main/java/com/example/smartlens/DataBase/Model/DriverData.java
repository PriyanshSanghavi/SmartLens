package com.example.smartlens.DataBase.Model;

public class DriverData {
    int user_id;
    String name,password,birthdate,gender,email,contact_no,address,city,state,photo;

    public DriverData(String password) {
        this.password = password;
    }

    public DriverData(int  user_id, String name, String password, String birthdate, String gender, String email, String contact_no, String address, String city, String state) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
        this.contact_no = contact_no;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public DriverData(String name, String birthdate, String gender, String email, String contact_no, String address, String city, String state) {
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
        this.contact_no = contact_no;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public DriverData(String name, String password, String birthdate, String gender, String email, String contact_no, String address, String city, String state, String photo) {
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
        this.contact_no = contact_no;
        this.address = address;
        this.city = city;
        this.state = state;
        this.photo = photo;
    }

    public DriverData(String name, String password, String birthdate, String gender, String email, String contact_no, String address, String city, String state) {
        this.name = name;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
        this.contact_no = contact_no;
        this.address = address;
        this.city = city;
        this.state = state;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
}

