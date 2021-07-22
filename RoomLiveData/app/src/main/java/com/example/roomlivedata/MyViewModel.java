package com.example.roomlivedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    MyRepository repository;
    LiveData<List<Student>> getAllData;
    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
        getAllData = repository.readAllData();
    }

    //This is for insert method
    public void insert(Student student){
        repository.insert(student);
    }
    //This is for Update method
    public void update(Student student){
        repository.update(student);
    }
    //This is for Delete method
    public void delete(Student student){
        repository.delete(student);
    }
    //for read data method
    public LiveData<List<Student>> readData(){
        return getAllData;
    }
}
