package com.example.eftkad0;

public class Father {

    private String key;
    private String nama;
    private String location;
    private String Street;
    private String numadress;
    private String Num;
    private String phone;


    public Father() {
    }

    public Father(String nama, String location, String Street, String numadress, String num, String phone) {
        this.nama = nama;
        this.location = location;
        this.Street = Street;
        this.numadress = numadress;
        Num = num;
        this.phone = phone;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getNumadress() {
        return numadress;
    }

    public void setNumadress(String numadress) {
        this.numadress = numadress;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
