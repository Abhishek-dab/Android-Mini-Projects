package com.example.roomlivedata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;

public class InsertActivity extends AppCompatActivity {
EditText address,names,mailid,phone;
RadioButton male,female;
Spinner dept;
CheckBox eng,hin,tel;
String gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        names=findViewById(R.id.name);
        mailid = findViewById(R.id.mailid);
        phone = findViewById(R.id.phonenumber);
        address=findViewById(R.id.address);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        dept=findViewById(R.id.dept);
        eng=findViewById(R.id.english);
        hin=findViewById(R.id.hindi);
        tel=findViewById(R.id.telugu);

        ArrayAdapter<CharSequence> department = ArrayAdapter.createFromResource(this, R.array.branch, android.R.layout.simple_spinner_item);
        department.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(department);

        dept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dept.setSelection(i);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void save(View view) {

        String name = names.getText().toString();
        String mail = mailid.getText().toString();
        String mobile = phone.getText().toString();
        String add = address.getText().toString();

        if(male.isChecked()){
            gender = male.getText().toString();
        }
        if(female.isChecked()){
            gender = female.getText().toString();
        }

        StringBuilder builder = new StringBuilder();

        if(eng.isChecked()){
            builder.append(eng.getText().toString()+" ");
        }
        if(hin.isChecked()){
            builder.append(hin.getText().toString()+" ");
        }
        if(tel.isChecked()){
            builder.append(tel.getText().toString());
        }

       String department = dept.getSelectedItem().toString();

        Student student = new Student();
if(name.isEmpty()){
    Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
 }else if(mail.isEmpty()) {
        Toast.makeText(this, "Please Enter mail", Toast.LENGTH_SHORT).show();
    }else if (mobile.length()<10) {
        Toast.makeText(this, "Please Enter Phone Number of 10 Digits", Toast.LENGTH_SHORT).show();
    }else if(add.isEmpty()){
        Toast.makeText(this, "Please Enter Address", Toast.LENGTH_SHORT).show();
    }else if(gender==null) {
    Toast.makeText(this, "Choose a gender", Toast.LENGTH_SHORT).show();
}

else {
    student.setName(name);
    student.setMailId(mail);
    student.setPhoneNumber(mobile);
    student.setaddr(add);
    student.setdept(department);
    student.setgender(gender);
    student.setlanguage(builder.toString());

    //MainActivity.database.myDao().insert(student);
    MainActivity.viewModel.insert(student);

    Toast.makeText(this, "Data Saved Sucessfully", Toast.LENGTH_SHORT).show();

    finish();


}


    }
}