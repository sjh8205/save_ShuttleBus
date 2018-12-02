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
import android.widget.ListView;
import android.widget.Toast;

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

        Log.v("telNum from SP",telNum);

        DR.child(telNum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.v("before for","a");
                int i = 0;
                for(DataSnapshot data: dataSnapshot.child("Teacher").getChildren()){
                    String nam = data.child("name").getValue(String.class);
                    String cla = data.child("tClass").getValue(String.class);
                    String num = data.child("phone").getValue(String.class);
                    String pic=data.child("imgPath").getValue().toString();

                    Log.v("i","name");
                    Log.v("i","tClass");
                    Log.v("i","phone");

                    Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();

                    i++;

                    adapter.addItem(Uri.parse(pic),nam,cla,num);
                }
                people.setAdapter(adapter);

                Toast.makeText(getApplicationContext(), "리스트뷰 구성완료", Toast.LENGTH_SHORT).show();

                people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                        ListViewTeach item = (ListViewTeach) parent.getItemAtPosition(position);

                        Uri img = item.getimg();
                        String nameStr = item.gettName();
                        String classStr = item.gettClass();
                        String numStr = item.gettNum();
                        Toast.makeText(getApplicationContext(), "리스트뷰 구성완료22", Toast.LENGTH_SHORT).show();

/*                        int j = 0; // 포문 얼마나 돌았는지 세어주는 변수 (선택한 position 수만큼 돌릴 것임)
                        Log.v("클릭", String.valueOf(position));

                        for(DataSnapshot data: dataSnapshot.child("Teacher").getChildren()) {
                            Log.v("포문입니다,,", String.valueOf(j) + " =?= "+ String.valueOf(position));
                            if(j == position) { // j와 pos가 같으면
                                boolean today;
                                // 선생님 데이터베이스에서 today값 가져오기
                                today = data.child("Teacher").child("phone").child("Today").getValue(boolean.class);
                                Log.v("포문입니다,,", String.valueOf(today));

                                DR.child("Tel_num").child("Teacher").child("phone").child("Today").setValue(!today);

                                if(today)
                                    Toast.makeText(getApplicationContext(), "지도교사가 해제되었습니다.", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(), "지도교사가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                            } // 선택된 교사 오늘의 지도교사로 세팅 또는 세팅 풀기 -> 토스트 말고 따로 보여줄 방법은 아직 없음

                            j++; // j ++ 해주구
                        }
*/
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
