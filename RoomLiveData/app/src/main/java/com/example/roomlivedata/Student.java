package com.example.roomlivedata;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "StudentData")
public class Student {


    String Name;
    String MailId;
    String PhoneNumber;
    String address;
    String dept;
    @PrimaryKey
    @NonNull
    String gender;
    String lang;




    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMailId() {
        return MailId;
    }

    public void setMailId(String mailId) {
        MailId = mailId;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getaddr() {
        return address;
    }

    public void setaddr(String address) {
        this.address = address;
    }
    public String getDept() {
        return dept;
    }

    public void setdept(String dept) {
        this.dept = dept;
    }
    public String getGender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }
    public String getLang() {
        return lang;
    }

    public void setlanguage(String lang) {
        this.lang = lang;
    }

}
