package com.example.sonhyejin.save_shuttlebus;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class H_Main_Register_child extends AppCompatActivity {
    boolean busRideStatus;
    EditText station;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_register_child);



        EditText childName = (EditText) findViewById(R.id.childName);
        EditText childClass = (EditText) findViewById(R.id.childClass);
        EditText phoneNum = (EditText) findViewById(R.id.parentPhoneNum);

        final RadioGroup busRide=(RadioGroup)findViewById(R.id.busStatusGroup);
        final LinearLayout layout=(LinearLayout)findViewById(R.id.busStation);

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

    }
}
