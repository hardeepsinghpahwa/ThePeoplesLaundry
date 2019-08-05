package com.example.hardeep.myproject;

public class d {
    String userid;
    String totalamount;

    public d(String userid, String totalamount, String status) {
        this.userid = userid;
        this.totalamount = totalamount;
        this.status = status;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;

    public d() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public d(String userid) {
        this.userid = userid;
    }
}
