package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class H_Main_Register_child extends AppCompatActivity {

    boolean busRideStatus;
    EditText station;

    Button busyes;
    Button busno;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_register_child);

        Intent intent;
        final String kindergarten;
        intent=getIntent();
        kindergarten=intent.getStringExtra("kindergarten");

        final DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        final StorageReference storageReference=firebaseStorage.getReference();
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

        EditText childName = (EditText) findViewById(R.id.hRegChildName);
        //원생 등록할 때 원생 이름
        String ChildName=childName.getText().toString();

        EditText childClass = (EditText) findViewById(R.id.hRegChildClass);
        //원생 등록시 원생이 속한 반
        String ChildClass=childClass.getText().toString();

        EditText phoneNum = (EditText) findViewById(R.id.hRegChildNum);
        //원생 등록시 원생 보호자 중 한명의 전화번호
        final String PhoneNum=phoneNum.getText().toString();

        EditText busStation=(EditText)findViewById(R.id.hRegChildStation);
        //원생 등록시 원생이 버스를 탈 경우 버스 정류장
        String  BusStation=busStation.getText().toString();

        final Register_child registerChild=new Register_child(ChildName,ChildClass,PhoneNum,
                BusStation,Boolean.FALSE,Boolean.FALSE, null);

        Button btn=(Button)findViewById(R.id.hRegChildSubmit);

        Button.OnClickListener onClickListener=new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Boolean bus;
               switch(v.getId()){
                   case R.id.busYes:
                       bus=Boolean.TRUE;
                       registerChild.setChildBus(bus);
                       break;
                   case R.id.busNo:
                       bus=Boolean.FALSE;
                       registerChild.setChildBus(bus);
                       break;
               }
            }
        };
        final String qrString= ChildName+"/"+PhoneNum;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap qr=generateRQCode(qrString);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                qr.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] data=byteArrayOutputStream.toByteArray();
                Uri downloadUrl;
                UploadTask uploadTask=storageReference.child("Kindergarten").child(kindergarten).
                        putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl=taskSnapshot.getDownloadUrl();
                        String uri=downloadUrl.toString();
                        registerChild.setQrUri(uri);
                    }
                });

                databaseReference.child("Kindergarten").child(kindergarten)
                        .child("child").child(PhoneNum).setValue(registerChild);

            }
        });

    }
    public Bitmap generateRQCode(String contents) {
        Bitmap bitmap = null ;

        try{
            QRCodeWriter qrCodeWriter=new QRCodeWriter();
            bitmap=toBitmap(qrCodeWriter.encode(contents,BarcodeFormat.QR_CODE,200,200));

        } catch (WriterException e) {
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


}
