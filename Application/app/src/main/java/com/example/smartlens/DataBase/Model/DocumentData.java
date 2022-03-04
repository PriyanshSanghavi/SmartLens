package com.example.smartlens.DataBase.Model;

public class DocumentData {
    int doc_id,user_id;
    String title,file,exp_date,verification;

    public DocumentData(int doc_id, int user_id, String title, String file, String exp_date, String verification) {
        this.doc_id = doc_id;
        this.user_id = user_id;
        this.title = title;
        this.file = file;
        this.exp_date = exp_date;
        this.verification = verification;
    }

    public DocumentData(int user_id, String title, String file, String exp_date, String verification) {
        this.user_id = user_id;
        this.title = title;
        this.file = file;
        this.exp_date = exp_date;
        this.verification = verification;
    }

    public int getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(int doc_id) {
        this.doc_id = doc_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}
