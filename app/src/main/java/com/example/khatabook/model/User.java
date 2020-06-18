package com.example.khatabook.model;


import com.example.khatabook.utils.Constants;

public class User {

    String email,password,number,fId;
    Constants.AuthType authType;
    Constants.Auth auth;

    public Constants.AuthType getAuthType() {
        return authType;
    }

    public Constants.Auth getAuth() {
        return auth;
    }

    public void setAuthType(Constants.AuthType authType) {
        this.authType = authType;
    }

    public void setAuth(Constants.Auth auth) {
        this.auth = auth;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", number='" + number + '\'' +
                ", fId='" + fId + '\'' +
                ", authType='" + authType + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }


}