package com.example.sonhyejin.save_shuttlebus;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AskingTaking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layer_asking_taking);

        Log.v("ㄴㄴㄴㄴㄴㄴ", "다이얼로그");
/*        ActionBar ab = getActionBar();
        if(ab.isShowing())
            ab.hide();
*/
        final Intent intent = new Intent(getApplicationContext(), T_main.class);

        Button rideBtn = (Button) findViewById(R.id.ride_btn); // 승차 버튼
        Button takeBtn = (Button) findViewById(R.id.take_btn); // 하차 버튼


        rideBtn.setOnClickListener(new View.OnClickListener() { // 승차 버튼을 눌렀을 때
            @Override
            public void onClick(View view) {

                intent.putExtra("rt", 2); // 승차
                Log.v("ㄴㄴㄴㄴㄴㄴ", "승차버튼 누름");
                startActivity(intent); // 선택이 끝나면 T_main으로 돌아감
            }
        });

        takeBtn.setOnClickListener(new View.OnClickListener() { // 하차 버튼을 눌렀을 때
            @Override
            public void onClick(View view) {

                intent.putExtra("rt", 3); // 하차
                Log.v("ㄴㄴㄴㄴㄴㄴ", "하차버튼 누름");
                startActivity(intent); // 선택이 끝나면 T_main으로 돌아감

            }
        });
    }
}
