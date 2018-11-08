package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
        } catch(Exception e) { } // 디바이스 번호 받아오기

        // 빈 textview창 => autonum 띄워주기
        TextView num = (TextView) findViewById(R.id.getAutoNum);
        // autonum = "0000000";
        num.setText(autonum);

        // tel_num 글자 비우기
        EditText tel_num = (EditText)findViewById(R.id.kinHeadNum);

        if(tel_num.didTouchFocusSelect() == true) {
            num.setText("");
        }

        // submit 클릭 시 정보 넘겨서 데이터베이스 조회하기

        Button submit = (Button)findViewById(R.id.loginSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase FD = FirebaseDatabase.getInstance();
                DatabaseReference DR = FD.getReference("saveshuttlebus");
                // saveshuttlebus 라는 키를 가진 값들을 참조

                DR.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                        // 세셔버의 모든 자식들의 key값과 value 값들을 iterator로 참조

                        while(child.hasNext()) {
                            // 찾고자 하는 ID값은 key로 존재
                            //if(child.next().getKey().equals(autonum.toString()))
                              //  Toast.makeText(getApplicationContext(), "로그인", Toast.LENGTH_LONG).show();

                            // 메인 창으로 넘어가기
                        }
                        //없으면 회원가입으로 (원장일 경우)
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }
}
