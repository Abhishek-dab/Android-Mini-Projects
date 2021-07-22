package com.example.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

     @GET("users/{user}/repos")
    Call<List<Pojo>> getRespos(@Path("user") String user);

}
