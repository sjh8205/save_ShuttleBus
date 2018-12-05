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
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class H_Main_View_bus extends AppCompatActivity {

    String telNum;
    ListView route;
    AdapterRoute Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_view_bus);

        route = (ListView)findViewById(R.id.hBusList);
        Adapter = new AdapterRoute();

        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        telNum = data.getString("telnum","0");

        FirebaseDatabase FD = FirebaseDatabase.getInstance();
        DatabaseReference DR = FD.getReference("Kindergarten");

        DR.child(telNum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.child("bus").getChildren()){
                    String temp=data.getKey();
                    Log.v("telNumb",temp);
                    int idx=temp.indexOf(" ");
                    String num = temp.substring(0,idx);
                    Log.v("bus"," "+num);
                    String nam = temp.substring(idx+1);
                    Log.v("bus"," "+nam);

                    Adapter.addItem(ContextCompat.getDrawable(H_Main_View_bus.this,R.drawable.bus),ContextCompat.getDrawable(H_Main_View_bus.this,R.drawable.busstop),nam);
                }
                route.setAdapter(Adapter);

                route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        ListViewRoute item = (ListViewRoute) parent.getItemAtPosition(position);

                        String stStr = item.getstname();
                        Drawable stimg = item.getstimg();
                        Drawable busimg = item.getbusimg();
                    }
                });

                Log.v("print", "affor");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        /*

        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"안양1단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"안양2단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"안양3단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"안양4단지");

        route.setAdapter(Adapter);

        route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewRoute item = (ListViewRoute) parent.getItemAtPosition(position);

                String stStr = item.getstname();
                Drawable img = item.getimg();
            }
        });
        */
    }
}
