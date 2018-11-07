package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        String tel_num = null;
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
            tel_num = mgr.getLine1Number();
            tel_num = tel_num.replace("+82", "0");
            Toast.makeText(getApplicationContext(), tel_num, Toast.LENGTH_SHORT).show();
        } catch(Exception e) { } // 디바이스 번호 받아오기

        // 에딧 텍스트 내 텍스트 => tel_num으로 바꾸고
        // 텍스트 수정 못하게 하기

        // 버튼 색깔 바꾸기

        // 정보 넘겨서 데이터베이스 조회하기

        // 데이터베이스에 존재하면 -> 메인 화면으로
        // 아니면 -> 로그인 불가. -> 회원가입으로 가거나 하기


    }
}
