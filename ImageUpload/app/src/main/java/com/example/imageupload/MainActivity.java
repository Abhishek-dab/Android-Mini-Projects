package com.example.imageupload;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText et_name,et_mobile;
Button b_camera,b_gallery,b_save,b_view;
ImageView iv;
public static final int CAMERA_REQUEST=22;
    public static final int GALLERY_REQUEST=33;
    FirebaseStorage storage;
    StorageReference storageReference;
FirebaseDatabase database;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.name);
        et_mobile = findViewById(R.id.mobile);
        iv = findViewById(R.id.image);
        b_camera = findViewById(R.id.camerabutton);
        b_gallery = findViewById(R.id.gallerybutton);
        b_save = findViewById(R.id.savebutton);
        b_view = findViewById(R.id.viewdatabutton);
        b_camera.setOnClickListener(this);
        b_gallery.setOnClickListener(this);
        b_save.setOnClickListener(this);
        b_view.setOnClickListener(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.camerabutton:
             openCamera();
             break;
         case R.id.gallerybutton:
             openGallery();
             break;
         case R.id.savebutton:
             saveData();
             break;
         case R.id.viewdatabutton:
             viewData();
             break;
     }
    }

    private void viewData() {
Intent i = new Intent(this,ViewDataActivity.class);
startActivity(i);


    }

    private void saveData() {

        final String name = et_name.getText().toString();
        final String mobile = et_mobile.getText().toString();
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String fileLocation = uri.toString();
                User u = new User(name, mobile, fileLocation);
                databaseReference.child("Users").push().setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Details Saved Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void openGallery() {
        Intent i  =new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,GALLERY_REQUEST);
    }

    private void openCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //acess the camera img
        if(requestCode==CAMERA_REQUEST){
            if(resultCode==RESULT_OK){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                Uri u = getImageURI(this,bitmap);
                iv.setImageURI(u);
                uploadImage(u);

            }//access galery image
        }else if(requestCode==GALLERY_REQUEST){
            if(resultCode==RESULT_OK){
                Uri u = data.getData();
                iv.setImageURI(u);
                uploadImage(u);
            }
        }
    }

    private void uploadImage(Uri u) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading file.. Rukh ja");
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMax(100);
        pd.setCancelable(false);
        pd.show();
storageReference = storageReference.child("Images/"+UUID.randomUUID().toString());
storageReference.putFile(u).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        Toast.makeText(MainActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
        pd.dismiss();
    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
        double d= (100.0*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
        pd.setProgress((int)d);
    }
});

    }

    //Convert bitmap to uri
    private Uri getImageURI(Context mainActivity, Bitmap bitmap) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mainActivity.getContentResolver(),bitmap,"Title", null);

        return Uri.parse(path);
    }
}