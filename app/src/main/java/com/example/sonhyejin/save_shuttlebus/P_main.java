package com.example.sonhyejin.save_shuttlebus;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class P_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_main);

        ListView route = (ListView)findViewById(R.id.pBusList);
        Intent intent = getIntent(); // getIntent()로 받을 준비

        final String telNum = intent.getStringExtra("telNum"); // 유치원 번호 받기
        final String phone = intent.getStringExtra("phone"); // 전화번호 받기

        Toast.makeText(getApplicationContext(), "intent 받아오기 " + telNum + " " + phone, Toast.LENGTH_SHORT).show();

        FirebaseDatabase FD = FirebaseDatabase.getInstance();
        DatabaseReference DR = FD.getReference("Kindergarten");
        // kindergarten 밑에 있는 데이터로 접근

        //child 상태 불러오기 - > 1 : 승차 중 2: 하차 3 :유치원



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
                AlertDialog.Builder C_alertDialogBuilder = new AlertDialog.Builder(this);

                //제목 셋팅
                C_alertDialogBuilder.setTitle("결석 처리");

                //AlertDialog 셋팅
                C_alertDialogBuilder.setMessage("결석처리 할까요?").setCancelable(false).setPositiveButton("결석",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 결석처리함 (아이 데이터베이스에 접근, 데이터베이스 저장)

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
                T_alertDialogBuilder.setMessage("선생님 정보?").setCancelable(false).setPositiveButton("결석",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 결석처리함
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

                // 다이얼로그 보여주기
                T_alertDialog.show();
                break;

                default:
                    break;
        }
      }
}
