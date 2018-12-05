package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class H_Main_View_Teacher extends AppCompatActivity {

    String telNum;
    ListView people;
    AdapterTeach adapter;
    boolean check=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_view_teacher);

        people = (ListView)findViewById(R.id.hTeachList);
        adapter = new AdapterTeach();

        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        telNum = data.getString("telnum","0");

        FirebaseDatabase FD = FirebaseDatabase.getInstance();
        final DatabaseReference DR = FD.getReference("Kindergarten");

        Log.v("telNum from SP",telNum);


        DR.child(telNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                Log.v("before for","a");
                int i = 0;
                for(DataSnapshot data: dataSnapshot.child("Teacher").getChildren()){
                    String nam = data.child("name").getValue(String.class);
                    String cla = data.child("tClass").getValue(String.class);
                    String num = data.child("phone").getValue().toString();
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
                        ListViewTeach teacher=(ListViewTeach) adapter.getItem(position);
                        final String tempP=String.valueOf(teacher.gettNum());
                        //Log.v("listClick","*"+teacher.gettNum());
                        alert(tempP,telNum);
                        Log.v("listClick","1.(f)"+check);


                        Log.v("listClick","finish(itemClick)");
                    }

                });


               // Log.v("print", "affor");

                Log.v("listClick","onDataChange(itemClick)");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
    private void alert(final String phone, final String telNum)
    {

        // 체인형으로 메소드를 사용한다.
        new AlertDialog.Builder(this)
                // 색상을 타이틀에 세팅한다.
                .setTitle("Today Teacher")
                // 설명을 메시지 부분에 세팅한다.
                .setMessage("Do you want to register today teacher?")
                // 취소를 못하도록 막는다.
                .setCancelable(true)
                // 확인 버튼을 만든다.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    /* (non-Javadoc)
                     * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
                     */
                    public void onClick(DialogInterface dialog, int which)
                    {
                        check=true;
                        final DatabaseReference df=FirebaseDatabase.getInstance().getReference("Kindergarten").child(telNum).child("Teacher");
                        df.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                String phonet=dataSnapshot1.getKey().toString();
                                //String sphone
                                    Log.v("todayteacher",phonet);
                                    Log.v("phoneC",phonet);
                                    if(!phone.equals(phonet)){
                                        Map<String,Object> taskMap=new HashMap<String, Object>();
                                        taskMap.put("todayTeacher",false);
                                        df.child(phonet).updateChildren(taskMap);
                                        Log.v("todayteacher",phonet+"false");
                                        }else{
                                    Map<String,Object> taskMap=new HashMap<String, Object>();
                                    taskMap.put("todayTeacher",true);
                                    df.child(phonet).updateChildren(taskMap);
                                    Log.v("todayteacher",phonet+"true");
                                }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        check=true;
                        Log.v("listClick","2.(true)"+check);
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
