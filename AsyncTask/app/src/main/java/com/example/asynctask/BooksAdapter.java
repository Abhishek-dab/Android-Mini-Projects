package com.example.asynctask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MyViewHolder> {

    Context myCt;
    List<Book> myList;
    public BooksAdapter(Context ct, List<Book> booksList) {
        myCt = ct;
        myList = booksList;
    }

    @NonNull
    @Override
    public BooksAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(myCt).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.MyViewHolder holder, final int position) {

        for(int i =0 ; i<myList.size(); i++) {
            holder.b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book b = myList.get(position);
                    Uri u = Uri.parse(b.getURL());
                    Intent i = new Intent(Intent.ACTION_VIEW,u);
                    myCt.startActivity(i);

                }
            });
        }


        Book b = myList.get(position);
        holder.tv_title.setText(b.getTitle());
        holder.tv_authors.setText(b.getAuthors());

        //Picaso or Glide these are two thirs party libraries to set the image url to the ImageView
        Glide.with(myCt).load(b.getImageLink()).into(holder.iv);


        holder.tv_price.setText(b.getPrice());

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_title, tv_authors,tv_price;
        Button b1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.bookimage);
            tv_title = itemView.findViewById(R.id.booktitle);
            tv_authors = itemView.findViewById(R.id.bookauthors);
            b1 = itemView.findViewById(R.id.url);

            tv_price=itemView.findViewById(R.id.price);
        }
    }
}