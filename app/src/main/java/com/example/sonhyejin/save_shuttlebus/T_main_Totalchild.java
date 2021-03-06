package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.lang.*;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class T_main_Totalchild extends AppCompatActivity {

    String telNum;
    AdapterQR Adapter;
    ListView total;
    Intent intent;
    int rt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main_totalchild);

        total = (ListView) findViewById(R.id.tTotalList);
        intent = getIntent();
        rt = intent.getIntExtra("rt", 0);

        Adapter = new AdapterQR();

        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editdata = data.edit();
        telNum = data.getString("telnum","0");
        Log.v("telNum is", telNum);

        FirebaseDatabase FD = FirebaseDatabase.getInstance();
        DatabaseReference DR = FD.getReference("Kindergarten");

        Log.v("C", "c");

        DR.child(telNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.v("A", "a");

                for(DataSnapshot data: dataSnapshot.child("child").getChildren()){
                    Log.v("B", "b");
                    String str = data.child("childName").getValue(String.class);
                    String cla = data.child("childClass").getValue(String.class);
                    int status = data.child("childOnBus").getValue(Integer.class);
                    Log.v("name is", str);
                    Log.v("class is", cla);

                    switch (status){
                        case 1:
                            Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.absence));
                            break;

                        case 2:
                            if(rt == 2)
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.imhere));
                            else
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.imnothere));
                            break;

                        case 3:
                            if(rt == 3)
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.imhere));
                            else
                                Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.imnothere));
                            break;
                    }

                }
                total.setAdapter(Adapter);

                total.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


    }
}

/*
*     final ListView total = (ListView) findViewById(R.id.tTotalList);

    AdapterQR Adapter = new AdapterQR();
* */
/*
* FirebaseDatabase FD = FirebaseDatabase.getInstance();
        DatabaseReference DR = FD.getReference("Kindergarten");

        DR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("log","on");
                for(DataSnapshot data: dataSnapshot.child(telNum).child("child").getChildren()){
                    Log.v("log","for");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        total.setAdapter(Adapter);

        total.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewQR item = (ListViewQR) parent.getItemAtPosition(position);

                Adapter.addItem("d", "매화", ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.imhere));

                String sName = item.getsName();
                String sClass = item.getsClass();
                Drawable img = item.getimg();
            }
        });
* */