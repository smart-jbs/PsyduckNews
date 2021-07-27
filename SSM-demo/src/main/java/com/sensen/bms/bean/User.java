package com.sensen.bms.bean;

import javax.validation.constraints.Pattern;

public class User {
    private Integer uid;

    @Pattern(regexp = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})",message = "用户名必须是6-16位数字和字母的组合或者2-5位中文")
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