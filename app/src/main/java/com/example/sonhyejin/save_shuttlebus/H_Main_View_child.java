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

public class H_Main_View_child extends AppCompatActivity {

    String telNum;
    AdapterChild adapter;
    ListView people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_view_child);

        people = (ListView)findViewById(R.id.hChildList);
        adapter = new AdapterChild();

        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        telNum = data.getString("telnum","0");

        FirebaseDatabase FD = FirebaseDatabase.getInstance();
        DatabaseReference DR = FD.getReference("Kindergarten");

        DR.child("025556666").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.child("child").getChildren()){
                    String nam = data.child("name").getValue(String.class);
                    String cla = data.child("class").getValue(String.class);
                    String num = data.child("Phone").getValue(String.class);

                    adapter.addItem(nam,cla,num);
                }
                people.setAdapter(adapter);

                people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        ListViewChild item = (ListViewChild) parent.getItemAtPosition(position);

                        String nameStr = item.getpName();
                        String classStr = item.getpClass();
                        String numStr = item.getpNum();
                    }
                });

                Log.v("print", "affor");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        /*

        Button but = (Button)findViewById(R.id.next);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(H_Main_View_child.this,H_Main_View_Teacher.class));
            }
        });

        */
        //상단 주석처리된 구문은 리스트뷰가 개별적으로 잘 나오는지 확인하기 위함

    }
}
/*

        adapter.addItem("방승희","꽃잎","0101");
        adapter.addItem("손혜진","매화","0101");
        adapter.addItem("최지우","장미","0101");

        people.setAdapter(adapter);

        people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewChild item = (ListViewChild) parent.getItemAtPosition(position);

                String nameStr = item.getpName();
                String classStr = item.getpClass();
                String numStr = item.getpNum();
            }
        });
 */