package com.example.sonhyejin.save_shuttlebus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.content.pm.PackageManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.media.MediaScannerConnection;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private static final String IMAGE_DIRECTORY = "/demonuts";
    String downloadUrl;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    String kindergarten;
    String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_register_child);

        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        kindergarten=data.getString("telnum","0");

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

        int permission =ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        checkAppPermission(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE});
        if(permission==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            Log.v("busS","StorageP");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ChildName =childName.getText().toString();
                ChildClass=childClass.getText().toString();
                PhoneNum=phoneNum.getText().toString();
                BusStation=busStation.getText().toString();
                DatabaseReference df=FirebaseDatabase.getInstance().getReference("Kindergarten")
                        .child(kindergarten);
                df.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int check = 0;
                        int ccheck=0;
                        Log.v("busS","*"+BusStation);
                        for(DataSnapshot data:dataSnapshot.child("bus").getChildren()){
                            String temp=data.getKey();
                            int idx=temp.indexOf(" ");
                            String nam = temp.substring(idx+1);
                            Log.v("busS"," "+nam);
                            if(BusStation.equals(nam)){
                                check++;
                                Log.v("busS","*"+check);
                            }
                        }
                        Log.v("classCheck","*"+ChildClass);
                        for(DataSnapshot data1:dataSnapshot.child("Teacher").getChildren()){
                            String temp= (String) data1.child("tClass").getValue();
                            Log.v("classCheck","*"+temp);
                            if(ChildClass.equals(temp)){
                                ccheck++;
                                Log.v("classCheck","*"+ccheck);
                            }
                        }
                        if(check!=0&&ccheck!=0){
                            String qrString= PhoneNum;
                            //qr코드에 들어갈 문구
                            Bitmap qr=generateRQCode(qrString);
                            //qr코드 생성
                            Log.v("qr","qrsuccess");
                            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                            qr.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                            //qr 코드 사이즈 형식 지정
                            Log.v("qr","qrTobyte");
                            String str=saveImage(qr,qrString);
                            //qr코드 기기에 저장해서 상대경로 가져오기
                            qr.recycle();
                            File file =new File(str);
                            Uri uri=Uri.fromFile(file);
                            //기기에 저장된 qr코드에 절대경로 가져오기
                            Log.v("ex",uri.toString());
                            if(TextUtils.isEmpty(ChildName)||TextUtils.isEmpty(ChildClass)||
                                    TextUtils.isEmpty(PhoneNum)||TextUtils.isEmpty(BusStation)){
                                //빈칸이 있음
                                Toast.makeText(getApplicationContext(),"There is a blank space",Toast.LENGTH_SHORT).show();
                            }else {
                                //빈칸 없음
                                //파이어베이스 스토리지에 qr코드 이미지 저장하고
                                //파이어베이스 리얼타임 디비에 정보 저장하기 위해 함수 호출하고
                                //완료 되면 원장 메인으로 화면 전환
                                registerChild=new Register_child(ChildName,ChildClass,PhoneNum,
                                        BusStation,bus,3, null);
                                uploadImage(uri,registerChild);
                                Intent intent1=new Intent(getApplicationContext(),H_Main.class);
                                startActivity(intent1);
                            }
                        }else{
                            //원생을 등록하는데 해당하는 정류장이 없을 때
                            //반 정보가 없을 때
                            Toast.makeText(getApplicationContext(),"Bus station or Class does not exist",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });


    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Log.v("permissionPN","external permission authorized");
                        } else {
                            Log.v("permissionPN","camera permission denied");
                        }
                    }
                }
                break;


        }
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
    public String saveImage(Bitmap myBitmap,String phoneNum) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, phoneNum + ".jpg");
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
    private void uploadImage(Uri filePath, final Register_child register_child){
        if(filePath!=null){
            Log.v("fbs","filePathT");
            Log.v("fbs","8"+kindergarten);
            final StorageReference ref= storageReference.child("images/").child(kindergarten).
                    child("child").child(filePath.getLastPathSegment());
            Log.v("fbs","spathT"+filePath);
            UploadTask uploadTask=ref.putFile(filePath);
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imgPath=uri.toString();
                            Log.v("storage","upOK  "+imgPath);
                            register_child.setQrUri(imgPath);
                            final DatabaseReference databaseReference;
                            databaseReference= FirebaseDatabase.getInstance().getReference("Kindergarten");
                            databaseReference.child(kindergarten)
                                    .child("child").child(PhoneNum).setValue(register_child);
                            Log.v("storage","imgPathOK");
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
