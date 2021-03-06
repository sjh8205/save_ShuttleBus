package com.example.sonhyejin.save_shuttlebus;

import android.app.Dialog;
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
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class P_main extends AppCompatActivity {

    DatabaseReference C_DR;
    String C_name;
    int C_status;

    String T_num;
    String T_name;
    String T_img;
    String T_class;

    String kindNum;
    String phone;

    Intent intent;

    ListView route;
    AdapterRoute Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "in p");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_main);

        SharedPreferences P_data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        kindNum = P_data.getString("telnum","0");
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", kindNum);
        phone = P_data.getString("mynum", "0");
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", phone);

        Adapter = new AdapterRoute();

        route = (ListView)findViewById(R.id.pBusList);

        final FirebaseDatabase FD = FirebaseDatabase.getInstance();

        final DatabaseReference T_DR = FD.getReference("Kindergarten");
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "DR");

        T_DR.child(kindNum).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.child("bus").getChildren()){
                    String temp=data.getKey();
                    Boolean station = data.child("busishere").getValue(Boolean.class);

                    Log.v("telNumb",temp);
                    int idx=temp.indexOf(" ");
                    String num = temp.substring(0,idx);
                    Log.v("bus"," "+num);
                    String nam = temp.substring(idx+1);
                    Log.v("bus"," "+nam);

                    if(station == true){
                        Adapter.addItem(ContextCompat.getDrawable(P_main.this,R.drawable.bus),ContextCompat.getDrawable(P_main.this,R.drawable.busstop),nam);
                    }else{
                        Adapter.addItem(ContextCompat.getDrawable(P_main.this,R.drawable.blank),ContextCompat.getDrawable(P_main.this,R.drawable.busstop),nam);
                    }


                }
                route.setAdapter(Adapter);

                for(DataSnapshot data:dataSnapshot.child("Teacher").getChildren()) {
                    Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "in for");
                    if (data.child("todayTeacher").getValue(Boolean.class)) { // 만약 오늘의 지도교사이면
                        T_num = data.getKey(); // 선생님 전화번호 받아오기
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", T_num);
                        T_name = data.child("name").getValue(String.class); // 선생님 이름 받아오기
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", T_name);
                        T_img = data.child("imgPath").getValue().toString();
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", T_img);
                        T_class=data.child("tClass").getValue(String.class);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewRoute item = (ListViewRoute) parent.getItemAtPosition(position);

                String stStr = item.getstname();
                Drawable stimg = item.getstimg();
                Drawable busimg = item.getbusimg();
                Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "리스트뷰 추가 완료");
            }
        });

        Button pViewChild = (Button)findViewById(R.id.pViewChild);
        Button pViewTeach = (Button)findViewById(R.id.pViewTeach);
 // 밑에서 id로 받음
//        pViewChild.setOnClickListener(this);

        C_DR = FD.getReference("Kindergarten").child(kindNum).child("child"); // child 데이터베이스 리퍼런스

        pViewChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "C클릭", Toast.LENGTH_SHORT).show();

                C_DR.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "in c_dc");
                        for(DataSnapshot data: dataSnapshot.getChildren()) {
                            Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "in c_for");
                            String data_phone = data.getKey();
                            Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", data_phone);
                            Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", phone);

                            if(data_phone.equals(phone)) { // 기본키가 폰 번호랑 같으면
                                Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "if문!");
                                C_name = data.child("childName").getValue(String.class); // 이름 가져오기
                                Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", C_name);
                                C_status = data.child("childOnBus").getValue(int.class); // 현재 가져오기
                                Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", String.valueOf(C_status));
                                break; // 값 받고 나간다
                            }
                        }

                        //                        Toast.makeText(getApplicationContext(), "message 생성", Toast.LENGTH_SHORT).show();

                        String message;

                        switch (C_status) { // 상태에 따라 메시지 내용 구성
                            case 2 :
                                message = "On Riding";
                                break;
                            case 3 :
                                message = "Taking";
                                break;
                            case 0 :
                                message = "in Kindergarten";
                                break;
                            case 1 :
                                message = "Absent";
                                break;
                            default:
                                message = "Error;";
                                break;
                        }

                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", message);

                        C_alert(message);
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "C_alert() 실행");
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        pViewTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Teacherinfo","click"+T_name+T_num+T_img);
                P_alert();
            }
        });
      }


    private void C_alert(final String message)
    {
        new AlertDialog.Builder(this)
                // 색상을 타이틀에 세팅한다.
                .setTitle("Now your child is")
                // 설명을 메시지 부분에 세팅한다.
                .setMessage(message)
                // 취소를 못하도록 막는다.
                .setCancelable(true)
                // 확인 버튼을 만든다.
                .setNegativeButton("Absent", new DialogInterface.OnClickListener()
                {
                    /* (non-Javadoc)
                     * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
                     */
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "결석버튼");
//                        boolean check=false;
                        DatabaseReference dr=FirebaseDatabase.getInstance().getReference("Kindergarten");
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "킨더같ㄴ");
                        Map<String,Object> taskMap=new HashMap<String, Object>();
                        taskMap.put("childOnBus",1);
                        dr.child(kindNum).child("child").child(phone).updateChildren(taskMap);
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "값셑.ㅇ");
                        //                        check=true;
                        dialog.dismiss();
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "닫습니다");
                        Toast.makeText(P_main.this, "Notice Absent", Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "닫습니다");
            }
        })
                .show();
//        return check;
    }

    private void P_alert()
    {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.layout_dial_teach);
        dialog.setTitle("Today Teacher");

        Log.v("Teacherinfo","indialog"+T_name+T_num+T_img);

        TextView textView=(TextView)dialog.findViewById(R.id.T_name);
        textView.setText(T_name);

        TextView  textView1=(TextView)dialog.findViewById(R.id.T_num);
        textView1.setText(T_num);

        ImageView imageView=(ImageView)dialog.findViewById(R.id.T_img);
        Glide.with(getApplicationContext()).load(T_img).into(imageView);

        Button btn=(Button)dialog.findViewById(R.id.close_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}