package com.example.smartlens.DataBase.Model;

public class CircularData {
    int c_id;
    String title,file,date;

    public CircularData(int c_id, String title, String file, String date) {
        this.c_id = c_id;
        this.title = title;
        this.file = file;
        this.date = date;
    }

    public CircularData(String title, String file, String date) {
        this.title = title;
        this.file = file;
        this.date = date;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
