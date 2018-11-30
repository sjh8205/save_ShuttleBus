package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class H_Main_Register_bus extends AppCompatActivity {
    String Routine;
    String Station;
    int Time;
    String telNum;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent =getIntent();
        telNum=intent.getStringExtra("telNum");

        setContentView(R.layout.activity_h_main_register_bus);
        final EditText tRoutine=(EditText)findViewById(R.id.hRegBusName);
        final EditText tStation=(EditText)findViewById(R.id.hRegBusRoute);
        final EditText tTime=(EditText)findViewById(R.id.hRegBusTime);
        Button btn=(Button)findViewById(R.id.hRegBusSubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference=FirebaseDatabase.getInstance().getReference()
                        .child("Kindergarten").child(telNum).child("busRoot");
                Routine=tRoutine.getText().toString();
                Station=tStation.toString();
                String  str=tTime.getText().toString();
                Time=Integer.parseInt(str);
                registerBus regB=new registerBus(Routine,Station,Time);
                Intent intent1=new Intent(getApplicationContext(),H_Main_Register.class);
                startActivity(intent1);
            }
        });
    }
}
