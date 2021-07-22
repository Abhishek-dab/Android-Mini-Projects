package com.example.retrocovid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    @GET("{user}")
    Call<List<Covid>> getRespos(@Path("user") String user);

}
