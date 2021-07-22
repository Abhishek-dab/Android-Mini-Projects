package com.example.covid19india;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CovidAdapter extends RecyclerView.Adapter<CovidAdapter.MyViewHolder> {
    Context myct;
    List<Covid>myList;
    public CovidAdapter(Context ct, List<Covid> covidList) {
        this.myct = ct;
        this.myList = covidList;
    }

    @NonNull
    @Override
    public CovidAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(myct).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CovidAdapter.MyViewHolder holder, int position) {

        Covid c = myList.get(position);
        holder.tv1.setText(c.getDate());
        holder.tv2.setText(c.getActive());
        holder.tv3.setText(c.getRecovered());
        holder.tv4.setText(c.getDeath());




    }


    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3,tv4;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.date);
            tv2 = itemView.findViewById(R.id.active);
            tv3 = itemView.findViewById(R.id.recovered);
            tv4 = itemView.findViewById(R.id.death);

        }
    }
}
