package com.example.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

RecyclerView rv;
Retrofit retrofit;
ProgressDialog pd;
EditText et;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        pd= new ProgressDialog(this);
        pd.setMessage("Please Wait..");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        et = findViewById(R.id.get);
        rv=findViewById(R.id.recycler);


    }

    public void getresponse(View view) {
        pd.show();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        GithubService service = retrofit.create(GithubService.class);
        String book = et.getText().toString();
        Call<List<Pojo>> response = service.getRespos(book);
        response.enqueue(new Callback<List<Pojo>>() {
            @Override
            public void onResponse(Call<List<Pojo>> call, Response<List<Pojo>> response) {
                List<Pojo> list = response.body();/*
                StringBuilder builder = new StringBuilder();
               for (int i=0;i<list.size();i++){
                 builder.append(list.get(i).getName()+"\n"+list.get(i).getFullName()+"\n\n");
                }*/

                rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rv.setAdapter(new Adapter(MainActivity.this, list));
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<List<Pojo>> call, Throwable t) {

            }
        });
    }
}