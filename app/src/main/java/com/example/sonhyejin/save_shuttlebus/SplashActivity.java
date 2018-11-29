package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;

public class SplashActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        String seedata = data.getString("telnum","0");


        SharedPreferences check = getSharedPreferences("login", Context.MODE_PRIVATE);
        int checked = check.getInt("FirstorNot",0);

        /*
        if(checked==1){ //사용자가 사전에 '원장' 자격으로 로그인 한 경우
            startActivity(new Intent(this,H_Main.class));
            finish();
        }else if(checked==2){//사용자가 사전에 '부모' 자격으로 로그인 한 경우
            startActivity(new Intent(this,P_main.class));
            finish();
        }else if(checked==3){//사용자가 사전에 '교사' 자격으로 로그인 한 경우
            startActivity(new Intent(this,T_main.class));
            finish();
        }else {//그 외의 경우
            startActivity(new Intent(this,Login.class));
            finish();
        }

        */startActivity(new Intent(this,T_main_Totalchild.class));
        finish();

    }
}
