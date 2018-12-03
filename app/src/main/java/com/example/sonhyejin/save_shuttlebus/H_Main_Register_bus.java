package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class H_Main_Register_bus extends AppCompatActivity {
    //String Routine;
    String Station;
    String Time;
    String telNum;
    int indexCheck=0;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences data = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        telNum=data.getString("telnum","0");

        setContentView(R.layout.activity_h_main_register_bus);

        final EditText tStation=(EditText)findViewById(R.id.hRegBusRoute);
        final EditText tTime=(EditText)findViewById(R.id.hRegBusTime);

        Button btn=(Button)findViewById(R.id.hRegBusSubmit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference=FirebaseDatabase.getInstance().getReference()
                        .child("Kindergarten").child(telNum).child("bus");

                //Routine=tRoutine.getText().toString();
                Station=tStation.getText().toString();
                Time=tTime.getText().toString();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            String temp=data.getKey();
                            Log.v("regBus",temp);
                            int idx=temp.indexOf(" ");
                            String num = temp.substring(0,idx);
                            if(Time.equals(num)) {
                                indexCheck++;
                            }
                        }
                        if(indexCheck==0){
                            registerBus regB=new registerBus(Station,Time,false);
                            String str=Time+" "+Station;
                            databaseReference.child(str).setValue(regB);
                            Intent intent1=new Intent(getApplicationContext(),H_Main_Register.class);
                            startActivity(intent1);
                        }else{
                            Toast.makeText(getApplicationContext(),"Enter another index!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
