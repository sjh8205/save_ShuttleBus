package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class T_main extends AppCompatActivity {

    String telNum;
    ListView route;
    AdapterRoute Adapter;
    String stationName;
    ArrayList busStation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main);

        Log.v("ㄴㄴㄴㄴㄴㄴ", "T_main임다-.-");

        route = (ListView)findViewById(R.id.tBusList);
        Button seeTotal = (Button)findViewById(R.id.tViewTotal) ;

        Adapter = new AdapterRoute();

        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        telNum = data.getString("telnum","0");

        Log.v("telnum",telNum);

        FirebaseDatabase FD = FirebaseDatabase.getInstance();
        DatabaseReference DR = FD.getReference("Kindergarten");


        DR.child(telNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                busStation=new ArrayList();
                for(DataSnapshot data: dataSnapshot.child("bus").getChildren()){
                    String temp=data.getKey();
                    Boolean station = data.child("busishere").getValue(Boolean.class);

                    Log.v("telNumb",temp);
                    int idx=temp.indexOf(" ");
                    String num = temp.substring(0,idx);
                    Log.v("bus"," "+num);
                    String nam = temp.substring(idx+1);
                    Log.v("bus"," "+nam);
                    busStation.add(nam);

                    if(station == true){
                        Adapter.addItem(ContextCompat.getDrawable(T_main.this,R.drawable.bus),ContextCompat.getDrawable(T_main.this,R.drawable.busstop),nam);
                    }else{
                        Adapter.addItem(ContextCompat.getDrawable(T_main.this,R.drawable.blank),ContextCompat.getDrawable(T_main.this,R.drawable.busstop),nam);
                    }


                }
                route.setAdapter(Adapter);
                Log.v("ㄴㄴㄴㄴㄴㄴ", "어뎁터");

                route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        ListViewRoute item = (ListViewRoute) parent.getItemAtPosition(position);
                        stationName = item.getstname();
                        int num=busStation.indexOf(stationName);
                        Log.v("busStation",stationName+num);

                        String stStr = item.getstname();
                        Drawable stimg = item.getstimg();
                        Drawable busimg = item.getbusimg();

                        Intent intent = new Intent(getApplicationContext(), T_main_QRScan.class);
                        Intent intent2 = getIntent();

                        int rt = intent2.getIntExtra("rt",0); // 전 액티비티(다이얼로그)에서 값 받아옴
                        Log.v("ㄴㄴㄴㄴㄴㄴ", "rt값 : " + rt);

                        intent.putExtra("rt", rt);
                        intent.putExtra("station", stationName);
                        intent.putExtra("stationNum",Integer.toString(num));
                        startActivity(intent);
                    }
                });

                Log.v("print", "affor");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        seeTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),T_main_Totalchild.class);
                startActivity(intent);
            }
        });


/*


        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산1단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산2단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산3단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산4단지");

        route.setAdapter(Adapter);

        route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewRoute item = (ListViewRoute) parent.getItemAtPosition(position);
                String row = (String) route.getItemAtPosition(position);

                String stStr = item.getstname();
                Drawable img = item.getimg();

                Toast.makeText(getApplicationContext(), "You selected : " + row, Toast.LENGTH_SHORT).show(); // 클릭한 해당 위치 받아오기
                //받은 위치의 버스 이미지만 보이게 하고 없애기
//                route.getItemAtPosition(row).getimg

            }
        });

        seeTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),T_main_Totalchild.class);
                startActivity(intent);
            }
        });
        */
    }
}
