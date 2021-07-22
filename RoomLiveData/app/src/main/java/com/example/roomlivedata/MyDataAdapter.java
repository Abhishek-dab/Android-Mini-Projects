package com.example.roomlivedata;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class MyDataAdapter extends RecyclerView.Adapter<MyDataAdapter.MyDataViewHolder> {
    Context ct;
    List<Student> list;

    public MyDataAdapter(MainActivity mainActivity, List<Student> studentList) {

 ct=mainActivity;
 list=studentList;

    }

    @NonNull
    @Override
    public MyDataAdapter.MyDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ct).inflate(R.layout.row_design, parent, false);
        return new MyDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDataAdapter.MyDataViewHolder holder, int position) {

        final Student student = list.get(position);

        holder.rname.setText(student.getName());
        holder.rmail.setText(student.getMailId());
        holder.rmobile.setText(student.getPhoneNumber());
        holder.raddr.setText(student.getaddr());
        holder.rdept.setText(student.getDept());
        holder.rgender.setText(student.getGender());
        holder.rlang.setText(student.getLang());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity.database.myDao().delete(student);
                MainActivity.viewModel.delete(student);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*public Student getWordAtPos(int position){
        return list.get(position);
    }*/
    public class MyDataViewHolder extends RecyclerView.ViewHolder {
        TextView rname, rmail, rmobile,raddr,rdept,rgender, rlang;
        ImageView edit, delete;
        public MyDataViewHolder(@NonNull View itemView) {
            super(itemView);
            rname = itemView.findViewById(R.id.readname);
            rmail = itemView.findViewById(R.id.readmailid);
            rmobile = itemView.findViewById(R.id.readmobile);

            raddr = itemView.findViewById(R.id.readaddr);
            rdept = itemView.findViewById(R.id.readdept);
            rgender = itemView.findViewById(R.id.readgender);
            rlang = itemView.findViewById(R.id.readlang);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //My God is an awesone god he lives in the heavens above.
                    /*Access data frm textviews*/
                    final String name = rname.getText().toString();
                    final String mail = rmail.getText().toString();
                    final String mobile = rmobile.getText().toString();
                    final String dept = rdept.getText().toString();
                    final String address = raddr.getText().toString();

                    final String gender = rgender.getText().toString();


                    ViewGroup viewGroup = view.findViewById(android.R.id.content);
                    View v = LayoutInflater.from(ct).inflate(R.layout.updatedata, viewGroup, false);
                    final EditText uname = v.findViewById(R.id.updatename);
                    final EditText umail = v.findViewById(R.id.updatemailid);
                    final EditText umobile = v.findViewById(R.id.updatemobilenumber);

                    final EditText uaddr = v.findViewById(R.id.updateaddr);
                    final Spinner udept = v.findViewById(R.id.updatedept);
                    /*final RadioButton umale = v.findViewById(R.id.updatemale);
                    final RadioButton ufemale = v.findViewById(R.id.updatefemale);*/
                    final CheckBox ueng = v.findViewById(R.id.updateenglish);
                    final CheckBox uhind = v.findViewById(R.id.updatehindi);
                    final CheckBox utelgu = v.findViewById(R.id.updatetelugu);


                    Button update = v.findViewById(R.id.updatedata);
                    Button cancel = v.findViewById(R.id.canceldata);

                    final BottomSheetDialog dialog = new BottomSheetDialog(ct);
                    dialog.setContentView(v);
                    dialog.setCancelable(false);

                    ArrayAdapter<CharSequence> department = ArrayAdapter.createFromResource(ct, R.array.branch, android.R.layout.simple_spinner_item);
                    department.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    udept.setAdapter(department);
                    uname.setText(name);
                    umail.setText(mail);
                    umobile.setText(mobile);
                    uaddr.setText(address);



                    /*if(gender.equals("Male")){
                        umale.setChecked(true);
                    }else{
                        ufemale.setChecked(true);
                    }*/
                    if (dept.equals("English")) {
                        ueng.setChecked(true);
                    }


                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Student student = new Student();
                            if (uname== null) {
                                Toast.makeText(ct, "Name is Empty", Toast.LENGTH_SHORT).show();
                            } else if (umail==null) {
                                Toast.makeText(ct, "Please Enter mail", Toast.LENGTH_SHORT).show();
                            } else if (umobile== null) {
                                Toast.makeText(ct, "Please Enter Phone Number of 10 Digits", Toast.LENGTH_SHORT).show();
                            } else if (uaddr== null) {
                                Toast.makeText(ct, "Please Enter Address", Toast.LENGTH_SHORT).show();
                            }  else {

                                student.setMailId(umail.getText().toString());
                                student.setName(uname.getText().toString());
                                student.setPhoneNumber(umobile.getText().toString());
                                student.setaddr(uaddr.getText().toString());

                           /* if(umale.isChecked()){
                                String gender = umale.getText().toString();
                            }
                            if(ufemale.isChecked()){
                              String gender = ufemale.getText().toString();
                            }*/

                                StringBuilder builder = new StringBuilder();

                                if (ueng.isChecked()) {
                                    builder.append(ueng.getText().toString() + " ");
                                }
                                if (uhind.isChecked()) {
                                    builder.append(uhind.getText().toString() + " ");
                                }
                                if (utelgu.isChecked()) {
                                    builder.append(utelgu.getText().toString());
                                }


                                String dep = udept.getSelectedItem().toString();

                                student.setgender(gender);
                                student.setlanguage(builder.toString());
                                student.setdept(dep);
                                MainActivity.viewModel.update(student);
                                Toast.makeText(ct, "Data Updated Sucessfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }
            });

        }
    }
}
