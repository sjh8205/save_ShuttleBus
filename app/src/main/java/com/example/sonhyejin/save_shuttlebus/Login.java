package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class Login extends AppCompatActivity {


    int status = 0; //head, teacher, parent를 구분하는 status --> 이 값으로 role 구분해서 값 넘기면 됨
    Button loginH;
    Button loginT;
    Button loginP;
    String autonum = null;
    Intent intent; // main으로 갈 때 쓰는 인텐트
    Intent H_intent ; // join으로 갈 때 쓰는 인텐트(원장만 씀)
    SharedPreferences check;
    SharedPreferences.Editor move ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        check = getSharedPreferences("login", Context.MODE_PRIVATE);
        move = check.edit();
        
        loginH = (Button)findViewById(R.id.loginCheckH);
        loginT = (Button)findViewById(R.id.loginCheckT);
        loginP = (Button)findViewById(R.id.loginCheckP);

        loginH.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = 1;
                loginH.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
                loginT.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                loginP.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
            }
        });

        loginT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = 2;
                loginH.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                loginT.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
                loginP.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
            }
        });

        loginP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = 3;
                loginH.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                loginT.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                loginP.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
            }
        });
        TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, android.Manifest.permission.READ_PHONE_NUMBERS)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, android.Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.v("버튼","세가지 모두 중 하나 클릭됨");
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                if(mgr.getLine1Number() !=null){
                    autonum = mgr.getLine1Number();
                }
                else {
                    if(mgr.getSimSerialNumber() != null){
                        autonum = mgr.getSimSerialNumber();
                    }
                }
                autonum = autonum.replace("+82", "0");
                Toast.makeText(getApplicationContext(), autonum, Toast.LENGTH_SHORT).show();
            }
           // Toast.makeText(getApplicationContext(), "트라이임다ㅎㅇ", Toast.LENGTH_SHORT).show();

        } catch(Exception e) {
            //Toast.makeText(getApplicationContext(), "오류ㅠ000000으로 저장합니다", Toast.LENGTH_SHORT).show();
            autonum = "0000000";
        } // 디바이스 번호 받아오기

        // 빈 textview창 => autonum 띄워주기
        TextView num = (TextView) findViewById(R.id.getAutoNum);
        // autonum = "0000000";
        num.setText(autonum);
        Toast.makeText(this,autonum,Toast.LENGTH_SHORT).show();
        // tel_num 글자 비우기
        final EditText tel_num = (EditText)findViewById(R.id.kinHeadNum);
        final String telNum=tel_num.getText().toString();

        if(tel_num.didTouchFocusSelect() == true) {
            num.setText("");
        }

        // submit 클릭 시 정보 넘겨서 데이터베이스 조회하기

        Button submit = (Button)findViewById(R.id.loginSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 섭밋 누르면
                Log.v("qjxms","qjxms");
                if(status==1){
                    move.putInt("FirstorNot",1);
                    move.commit();
                }else if(status ==2){
                    move.putInt("FirstorNot",3);
                    move.commit();
                }else if(status ==3){
                    move.putInt("FirstorNot",2);
                    move.commit();
                }

                Log.v("ddd","ddd");
                FirebaseDatabase FD = FirebaseDatabase.getInstance();
                DatabaseReference DR = FD.getReference("Kindergarten");
                //파이어베이스에서 Kindergarten 밑에 있는 값들을 접근하기 위한 변수 선언

                DR.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.v("sja","djrkkk");
                       for(DataSnapshot data: dataSnapshot.getChildren()){
                           //Kindergarten 밑에 있는 값들 확인 유치원 번호에 해당하는 값들이 있는만큼 확인
                           Log.v("sja","for");
                           if(!data.hasChild(telNum)){//내가 입력한 유치원 번호가 디비에 없을 경우
                               Log.v("sja","ifno");
                               if(status==1){//원장일 경우
                                   Log.v("sja","if1");
                                   H_intent = new Intent(getApplicationContext(),H_Join.class);
                                   //원장일 경우 유치원을 새로 등록해야하는 경우 이므로 유치원을 등록할 수 있는 화면으로 전환
                                   H_intent.putExtra("phone", autonum); // 번호 전달
                                   startActivity(H_intent);
                               }
                               else{
                                   //원장 외에 다른 사람일 경우 번호를 잘못 입력했거나 유치원이 존재하지 않은 경우
                                   Toast.makeText(getApplicationContext(),"Wrong kindergarten phoneNum",Toast.LENGTH_SHORT);
                               }
                           }
                           else{//유치원이 디비에 존재할 경우
                               if(status==1){//원장일 경우
                                   String Num=dataSnapshot.child(telNum).child("headNum").getValue().toString();
                                   //디비에서 유치원 원장의 번호를 가져옴
                                   if(autonum.equals(Num)){//디비에서 가져온 번호와 원장의 핸드폰 번호가 같은지 확인
                                       Intent intent1=new Intent(getApplicationContext(),H_Main.class);
                                       intent1.putExtra("phone",autonum);
                                       intent1.putExtra("telNum",telNum);
                                   }
                                   else{//아닐 경우 외부인이므로 앱 사용 불가능
                                       Toast.makeText(getApplicationContext(),"Not accepted!",Toast.LENGTH_SHORT).show();
                                   }
                               } else if(status==2){//선생님인 경우
                                   for(DataSnapshot data1 : dataSnapshot.child(telNum).child("Teacher").getChildren()){
                                       //디비에서 해당하는 유치원의 Teacher 밑에있는 선생님의 번호를 전부 볼거임
                                       if(!data1.hasChild(autonum)){//전부 보았는데 해당하는 번호가 없는 경우 외부인
                                           Toast.makeText(getApplicationContext(),"Not accepted!",Toast.LENGTH_SHORT).show();
                                       }
                                       else{//해당하는 선생님이 존재하므로 선생님 메인화면으로 넘길거임
                                           Intent intent1=new Intent(getApplicationContext(),T_main.class);
                                           intent1.putExtra("phone",autonum);
                                           intent1.putExtra("telNum",telNum);
                                           startActivity(intent1);
                                       }
                                   }
                               }else if(status==3){//학부모일 경우
                                   for(DataSnapshot data1 : dataSnapshot.child(telNum).child("child").getChildren()){
                                       //디비에서 해당하는 유치원의 child 밑에 있는 학부모의 번호를 다 확인할 거임
                                       if(!data1.hasChild(autonum)){//해당하는 번호가 없는 경우이므로 외부인
                                           Toast.makeText(getApplicationContext(),"Not accepted!",Toast.LENGTH_SHORT).show();
                                       }
                                       else{//해당하는 번호가 있으므로 학부모 메인으로 화면 전환할거임
                                           Intent intent2=new Intent(getApplicationContext(),P_main.class);
                                           intent2.putExtra("phone",autonum);
                                           intent2.putExtra("telNum",telNum);
                                           startActivity(intent2);
                                       }
                                   }
                               }
                           }
                       }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }


}
//권한분리 명확히 한 이유와 장점을 발표 때 언급
//여러 기능들이 있으니까, '승하차안전'쪽에만 치우치지 않고 더 포괄적으로 잡기