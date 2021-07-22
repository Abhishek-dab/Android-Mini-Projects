package com.example.roomlivedata;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MyRepository {
StudentDatabase studentDatabase;
LiveData<List<Student>> readData;

    public MyRepository(Application application) {

        studentDatabase = StudentDatabase.getDatabase(application);
        readData = studentDatabase.myDao().readData();

    }
    //this is insert method
    public void insert(Student student){
        new InsertTask().execute(student);
    }
    //this is read method

    public LiveData<List<Student>> readAllData(){
        return readData;
    }
    // This is Update Method
    public void update(Student student){
        new UpdateTask().execute(student);
    }
    // This is Delete Method
    public void delete(Student student){
        new DeleteTask().execute(student);
    }

   class InsertTask extends AsyncTask<Student, Void, Void>{

       @Override
       protected Void doInBackground(Student... students) {
           studentDatabase.myDao().insert(students[0]);
           return null;
       }
   }

    class UpdateTask extends AsyncTask<Student, Void, Void>{

        @Override
        protected Void doInBackground(Student... students) {
            studentDatabase.myDao().update(students[0]);
            return null;
        }
    }

    class DeleteTask extends AsyncTask<Student, Void, Void>{

        @Override
        protected Void doInBackground(Student... students) {
            studentDatabase.myDao().delete(students[0]);
            return null;
        }
    }

}
