package com.example.sonhyejin.save_shuttlebus;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class H_Main_Register_child extends AppCompatActivity {
    boolean busRideStatus;
    EditText station;

    Button busyes;
    Button busno;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_register_child);

        busyes = (Button)findViewById(R.id.busYes);
        busno = (Button)findViewById(R.id.busNo);

        busyes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = true;
                if(status==true){
                    busyes.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
                    busno.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                }
            }
        });

        busno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                status = false;
                if(status==false){
                    busyes.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ChooseButton));
                    busno.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.ClickedChooseButton));
                }
            }
        });

        EditText childName = (EditText) findViewById(R.id.hRegChildName);
        EditText childClass = (EditText) findViewById(R.id.hRegChildClass);
        EditText phoneNum = (EditText) findViewById(R.id.hRegChildNum);

        //final RadioGroup busRide=(RadioGroup)findViewById(R.id.busStatusGroup);
        //final LinearLayout layout=(LinearLayout)findViewById(R.id.busStation);
/*
        RadioGroup.OnCheckedChangeListener radiogroupChange=new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,@IdRes int checkedId) {
                if(checkedId==R.id.busYes){
                    busRideStatus=true;
                    layout.setVisibility(View.VISIBLE);
                    station = (EditText) findViewById(R.id.childBusStation);
                }
                else{
                    busRideStatus=false;
                }
            }
        };

        */

    }
}
