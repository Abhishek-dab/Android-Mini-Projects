package com.example.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MyTask extends AsyncTask<Void,Void,String> {

    String url ="https://www.googleapis.com/books/v1/volumes?q=";
    String myUrl;
    Context ct;
    ProgressDialog pd;
    RecyclerView myrv;

    public MyTask(MainActivity mainActivity, String bookname, RecyclerView rv) {
        ct =mainActivity;
        myUrl = url+bookname;
        myrv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(ct);
        pd.setMessage("Please Wait...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {
            URL u = new URL(myUrl);
            HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while((line = reader.readLine())!= null){
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
        List<Book> bookList = new ArrayList<>();
        if(s==null){
            Toast.makeText(ct, "Your Offline", Toast.LENGTH_SHORT).show();

        }else{
        try {
            JSONObject rootJsonObject = new JSONObject(s);
            JSONArray itemsJsonArray =rootJsonObject.getJSONArray("items");
            for(int i =0; i<itemsJsonArray.length(); i++) {
                JSONObject indexObject = itemsJsonArray.getJSONObject(i);
                JSONObject volumeInfoObject = indexObject.getJSONObject("volumeInfo");
                String title = volumeInfoObject.optString("title");
                String stitle = volumeInfoObject.optString("infoLink");
                JSONArray myArray = volumeInfoObject.getJSONArray("authors");
               String authors = myArray.get(0).toString();



                String imageobject = volumeInfoObject.optString("imageLinks");
                boolean isphoto = imageobject.isEmpty();
                String thumbnail = null;
                if (isphoto != true) {
                    JSONObject ima = volumeInfoObject.getJSONObject("imageLinks");
                    thumbnail = ima.getString("thumbnail");
                }

                JSONObject saleInfoObject = indexObject.getJSONObject("saleInfo");
                String retailpriceobject = saleInfoObject.getString("saleability");
                boolean issold = retailpriceobject.equals("FOR_SALE");
                String price = null;
                if (issold) {
                    JSONObject priceinfo = saleInfoObject.getJSONObject("retailPrice");
                    price = priceinfo.getString("amount");
                }






                if (title != null && authors != null) {
                    Book b = new Book(title, authors, thumbnail, stitle, price);
                    bookList.add(b);
                }

            }    // JSONObject searchInfoObject = zeroIndexObject.getJSONObject("searchInfo");
                //String info = searchInfoObject.optString("textSnippet");
           // Log.i("DATA",price);
           // Log.i("DATA",authors);*/
              //  myTextView.setText("Book Title: " + title + "\n" + "Author: " + authors + "\n");



            //Log.i("SIZE", ""+bookList.size());
            myrv.setLayoutManager(new LinearLayoutManager(ct));
            myrv.setAdapter(new BooksAdapter(ct,bookList));

            //Toast.makeText(ct, ""+title+"\n"+authors, Toast.LENGTH_SHORT).show();
            //Log.i("DATA",title);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }

    }
}
