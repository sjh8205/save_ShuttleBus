package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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

public class T_main_QRScan extends AppCompatActivity {

    private IntentIntegrator qrScan;

    String telNum;
    AdapterQR Adapter;
    ListView qr;
    boolean attendance = true; // 출석 확인 변수
    SharedPreferences data;
    Button scanner;
    Button submit;
    String station;
    String Scanresult = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main_qrscan);



        qr = (ListView) findViewById(R.id.tChildList);
        scanner = (Button) findViewById(R.id.tQrScan);
        submit = (Button) findViewById(R.id.tCompleteScan);

        Adapter = new AdapterQR();

        data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editdata = data.edit();
        telNum = data.getString("telnum","0");

        Intent intent = getIntent();
        station = intent.getStringExtra("station");

        FirebaseDatabase FD = FirebaseDatabase.getInstance();
        DatabaseReference DR = FD.getReference("Kindergarten");

        Log.v("telNUM is", telNum);

        DR.child(telNum).addValueEventListener(new ValueEventListener() {
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
    //                            Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "결석했잖아요");
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.busstop));
                                break;

                            case 2: // 승차
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.imhere));
  //                              Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "승차중이에요");
                                attendance = false; // 하차 안 한 아이가 한 명이라도 있다면 attendance가 완료되지 않은 것 -> 버튼 계속 비활성화
 //                               Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", String.valueOf(attendance));
                                break;

                            case 3: // 하차
  //                              Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "하차함요");
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.imnothere));
  //                              Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", String.valueOf(attendance));
                                break;

                        }

                    }
                }
// 모든 아이들이 하차 상태일 때 submit 버튼 활성화
                if(attendance) {
                    submit.setEnabled(true); // 됩니당 /(^ㅁ^)/~~~~
//                    Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ","submit셋액티베이테드");
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
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String str;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null && resultCode == RESULT_OK) {
            // if user scanned and the result is valid, do your stuff here
            Scanresult = result.getContents().toString();
            Toast.makeText(this, "Scanned: " + Scanresult, Toast.LENGTH_LONG).show();
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