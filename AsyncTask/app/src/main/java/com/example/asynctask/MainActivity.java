package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.userinput);
        recyclerView = findViewById(R.id.recycler);
    }

    public void search(View view) {
        String bookname = et.getText().toString();

        /*ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null){
            tv.setText("No Connection");
        }*/

        MyTask task = new MyTask(this,bookname,recyclerView);
        if(bookname.length()!=0) {
            task.execute();
        }else{
            Toast.makeText(this, "Pls Enter A Search Item", Toast.LENGTH_SHORT).show();
        }
    }
}