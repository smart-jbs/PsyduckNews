package com.sensen.bms.bean;

public class Admin {
    private Integer aid;

    private String adminName;

    private String adminPwd;

    public Admin() {
    }

    public Admin(Integer aid, String adminName, String adminPwd) {
        this.aid = aid;
        this.adminName = adminName;
        this.adminPwd = adminPwd;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd == null ? null : adminPwd.trim();
    }
}