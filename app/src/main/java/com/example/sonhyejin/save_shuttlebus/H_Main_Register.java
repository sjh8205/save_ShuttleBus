package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class H_Main_Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_register);
        String autonum=null;
        Intent intent=getIntent();
        autonum=intent.getStringExtra("phone");
        Button hRegBus=(Button)findViewById(R.id.hRegBus);
        Button hRegTeach=(Button)findViewById(R.id.hRegTeach);
        Button hRegChild=(Button)findViewById(R.id.hRegChild);

        final String finalAutonum = autonum;
        hRegBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),H_Main_Register_bus.class);
                intent.putExtra("phone", finalAutonum);
                startActivity(intent);
            }
        });

        hRegTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),H_Main_Register_Teacher.class);
                intent.putExtra("phone", finalAutonum);
                startActivity(intent);
            }
        });

        hRegChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),H_Main_Register_child.class);
                intent.putExtra("phone", finalAutonum);
                startActivity(intent);
            }
        });

    }
}
