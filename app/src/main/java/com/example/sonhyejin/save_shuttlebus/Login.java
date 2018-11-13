package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
    SharedPreferences check = getSharedPreferences("login", Context.MODE_PRIVATE);
    SharedPreferences.Editor move = check.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        String autonum = null;
        TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            autonum = mgr.getLine1Number();
            autonum = autonum.replace("+82", "0");
            Toast.makeText(getApplicationContext(), autonum, Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "000000", Toast.LENGTH_SHORT).show();
        } // 디바이스 번호 받아오기

        // 빈 textview창 => autonum 띄워주기
        TextView num = (TextView) findViewById(R.id.getAutoNum);
        // autonum = "0000000";
        num.setText(autonum);

        // tel_num 글자 비우기
        final EditText tel_num = (EditText)findViewById(R.id.kinHeadNum);

        if(tel_num.didTouchFocusSelect() == true) {
            num.setText("");
        }

        // submit 클릭 시 정보 넘겨서 데이터베이스 조회하기

        Button submit = (Button)findViewById(R.id.loginSubmit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 섭밋 누르면

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

                final Intent intent;
                // intent.putExtra("phone", autonum); // final로 선언하면 어캄???????????

                FirebaseDatabase FD = FirebaseDatabase.getInstance();
                DatabaseReference DR = FD.getReference(tel_num.toString());
                // saveshuttlebus 라는 키를 가진 값들을 참조

                DR.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                        // 세셔버의 모든 자식들의 key값과 value 값들을 iterator로 참조

                        while(child.hasNext()) {
                            // 찾고자 하는 ID값은 key로 존재
                            //if(child.next().getKey().equals(autonum.toString())) {
                            //  Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_LONG).show();
                            //switch ( mode ){
                            // intent = new Intent(getApplicationContext(), class명) // 선택 모드에 따라 클래스 바뀜
                            // }
                            //break; // 찾자마자 와일문 나가뮤
                            if(status==1){
                                //                            intent = new Intent(getApplicationContext(), H_Main.class);
                            }else if(status ==2){
                                //                              intent = new Intent(getApplicationContext(), T_main.class);
                            }else if(status ==3){
//                                intent = new Intent(getApplicationContext(), P_main.class);
                            }
                        // }

//                            intent = new Intent(getApplicationContext(),H_Join.class); // final이라 변경 불가,,,하다는뎅,,,ㅠ 뭐냐구...
                            // 메인 창으로 넘어가기
                            // 휴대폰 번호는 계속 가지고 가기
                        }

//                        startActivity(intent);
                        //없으면 회원가입으로 (원장일 경우)
                        // 휴대폰 번호는 계속 가지고 가기
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