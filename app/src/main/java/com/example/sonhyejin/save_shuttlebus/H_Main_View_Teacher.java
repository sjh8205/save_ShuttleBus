package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class H_Main_View_Teacher extends AppCompatActivity {

    String telNum;
    ListView people;
    AdapterTeach adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_view_teacher);

        people = (ListView)findViewById(R.id.hTeachList);
        adapter = new AdapterTeach();

        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        telNum = data.getString("telnum","0");

        FirebaseDatabase FD = FirebaseDatabase.getInstance();
        DatabaseReference DR = FD.getReference("Kindergarten");

        DR.child("telNum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.child("Teacher").getChildren()){
                    String nam = data.child("name").getValue(String.class);
                    String cla = data.child("tClass").getValue(String.class);
                    String num = data.child("phone").getValue(String.class);
                    String pic=data.child("imgPath").getValue().toString();

                    adapter.addItem(Uri.parse(pic),nam,cla,num);
                }
                people.setAdapter(adapter);

                people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        ListViewTeach item = (ListViewTeach) parent.getItemAtPosition(position);

                        Uri img = item.getimg();
                        String nameStr = item.gettName();
                        String classStr = item.gettClass();
                        String numStr = item.gettNum();
                    }
                });

                Log.v("print", "affor");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

/*

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"꽃","0101","00");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"매","0101","00");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"장","0101","00");

        people.setAdapter(adapter);

        people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewTeach item = (ListViewTeach) parent.getItemAtPosition(position);

                Drawable img = item.getimg();
                String nameStr = item.gettName();
                String classStr = item.gettClass();
                String numStr = item.gettNum();
            }
        });*/
    }
}
