package com.example.covid19india;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.os.Bundle;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity {
RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recycler);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        MyTask task = new MyTask(this,rv,nm);
        task.execute();
    }

}