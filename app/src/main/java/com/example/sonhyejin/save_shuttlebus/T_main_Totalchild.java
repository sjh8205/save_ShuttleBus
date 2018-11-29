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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class T_main_Totalchild extends AppCompatActivity {

    String telNum;
    ArrayList<String> names;
    ArrayList<String> classes;
    AdapterQR Adapter;
    int size = 0;
    ListView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main_totalchild);

        total = (ListView) findViewById(R.id.tTotalList);

        names = new ArrayList<String>();
        classes = new ArrayList<String>();

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
                    int status = data.child("status").getValue(Integer.class);
                    Log.v("name is", str);
                    Log.v("class is", cla);

                    switch (status){
                        case 0:
                            names.add(str);
                            classes.add(cla);
                            Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.busstop));
                            size++;
                            Log.v("print", Integer.toString(size));
                            break;

                        case 1:
                            names.add(str);
                            classes.add(cla);
                            Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.imhere));
                            size++;
                            Log.v("print", Integer.toString(size));
                            break;

                        case 2:
                            names.add(str);
                            classes.add(cla);
                            Adapter.addItem(str, cla, ContextCompat.getDrawable(T_main_Totalchild.this, R.drawable.imnothere));
                            size++;
                            Log.v("print", Integer.toString(size));
                            break;
                    }

                    Log.v("print", Integer.toString(size));
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