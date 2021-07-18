package com.sensen.bms.bean;

public class User {
    private Integer uid;

    private String userName;

    private String userPwd;

    public User() {
    }

    public User(Integer uid, String userName, String userPwd) {
        this.uid = uid;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }
}