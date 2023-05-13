package com.example.myapplication;

public class user {
    private String email,fullname,CIN ;

    public user(String email, String fullname, String CIN) {
        this.email = email;
        this.fullname = fullname;
        this.CIN = CIN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    @Override
    public String toString() {
        return "user{" +
                "email='" + email + '\'' +
                ", fullname='" + fullname + '\'' +
                ", CIN='" + CIN + '\'' +
                '}';
    }
}
