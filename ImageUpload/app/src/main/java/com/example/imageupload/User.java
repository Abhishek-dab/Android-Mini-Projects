package com.example.imageupload;

public class User {
    private String name, mobile, filelocation;

    public User(String name, String mobile, String filelocation) {
        this.name = name;
        this.mobile = mobile;
        this.filelocation = filelocation;
    }
public User(){

}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFilelocation() {
        return filelocation;
    }

    public void setFilelocation(String filelocation) {
        this.filelocation = filelocation;
    }
}
