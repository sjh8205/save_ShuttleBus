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
    String sStation="a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main_qrscan);

        qr = (ListView) findViewById(R.id.tChildList);
        Button scanner = (Button) findViewById(R.id.tQrScan);
        Button submit = (Button) findViewById(R.id.tCompletScan);

        Adapter = new AdapterQR();

        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editdata = data.edit();
        telNum = data.getString("telnum","0");

        FirebaseDatabase FD = FirebaseDatabase.getInstance();
        DatabaseReference DR = FD.getReference("Kindergarten");

        DR.child("025556666").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.child("child").getChildren()){
                    String str = data.child("name").getValue(String.class);
                    String cla = data.child("class").getValue(String.class);
                    Log.v("name is", str);
                    Log.v("class is", cla);
                    String com = data.child("station").getValue(String.class);
                    int status = data.child("status").getValue(Integer.class);
                    Log.v("class is", com);
                    if(com.equals(sStation)){
                        switch (status){
                            case 0:
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.busstop));
                                break;

                            case 1:
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.imhere));
                                break;

                            case 2:
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_QRScan.this, R.drawable.imnothere));
                                break;
                        }

                    }
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
                qrScan.setPrompt("Scanning...");
                //qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });

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
            str = result.getContents().toString();
            Toast.makeText(this, "Scanned: " + str, Toast.LENGTH_LONG).show();
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