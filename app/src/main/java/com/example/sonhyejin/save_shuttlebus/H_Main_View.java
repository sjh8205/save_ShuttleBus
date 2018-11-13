package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class H_Main_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_view);

        Intent intent=getIntent();
        String autoNum=intent.getStringExtra("phone");

        Button hViewBus=(Button)findViewById(R.id.hViewBus);
        Button hViewTeach=(Button)findViewById(R.id.hViewTeach);
        Button hViewChild=(Button)findViewById(R.id.hViewChild);
    }
}
