package com.ark.roomdb.RoomDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bio")
public class Bio {
    @PrimaryKey
    @ColumnInfo(name = "regNo")
    private int regNo;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "fname")
    private String fname;

    @ColumnInfo(name = "mobileNo")
    private long mobileNo;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "qualification")
    private String qualification;

    public int getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    public String getFname() {
        return fname;
    }

    public long getMobileNo() {
        return mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getQualification() {
        return qualification;
    }

    public void setRegNo(int regNo) {
        this.regNo = regNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setMobileNo(long mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
