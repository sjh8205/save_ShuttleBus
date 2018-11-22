package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class H_Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main);
        final Intent intent;
        final String kindNum;
        intent=getIntent();
        kindNum=intent.getStringExtra("kindNum");

        Button hView=(Button)findViewById(R.id.hView);
        Button hReg=(Button)findViewById(R.id.hReg);

        hView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),H_Main_View.class);
                intent1.putExtra("phone",kindNum);
                startActivity(intent1);
            }
        });

        hReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),H_Main_Register.class);
                intent1.putExtra("phone",kindNum);
                startActivity(intent1);
            }
        });


    }
}
