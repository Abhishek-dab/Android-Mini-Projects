package com.example.retrocovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
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
    //List<Covid> rlist = new ArrayList<>();


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
        retrofit = new Retrofit.Builder().baseUrl("https://api.covid19api.com/dayone/country/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        GithubService service = retrofit.create(GithubService.class);
        String book = et.getText().toString();
        Call<List<Covid>> response = service.getRespos(book);
        response.enqueue(new Callback<List<Covid>>() {
            @Override
            public void onResponse(Call<List<Covid>> call, Response<List<Covid>> response) {
                List<Covid> list = response.body();
              //
               /* for (int i=0;i<list.size();i++){
                    rlist.add(list.get(i).getDate(), list.get(i).getRecovered(), list.get(i).getActive(), list.get(i).getDeath());
                }*/
                /*
                StringBuilder builder = new StringBuilder();
               for (int i=list.size();i<=0;i++){
                 //builder.append(list.get(i).getName()+"\n"+list.get(i).getFullName()+"\n\n");
                }*/

                rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rv.setAdapter(new CovidAdapter(MainActivity.this, list));
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<List<Covid>> call, Throwable t) {

            }
        });
    }

}



