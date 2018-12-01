package com.example.sonhyejin.save_shuttlebus;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class H_Main_Register_Teacher extends AppCompatActivity {
    EditText hRegTeachName;
    String teachName;
    EditText hRegTeachClass;
    String teachClass;
    EditText hRegTeachNum;
    String teachNum;
    registerTeacher registerTeacher;
    ImageView imageView;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    final int GALLERY = 1, CAMERA = 2;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference table;
    String telNum;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_register_teacher);
        Intent intent=getIntent();
        telNum=intent.getStringExtra("telNum");

        hRegTeachName=(EditText)findViewById(R.id.hRegTeachName);
        hRegTeachClass=(EditText)findViewById(R.id.hRegTeachClass);
        hRegTeachNum=(EditText)findViewById(R.id.hRegTeachNum);
        imageView=(ImageView)findViewById(R.id.pics);
        Button gallery=(Button)findViewById(R.id.gal);
        Button cam=(Button)findViewById(R.id.cam);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallary();
            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
            }
        });

        Button hRegTeachSubmit=(Button)findViewById(R.id.hRegTeachSubmit);


        table= FirebaseDatabase.getInstance().getReference("Kindergarten");
        hRegTeachName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==hRegTeachName){
                    hRegTeachName.setText("");
                }
            }
        });
        hRegTeachNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==hRegTeachNum){
                    hRegTeachNum.setText("");
                }
            }
        });
        hRegTeachClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==hRegTeachClass){
                    hRegTeachClass.setText("");
                }
            }
        });
        hRegTeachSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teachName=hRegTeachName.getText().toString();
                teachClass=hRegTeachClass.getText().toString();
                teachNum=hRegTeachNum.getText().toString();
                registerTeacher=new registerTeacher(teachName,teachClass,teachNum,imgPath);

                Intent intent1=new Intent(getApplicationContext(),H_Main.class);
                table.child(telNum).child("Teacher").child(teachNum).setValue(registerTeacher);
                startActivity(intent1);
            }
        });


    }
    boolean checkAppPermission(String[] requestPermission){
        boolean[] requestResult= new boolean[requestPermission.length];
        for(int i=0; i< requestResult.length; i++){
            requestResult[i] = (ContextCompat.checkSelfPermission(this,
                    requestPermission[i]) == PackageManager.PERMISSION_GRANTED);
            if(!requestResult[i])
            {
                return false;
            }
        }
        return true;
    }
    void askPermission(String[] requestPermission, int REQ_PERMISSION) {
        ActivityCompat.requestPermissions(this,requestPermission,
                REQ_PERMISSION);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case GALLERY:
                if(checkAppPermission(permissions)){
                    Intent intent=new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,GALLERY);
                }
                else{
                    finish();
                }
                break;
        }
    }

    public void choosePhotoFromGallary() {

        if(checkAppPermission(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE})){
            Intent intent=new Intent(Intent.ACTION_PICK);
            intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,GALLERY);
        }
        else{
            askPermission(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    GALLERY);
        }
    }

    private void takePhotoFromCamera() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        if(permissionCheck==PackageManager.PERMISSION_DENIED){
            //권한 없음
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},CAMERA);
            Log.v("p","권함없음");
        }else{
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        storage= FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    Log.v("img","imgsaved");
                    String path = saveImage(bitmap);

                    imageView.setImageBitmap(bitmap);

                    uploadImage(contentURI);
                    //Toast.makeText(Senior_addroom.this,contentURI.toString(),Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v("img","imgsavefail");
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Uri cameraUri=null;
            imageView.setImageBitmap(thumbnail);

            String str= saveImage(thumbnail);
            //CameraUri=Uri.fromFile(getFileStreamPath(f.toString()));
            File f=new File(str);
            cameraUri=Uri.fromFile(f);
            uploadImage(cameraUri);
            Log.v("img","cimgS");
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();

            Log.d("TAG", "File Saved::??" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }
    private void uploadImage(Uri filePath) {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/").child(telNum).
                    child("Teacher").child(filePath.getLastPathSegment());
            UploadTask uploadTask = ref.putFile(filePath);

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imgPath=uri.toString();

                                }
                            });
                            Toast.makeText(getApplicationContext(), "Uploa ded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

        }
    }

}
