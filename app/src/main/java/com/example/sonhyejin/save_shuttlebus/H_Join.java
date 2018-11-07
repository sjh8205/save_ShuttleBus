package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class H_Join extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_join);

        // 제출
        // => 입력한 등록 번호, 이름 데이터ㅂㅔ이스에서 조회 후
        // 있으면 있다고 안내 -> 뒤로 가기
        // 없으면 넣기
        // => 폰 번호도 받아야 함 (로그인 창에서 이미 받아옴) (원장의 경우만 회원가입)
    }

}
