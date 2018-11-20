package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class H_Main_View extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_view);

        Intent intent=getIntent();
        final String autoNum=intent.getStringExtra("phone");

        Button hViewBus=(Button)findViewById(R.id.hViewBus);
        Button hViewTeach=(Button)findViewById(R.id.hViewTeach);
        Button hViewChild=(Button)findViewById(R.id.hViewChild);

        hViewBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),H_Main_View_bus.class);
                intent1.putExtra("phone",autoNum);
                startActivity(intent1);
            }
        });
        hViewChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),H_Main_View_child.class);
                intent1.putExtra("phone",autoNum);
                startActivity(intent1);
            }
        });
        hViewTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),H_Main_View_Teacher.class);
                intent1.putExtra("phone",autoNum);
                startActivity(intent1);
            }
        });
    }
}
