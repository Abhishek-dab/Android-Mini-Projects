package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

public class Pojo {
    private String name;
    @SerializedName("full_name")
    private String fullName;




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
