package com.example.imageupload;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
    Context ct;
    List<User> list;
    public UsersAdapter(ViewDataActivity viewDataActivity, List<User> list) {
        ct = viewDataActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public UsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersAdapter.MyViewHolder holder, int position) {
        User user = list.get(position);

holder.tv1.setText(user.getName());
        holder.tv2.setText(user.getMobile());
        //Glide Dependency
        Glide.with(ct).load(user.getFilelocation()).into(holder.iv);
        holder.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = holder.tv2.getText().toString();
                Uri u = Uri.parse("tel:"+mobile);
                Intent i= new Intent(Intent.ACTION_DIAL,u);
                ct.startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;
        ImageView iv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.image);
            tv1 = itemView.findViewById(R.id.name);
            tv2 = itemView.findViewById(R.id.mobile);


        }
    }
}
