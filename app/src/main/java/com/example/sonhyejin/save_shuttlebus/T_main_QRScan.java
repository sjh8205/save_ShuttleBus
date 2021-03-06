package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class T_main_QRScan extends AppCompatActivity {

    private IntentIntegrator qrScan;
    private SwipeRefreshLayout swipeRefreshLayout;
    Intent intent;

    String telNum;
    AdapterQR Adapter;
    ListView qr;
    boolean attendance = true; // 출석 확인 변수
    SharedPreferences data;
    Button scanner;
    Button submit;
    String station;
    String Scanresult = "0";
    DatabaseReference DR;
    FirebaseDatabase FD;
    String stationNum;
    boolean atleast = false;

    int rt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main_qrscan);

        qr = (ListView) findViewById(R.id.tChildList);
        scanner = (Button) findViewById(R.id.tQrScan);
        submit = (Button) findViewById(R.id.tCompleteScan);



        data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editdata = data.edit();
        telNum = data.getString("telnum","0");

        Intent intent = getIntent();
        rt = intent.getIntExtra("rt", 0); // defaultValue는 0으로 세팅 (유치원에 있음)
        Log.v("ㄴㄴㄴㄴㄴㄴ", "rt값 : " + rt);
        station = intent.getStringExtra("station");
        stationNum=intent.getStringExtra("stationNum");

        FD = FirebaseDatabase.getInstance();
        DR = FD.getReference("Kindergarten");

        Log.v("telNUM is", telNum);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_layout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Adapter = new AdapterQR();
                DR.child(telNum).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) { // 스캔하고 돌아올 때마다 계속 검사할 것
                        Log.v("before for", "A");
                        for(DataSnapshot data: dataSnapshot.child("child").getChildren()){
                            String str = data.child("childName").getValue(String.class);
                            String cla = data.child("childClass").getValue(String.class);
                            Log.v("name is", str);
                            Log.v("class is", cla);
                            String com = data.child("childBusStation").getValue(String.class);
                            int status = data.child("childOnBus").getValue(Integer.class);
                            Log.v("busstation is", com);
                            if(com.equals(station)){
                                switch (status){
                                    case 1: // 결석

                                        Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.absence));
                                        break;

                                    case 2: // 승차
                                        if(rt == 2)
                                            Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.imhere));
                                        else {
                                            Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.imnothere));
                                            attendance = false; // 하차 안 한 아이가 한 명이라도 있다면 attendance가 완료되지 않은 것 -> 버튼 계속 비활성화
                                        }
                                        //                               Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", String.valueOf(attendance));
                                        break;

                                    case 3: // 하차
                                        if(rt == 3)
                                            Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.imhere));
                                        else {
                                            Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.imnothere));
                                            attendance = false; // 하차 안 한 아이가 한 명이라도 있다면 attendance가 완료되지 않은 것 -> 버튼 계속 비활성화
                                        }
                                        break;

                                }
                            }
                        }
// 모든 아이들이 하차 상태일 때 submit 버튼 활성화
                        if(attendance) {
                            submit.setEnabled(true); // 됩니당 /(^ㅁ^)/~~~~
                            Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ","submit셋액티베이테드");
                        }

                /*
                for(int i=0;i<size;i++){
                    Adapter.addItem(names.get(i), classes.get(i), ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.imhere));
                    Log.v("print", names.get(i));
                }*/

                        qr.setAdapter(Adapter);

                        qr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView parent, View view, int position, long id) {
                                ListViewQR item = (ListViewQR) parent.getItemAtPosition(position);

                                //Adapter.addItem("d", "매화", ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.imhere));

                                String sName = item.getsName();
                                String sClass = item.getsClass();
                                Drawable img = item.getimg();
                            }
                        });

                        Log.v("print", "affor");
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*qrScan.setPrompt("Scanning...");
                qrScan.setOrientationLocked(false);
                qrScan.initiateScan();*/
                new IntentIntegrator(T_main_QRScan.this).initiateScan();

            }
        });

// 활성화된 버튼을 누르면 T_main으로 돌아감
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),T_main.class);
                intent.putExtra("rt", rt);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String str;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Log.v("ㄴㄴㄴㄴㄴㄴ", "대체 무슨 일이 일어나고 있나요?");

        FD = FirebaseDatabase.getInstance();
        DR = FD.getReference("Kindergarten");
        final Map<String,Object> taskMap = new HashMap<String,Object>();

        if(result != null && resultCode == RESULT_OK) {
            // if user scanned and the result is valid, do your stuff here
            Scanresult = result.getContents().toString();
            Toast.makeText(this, "Scanned: " + Scanresult, Toast.LENGTH_LONG).show();

            DR.child(telNum).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) { // 스캔하고 돌아올 때마다 계속 검사할 것
                    Log.v("before for", "A");
                    for(DataSnapshot data: dataSnapshot.child("child").getChildren()){
                        Log.v("ㄴㄴㄴㄴㄴㄴ", "대체 무슨 일이 일어나고 있나요?000");

                        String num = data.child("childPhoneNum").getValue().toString();
                        int busState = data.child("childOnBus").getValue(Integer.class);

                        Log.v("QR scan success", num);

                        if(num.equals(Scanresult)){
                            Log.v("ㄴㄴㄴㄴㄴㄴ", "대체 무슨 일이 일어나고 있나요?111");

                            atleast = true; //적어도 한명이라도 찍었다!

                            taskMap.put("childOnBus", rt); // 승차, 하차에 따라 저장 값 바뀜
                            DR.child(telNum).child("child").child(num).updateChildren(taskMap);

                            Log.v("ㄴㄴㄴㄴㄴㄴ", "rt값 : " + rt);

/*                            if(busState == 2){
                                taskMap.put("childOnBus",3);
                                DR.child(telNum).child("child").child(num).updateChildren(taskMap);
                            }else if(busState ==3){
                                taskMap.put("childOnBus",2);
                                DR.child(telNum).child("child").child(num).updateChildren(taskMap);
                            }
*/
                        }

                    }

                    if(atleast){ //적어도 한명이라도 찍었다면
                        for(DataSnapshot data2: dataSnapshot.child("bus").getChildren()){ //일단 다른 버스 정류장 busishere 값 다 false로
                            Log.v("ㄴㄴㄴㄴㄴㄴ", "대체 무슨 일이 일어나고 있나요?222");

//                            String nowstation = data2.child("station").getValue(String.class);
//                            String nowtime = data2.child("time").getValue(String.class);
                            String temp=data2.getKey();

                            taskMap.put("busishere",false);
//                            String temp=nowtime+" "+nowstation;
                            DR.child(telNum).child("bus").child(temp).updateChildren(taskMap);

                        }

                        Log.v("ㄴㄴㄴㄴㄴㄴ", "대체 무슨 일이 일어나고 있나요?333");

                        taskMap.put("busishere",true); //얘만 true로
                        String temp=stationNum+" "+station;
                        DR.child(telNum).child("bus").child(temp).updateChildren(taskMap);
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });

           // Intent intent = new Intent(getApplicationContext(), T_main_QRScan.class);

            //startActivity(intent);
        } else {
            // if user pressed back or there's error, do your stuff here
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }

    }
}


/*
* String row = (String) qr.getItemAtPosition(position);
* Toast.makeText(getApplicationContext(), "You selected : " + row, Toast.LENGTH_SHORT).show(); // 클릭한 해당 위치 받아오기
* */