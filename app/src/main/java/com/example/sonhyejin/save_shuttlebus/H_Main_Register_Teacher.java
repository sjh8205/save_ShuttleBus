package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class H_Main_Register_Teacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_register_teacher);
        Intent intent=getIntent();
        String autoNum=intent.getStringExtra("phone");

        EditText hRegTeachName=(EditText)findViewById(R.id.hRegTeachName);
        String teachName=hRegTeachName.getText().toString();
        EditText hRegTeachClass=(EditText)findViewById(R.id.hRegTeachClass);
        String teachClass=hRegTeachClass.getText().toString();
        EditText hRegTeachNum=(EditText)findViewById(R.id.hRegTeachNum);
        final String teachNum=hRegTeachNum.getText().toString();

        Button hRegTeachSubmit=(Button)findViewById(R.id.hRegTeachSubmit);

        final DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference(autoNum);

        final registerTeacher registerTeacher=new registerTeacher(teachName,teachClass,teachNum);

        hRegTeachSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),H_Main.class);
                databaseReference.child(teachNum).setValue(registerTeacher);
                startActivity(intent1);
            }
        });


    }
}
