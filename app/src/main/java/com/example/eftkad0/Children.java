package com.example.eftkad0;

public class Children {

    private String key;
    private String fullname;
    private String who;
    private String IdNumber;
    private String birthday;
    private String status;
    private String qualification;
    private String work;
    private String phone;
    private String dateofentry;


    public Children() {
    }

    public Children(String fullname, String who, String idNumber, String birthday, String status, String qualification, String work, String phone, String dateofentry) {
        this.fullname = fullname;
        this.who = who;
        IdNumber = idNumber;
        this.birthday = birthday;
        this.status = status;
        this.qualification = qualification;
        this.work = work;
        this.phone = phone;
        this.dateofentry = dateofentry;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }


    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String idNumber) {
        IdNumber = idNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getDateofentry() {
        return dateofentry;
    }

    public void setDateofentry(String dateofentry) {
        this.dateofentry = dateofentry;
    }
}
