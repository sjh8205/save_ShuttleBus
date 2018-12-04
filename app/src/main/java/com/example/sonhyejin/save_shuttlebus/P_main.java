package com.example.sonhyejin.save_shuttlebus;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

    String kindNum;
    String phone;

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

        ListView route = (ListView)findViewById(R.id.pBusList);

        FirebaseDatabase FD = FirebaseDatabase.getInstance();

        final DatabaseReference T_DR = FD.getReference("Kindergarten");
        // kindergarten 밑에 있는 데이터로 접근

        T_DR.child(kindNum).addValueEventListener(new ValueEventListener() { // 오늘의 지도교사 탐색 --> 어떻게 하징,,ㅠ 분별방법 아직 xxxx
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.child("Teacher").getChildren()) { // 다음 탐색 -> 인데 티쳐가 없어서 아무것도 못받음..
                    Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "in dc");
                   if (data.child("todayTeacher").getValue(Boolean.class) == true) { // 만약 오늘의 지도교사이면
                        T_num = data.getKey(); // 선생님 전화번호 받아오기
                    Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", T_num);
                        T_name = data.child("name").getValue(String.class); // 선생님 이름 받아오기
                    Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", T_name);
                        T_img = data.child("imgPath").getValue(String.class);
                    Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", T_img);

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

                C_DR.addValueEventListener(new ValueEventListener() {
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
                        String message = " 헤헷 ";
                        Toast.makeText(getApplicationContext(), "message 생성", Toast.LENGTH_SHORT).show();

                        switch (C_status) { // 스위치는 왜 못 들어가징
                            case 2 :
                                message = "승차 중이에요.";
                                break;
                            case 3 :
                                message = "하차했어요.";
                                break;
                            case 0 :
                                message = "유치원에 있어요.";
                                break;
                            case 1 :
                                message = "결석했어요.";
                                break;
                            default:
                                message = "에러;";
                                break;
                        }

                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", message);

/*                        final Dialog d = new Dialog(getApplicationContext());
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "다이얼로그 생성");
                        d.setTitle("우리 아이는 지금\n");
*/
                        final AlertDialog.Builder C_alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "다이얼로그 빌더 생성");

                        //제목 셋팅
                        C_alertDialogBuilder.setTitle("우리 아이는 지금\n");
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "ㅈㅔ목 세팅");
                        C_alertDialogBuilder.setMessage(message);
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "메시지 세팅");
//////////////////////////여기까지 되어용
                        C_alertDialogBuilder.setPositiveButton("결석", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "결석 버튼으로 들어옴");
                                dialogInterface.dismiss(); // 닫기
                            }
                        });

                        C_alertDialogBuilder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "확인 버튼");
                                dialogInterface.dismiss(); // 닫기
                            }
                        });

                        // 다이얼로그 보여주기
                        C_alertDialogBuilder.show();
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "다이얼로그 보여주기");

                        /*                        //AlertDialog 셋팅
                        C_alertDialogBuilder.setMessage(message).setCancelable(false).setPositiveButton("결석",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // 결석처리함 (아이 데이터베이스에 접근, 데이터베이스 저장)
                                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "결석처리합닏");

                                        C_DR.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                C_DR.child(phone).child("childOnBus").setValue(1); // 결석값으로 세팅
                                                Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "결석값 세팅");
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
                                Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "다이얼로그 닫기");
                            }
                        });

                        // 다이얼로그 생성
                        AlertDialog C_alertDialog = C_alertDialogBuilder.create();
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "다이얼로그 생성(밑");

                        // 다이얼로그 보여주기
                        C_alertDialog.show();
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "다이얼로그 보여주기");
*/
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


/*                AlertDialog.Builder C_alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());

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
                });*/

            }
        });

      }

/*      public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pViewChild:
                Log.v("pViewChild",phone);
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
                Log.v("pViewTeach",phone);
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
      }*/

}
