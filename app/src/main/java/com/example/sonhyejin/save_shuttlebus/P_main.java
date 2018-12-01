package com.example.sonhyejin.save_shuttlebus;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class P_main extends AppCompatActivity {

    DatabaseReference C_DR;
    String C_name;
    int C_status;

    String T_num;
    String T_name;
    String T_img;

    Intent intent = getIntent(); // getIntent()로 받을 준비
    final String phone = intent.getStringExtra("phone"); // 전화번호 받기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_main);

        ListView route = (ListView)findViewById(R.id.pBusList);

        final String telNum = intent.getStringExtra("telNum"); // 유치원 번호 받기
        Log.v("tel_num","왔어용");
        Log.v("autonum",phone);

        Toast.makeText(getApplicationContext(), "intent 받아오기 " + telNum + " " + phone, Toast.LENGTH_SHORT).show();

        FirebaseDatabase FD = FirebaseDatabase.getInstance();

        C_DR = FD.getReference("Kindergarten").child(telNum).child("child").child(phone);
        Log.v("C_DR","왔어용");

        // child 상태 불러오기 - > 1 : 승차 중 / 2 : 하차 / 3 : 유치원 / 4 : 결석
        Log.v("name","왔어용");

        final DatabaseReference T_DR = FD.getReference("Kindergarten").child(telNum);
//        Log.v("T_DR","왔어용");
        // kindergarten 밑에 있는 데이터로 접근

        T_DR.addValueEventListener(new ValueEventListener() { // 오늘의 지도교사 탐색 --> 어떻게 하징,,ㅠ 분별방법 아직 xxxx
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()) { // 다음 탐색
                    if (data.child("phone").child("Today").getValue() == 1) { // 만약 오늘의 지도교사이면
                        T_num = T_DR.child("phone").getKey().toString(); // 선생님 전화번호 받아오기
                        T_name = data.child("phone").child("name").getValue(String.class); // 선생님 이름 받아오기
                        T_img = data.child("phone").child("img").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        AdapterRoute Adapter = new AdapterRoute();

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

        Button pViewChild = (Button)findViewById(R.id.pViewChild);
        Button pViewTeach = (Button)findViewById(R.id.pViewTeach);

//        pViewChild.setOnClickListener(this);

      }

      public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pViewChild:
                C_DR.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()) {
                            if(C_DR.getKey() == phone) { // 기본키가 폰 번호랑 같으면
                                C_name = data.child("Name").getValue(String.class); // 이름 가져오기
                                C_status = data.child("status").getValue(int.class); // 현재 가져오기
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                String message = " ";
                switch (C_status) {
                    case 1 :
                        message = "승차 중이에요.";
                        break;
                    case 2 :
                        message = "하차했어요.";
                        break;
                    case 3 :
                        message = "유치원에 있어요.";
                        break;
                    case 0 :
                        message = "결석했어요.";
                        break;
                    default:
                        message = "에러;";
                        break;
                }

                AlertDialog.Builder C_alertDialogBuilder = new AlertDialog.Builder(this);

                //제목 셋팅
                C_alertDialogBuilder.setTitle("우리 아이는 지금\n");

                //AlertDialog 셋팅
                C_alertDialogBuilder.setMessage(message).setCancelable(false).setPositiveButton("결석",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 결석처리함 (아이 데이터베이스에 접근, 데이터베이스 저장)

                                C_DR.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        C_DR.child("status").setValue(4); // 결석값으로 세팅
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                            }
                        }).setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 다이얼로그를 취소함
                        dialogInterface.cancel();
                    }
                });

                // 다이얼로그 생성
                AlertDialog C_alertDialog = C_alertDialogBuilder.create();

                // 다이얼로그 보여주기
                C_alertDialog.show();
                break;

            case R.id.pViewTeach:
                AlertDialog.Builder T_alertDialogBuilder = new AlertDialog.Builder(this);

                //제목 셋팅
                T_alertDialogBuilder.setTitle("오늘의 선생님");

                //AlertDialog 셋팅
                T_alertDialogBuilder.setMessage(T_name +" 선생님\n" + "전화번호 : " +T_num).setCancelable(false).setPositiveButton("결석",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 다이얼로그를 취소함
                        dialogInterface.cancel();
                    }
                });

                // 다이얼로그 생성
                AlertDialog T_alertDialog = T_alertDialogBuilder.create();

                // 다이얼로그에 사진 넣기
                ImageView iv = (ImageView)T_alertDialog.findViewById(R.id.image); // 다이얼로그 내 이미지뷰 선언
//                iv.setImageResource(); // 파이어베이스에서 이미지 불러오기

                // 다이얼로그 보여주기
                T_alertDialog.show();
                break;

                default:
                    break;
        }
      }
}
