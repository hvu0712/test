package com.example.duan1;

public class UserHelperClass {
    String user, pass, number, email;

    public UserHelperClass() {
    }

    public UserHelperClass(String user, String pass, String email) {
        this.user = user;
        this.pass = pass;
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
