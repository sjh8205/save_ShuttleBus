package com.example.sonhyejin.save_shuttlebus;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class Login extends AppCompatActivity {


    int status = 0; //head, teacher, parent를 구분하는 status --> 이 값으로 role 구분해서 값 넘기면 됨
    Button loginH;
    Button loginT;
    Button loginP;
    String autonum = null;
    String telNum;
    Intent intent; // main으로 갈 때 쓰는 인텐트
    Intent H_intent ; // join으로 갈 때 쓰는 인텐트(원장만 씀)
    SharedPreferences check;
    SharedPreferences data;
    SharedPreferences.Editor move ;
    SharedPreferences.Editor editdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        editdata = data.edit();

        check = getSharedPreferences("login", Context.MODE_PRIVATE);
        move = check.edit();
        
        loginH = (Button)findViewById(R.id.loginCheckH);
        loginT = (Button)findViewById(R.id.loginCheckT);
        loginP = (Button)findViewById(R.id.loginCheckP);

        loginH.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = 1;
                loginH.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
                loginT.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                loginP.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
            }
        });

        loginT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = 2;
                loginH.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                loginT.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
                loginP.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
            }
        });

        loginP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = 3;
                loginH.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                loginT.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                loginP.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
            }
        });
        TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            int permissionPhone = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
            if(permissionPhone == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            } else {
                Toast.makeText(getApplicationContext(),"camera permission authorized",Toast.LENGTH_SHORT).show();
                if(mgr.getLine1Number() !=null){
                    autonum = mgr.getLine1Number();
                }
                else {
                    if(mgr.getSimSerialNumber() != null){
                        autonum = mgr.getSimSerialNumber();
                    }
                }
                if(autonum.contains("+82")){
                    autonum = autonum.replace("+82", "0");
                }else if(autonum.contains("+")){
                    autonum = autonum.replace("+", "");
                }


                editdata.putString("mynum",autonum);
                editdata.commit();

                Toast.makeText(getApplicationContext(), autonum, Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e) {
            //Toast.makeText(getApplicationContext(), "오류ㅠ000000으로 저장합니다", Toast.LENGTH_SHORT).show();
            autonum = "0000000";
        } // 디바이스 번호 받아오기

        // 빈 textview창 => autonum 띄워주기
        TextView num = (TextView) findViewById(R.id.getAutoNum);
        // autonum = "0000000";
        num.setText(autonum);
        Toast.makeText(this,autonum,Toast.LENGTH_SHORT).show();

        final EditText tel_num = (EditText)findViewById(R.id.kinHeadNum);

        // submit 클릭 시 정보 넘겨서 데이터베이스 조회하기
        Button submit = (Button)findViewById(R.id.loginSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 섭밋 누르면
                telNum=(String)tel_num.getText().toString();
                Log.v("tel_Num",tel_num.getText().toString());
                editdata.putString("telnum",telNum);
                editdata.commit();

                Log.v("telNUm in SharedPreferende",telNum);

                FirebaseDatabase FD = FirebaseDatabase.getInstance();
                DatabaseReference DR = FD.getReference("Kindergarten");
                //파이어베이스에서 Kindergarten 밑에 있는 값들을 접근하기 위한 변수 선언
                if(TextUtils.isEmpty(telNum)||status==0){
                    Toast.makeText(getApplicationContext(),"Please enter kindergarten Number",Toast.LENGTH_SHORT).show();
                }else {
                    DR.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.v("log","on");
                            int check=0;
                            for(DataSnapshot data:dataSnapshot.getChildren()){
                                Log.v("log","for");
                                String temp=data.getKey();
                                Log.v("telNum",telNum);
                                Log.v("dbNum",temp);
                                if(telNum.equals(temp)){
                                    Log.v("check","same");
                                    check++;
                                }
                            }
                            String check1=Integer.toString(check);
                            if(check==0){//디비에 유치원 없음
                                Log.v("noK","noK");
                                if(status==1){
                                    Log.v("noK","s1");
                                    H_intent=new Intent(getApplicationContext(),H_Join.class);
                                    H_intent.putExtra("phone",autonum);

                                    move.putInt("FirstorNot",1);
                                    move.commit();

                                    startActivity(H_intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Wrong kindergarten number",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                if(status==1){
                                    String Num = dataSnapshot.child(telNum).child("headNum").getValue().toString();
                                    if(autonum.equals(Num)){
                                        Intent intent1=new Intent(getApplicationContext(),H_Main.class);
                                        intent1.putExtra("phone",autonum);
                                        intent1.putExtra("telNum",telNum);
                                        Toast.makeText(getApplicationContext(), "록ㄱㄱ", Toast.LENGTH_SHORT).show();

                                        move.putInt("FirstorNot",1);
                                        move.commit();

                                        startActivity(intent1);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Not accepted!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }else if(status==2){
                                    int check2=0;
                                    for(DataSnapshot data1: dataSnapshot.child(telNum).child("Teacher").getChildren()){
                                        String temp=data1.getKey();
                                        Log.v("telNum",autonum);
                                        Log.v("dbNum",temp);
                                        if(autonum.equals(temp)){
                                            Log.v("check","same");
                                            check2++;
                                        }
                                    }
                                    if(check2==0){
                                        Toast.makeText(getApplicationContext(),"Not accepted!",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Intent intent2=new Intent(getApplicationContext(),T_main.class);
                                        intent2.putExtra("phone",autonum);
                                        intent2.putExtra("telNum",telNum);
                                        Toast.makeText(getApplicationContext(), "록ㄱㄱ", Toast.LENGTH_SHORT).show();

                                        move.putInt("FirstorNot",3);
                                        move.commit();

                                        startActivity(intent2);
                                    }
                                }else if(status==3){
                                    int check2=0;
                                    for(DataSnapshot data1: dataSnapshot.child(telNum).child("child").getChildren()){
                                        String temp=data1.getKey();
                                        Log.v("telNum",autonum);
                                        Log.v("dbNum",temp);
                                        if(autonum.equals(temp)){
                                            Log.v("check","same");
                                            check2++;
                                            Toast.makeText(getApplicationContext(), "록ㄱㄱ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    if(check2==0){
                                        Toast.makeText(getApplicationContext(),"Not accepted!",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Intent intent2=new Intent(getApplicationContext(),P_main.class);
                                        intent2.putExtra("phone",autonum);
                                        intent2.putExtra("telNum",telNum);

                                        move.putInt("FirstorNot",2);
                                        move.commit();

                                        startActivity(intent2);
                                    }
                                }
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.READ_PHONE_STATE)) {
                        if(grantResult == PackageManager.PERMISSION_GRANTED) {
                            Log.v("permissionPN","camera permission authorized");
                        } else {
                            Log.v("permissionPN","camera permission denied");
                        }
                    }
                }
                break;


        }
    }


}
//권한분리 명확히 한 이유와 장점을 발표 때 언급
//여러 기능들이 있으니까, '승하차안전'쪽에만 치우치지 않고 더 포괄적으로 잡기