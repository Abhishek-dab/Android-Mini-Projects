package com.example.covid19india;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class MyTask extends AsyncTask<Void, Void, String > {

    Context ct;
    RecyclerView myrv;
    ProgressDialog pd;
    String url ="https://api.covid19api.com/dayone/country/IN";
    NotificationManager nm;
    Calendar cal;
    SimpleDateFormat date;

    public MyTask(MainActivity mainActivity, RecyclerView rv, NotificationManager nm) {

        ct = mainActivity;
        myrv = rv;
        this.nm = nm;
    }


    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ct);
        pd.setTitle("Getting Data");
        pd.setMessage("Please Wait...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }

    @Override

    protected String doInBackground(Void... voids) {
        try {
            URL u = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();

        if(s==null){
            Toast.makeText(ct, "Your are offline", Toast.LENGTH_SHORT).show();
        }else {
            try {
                JSONArray rootjsonarray = new JSONArray(s);
                List<Covid> covidList = new ArrayList<>();
                cal= Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                date = new SimpleDateFormat("yyyy-mm-dd");
                String date2 = date.format(cal.getTime());
                JSONObject indexaObject = rootjsonarray.getJSONObject(rootjsonarray.length()-1);
                if(date2.equals((indexaObject.getString("Date").substring(0,10)))){



                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        //notification channel
                        NotificationChannel nc = new NotificationChannel("srm","NOTIFICATIONS",NotificationManager.IMPORTANCE_HIGH);
                        nm.createNotificationChannel(nc);

                    }
                    //notifications code
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(ct,"srm");
                    builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
                    builder.setContentTitle("Notification");
                    builder.setContentText(" Hey this is a notification");
                    builder.setAutoCancel(true);
                    nm.notify(42,builder.build());

                }

                for(int i=rootjsonarray.length()-1;i>=0;i--){
                    JSONObject indexObject = rootjsonarray.getJSONObject(i);

                    String date = indexObject.optString("Date");
                    String temp = "";

                    for(int j=0;j<date.length();j++){
                        if(date.charAt(j)=='T'){
                            break;
                        }else{
                            temp = temp + date.charAt(j);
                        }
                    }
                    String active = indexObject.optString("Active");
                    String recovered = indexObject.optString("Recovered");
                    String deaths = indexObject.optString("Deaths");

                    Covid c = new Covid(temp,active,recovered,deaths);
                    covidList.add(c);
                }

                myrv.setLayoutManager(new LinearLayoutManager(ct));
                myrv.setAdapter(new CovidAdapter(ct, covidList));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
