package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.content.pm.PackageManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.media.MediaScannerConnection;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.*;
import java.util.Calendar;
import java.io.IOException;

public class H_Main_Register_child extends AppCompatActivity {

    boolean busRideStatus;
    EditText station;

    Button busyes;
    Button busno;
    boolean status;
    String ChildName;
    EditText childName;
    EditText childClass;
    String ChildClass;
    EditText phoneNum;
    String PhoneNum;
    EditText busStation;
    String  BusStation;
    Register_child registerChild;
    Boolean bus=Boolean.TRUE;
    private static final String IMAGE_DIRECTORY = "/demonuts/";
    String downloadUrl;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String kindergarten;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_register_child);

        Intent intent;

        intent=getIntent();
        kindergarten=intent.getStringExtra("telNum");

        final DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference("Kindergarten");
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        busyes = (Button)findViewById(R.id.busYes);
        busno = (Button)findViewById(R.id.busNo);

        busyes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = true;

                if(status==true){
                    busyes.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
                    busno.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                }
            }
        });

        busno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = false;
                if(status==false){
                    busyes.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                    busno.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
                }
            }
        });

        childName = (EditText) findViewById(R.id.hRegChildName);
        //원생 등록할 때 원생 이름
        childClass = (EditText) findViewById(R.id.hRegChildClass);
        //원생 등록시 원생이 속한 반
        phoneNum = (EditText) findViewById(R.id.hRegChildNum);
        //원생 등록시 원생 보호자 중 한명의 전화번호
        busStation=(EditText)findViewById(R.id.hRegChildStation);
        //원생 등록시 원생이 버스를 탈 경우 버스 정류장

        Button btn=(Button)findViewById(R.id.hRegChildSubmit);

        Button.OnClickListener onClickListener=new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch(v.getId()){
                   case R.id.busYes:
                       bus=Boolean.TRUE;
                       //registerChild.setChildBus(bus);
                       break;
                   case R.id.busNo:
                       bus=Boolean.FALSE;
                       //registerChild.setChildBus(bus);
                       break;
               }

            }
        };
        final String qrString= ChildName+"/"+PhoneNum;

        checkAppPermission(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE});
        checkAppPermission(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE});
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap qr=generateRQCode(qrString);
                Log.v("qr","qrsuccess");
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                qr.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                Log.v("qr","qrTobyte");
                qr.recycle();
                File dir=new File(Environment.getExternalStorageDirectory().getPath()+IMAGE_DIRECTORY);
                if(!dir.exists()){
                    Log.v("make","dir");
                    dir.mkdirs();
                }
                try{
                    Log.v("make","file");
                    File f=new File(dir, Calendar.getInstance().getTimeInMillis()+".jpg");
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(byteArrayOutputStream.toByteArray());
                    MediaScannerConnection.scanFile(getApplicationContext(),
                            new String[]{f.getPath()},
                            new String[]{"image/jpeg"}, null);
                    fo.close();
                    downloadUrl=f.getAbsolutePath();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                /*UploadTask uploadTask=storageReference.child("Kindergarten/").child(kindergarten).
                        child
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v("storage","uploadfail");
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.v("storage","upload");
                        Uri downloadUrl=taskSnapshot.getDownloadUrl();
                        String uri=downloadUrl.toString();
                        Log.v("storage",uri);
                        registerChild.setQrUri(uri);
                    }
                });*/
                File file =new File(downloadUrl);
                Uri uri=Uri.fromFile(file);
                uploadImage(uri);
                ChildName =childName.getText().toString();
                ChildClass=childClass.getText().toString();
                PhoneNum=phoneNum.getText().toString();
                BusStation=busStation.getText().toString();
                registerChild=new Register_child(ChildName,ChildClass,PhoneNum,
                        BusStation,bus,Boolean.FALSE, imgPath);

                databaseReference.child(kindergarten)
                        .child("child").child(PhoneNum).setValue(registerChild);
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
    /*public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
    }*/

    public Bitmap generateRQCode(String contents) {
        Bitmap bitmap = null ;

        try{
            Log.v("qr","makingtry");
            QRCodeWriter qrCodeWriter=new QRCodeWriter();
            bitmap=toBitmap(qrCodeWriter.encode(contents,BarcodeFormat.QR_CODE,200,200));

        } catch (WriterException e) {
            Log.v("qr","makeerror");
            e.printStackTrace();
        }
        return bitmap;

    }

    private static Bitmap toBitmap(BitMatrix matrix) {
        int height= matrix.getHeight();
        int width=matrix.getWidth();
        Bitmap bmp=Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);
        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                bmp.setPixel(x,y,matrix.get(x,y)? Color.BLACK:Color.WHITE);
            }
        }
        return bmp;
    }
    private void uploadImage(Uri filePath){
        if(filePath!=null){
            final StorageReference ref= storageReference.child("Kindergarten/").child(kindergarten).
                    child(PhoneNum).child(filePath.getLastPathSegment());
            UploadTask uploadTask=ref.putFile(filePath);
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgPath=uri.toString();
                            Log.v("storage","upOK");
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.v("storage",e.getMessage());
                }
            });
        }
    }


}
