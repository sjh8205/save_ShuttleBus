package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.file.WatchEvent;

public class H_Join extends AppCompatActivity {
    DatabaseReference databaseReference;
    String KindName;
    String KindNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_join);

        Intent intent = getIntent();
        final String autonum = intent.getStringExtra("autonum"); // 전에서 얻었던 폰 번호 받아오기

        Button submit = (Button)findViewById(R.id.joinSubmit);
        final EditText joinResNum = (EditText)findViewById(R.id.joinResNum);
        final EditText joinName = (EditText)findViewById(R.id.joinName);

        databaseReference = FirebaseDatabase.getInstance().getReference("Kindergarten");
        submit.setOnClickListener(new View.OnClickListener() { // 제출
            @Override
            public void onClick(View v) {
                // => 입력한 등록 번호, 이름 데이터ㅂㅔ이스에서 조회 후

                // 없으면 넣기
                // => 폰 번호도 받아야 함 (로그인 창에서 이미 받아옴)
                KindNum = joinResNum.getText().toString();
                KindName = joinName.getText().toString();
                headJoin newHead = new headJoin(autonum,KindNum, KindName);
                databaseReference.child(KindNum).setValue(newHead);

                // 있으면 있다고 안내 -> 메인으로 ㄱㅏ기
                Intent intent1 = new Intent(H_Join.this,H_Main.class);
                intent1.putExtra("kindNum",KindNum);
                startActivity(intent1);
            }
        });
    }

}
