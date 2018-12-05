package com.example.sonhyejin.save_shuttlebus;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class DialogueTeach extends AppCompatActivity {

    String T_name;
    String T_num;
    String T_img;

/*    ImageView img = (ImageView) findViewById(R.id.T_img);
    TextView NAME = (TextView) findViewById(R.id.T_name);
    TextView NUM = (TextView) findViewById(R.id.T_num);
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialogue_teach);
//        View view = (View) getLayoutInflater().inflate(R.layout.layout_dialogue_teach, null);

    }

    public void T_info(String kindNum) {

        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "in T_info");

        final FirebaseDatabase FD = FirebaseDatabase.getInstance();
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "FD");
        final DatabaseReference DR = FD.getReference("Kindergarten");
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "DR");

        DR.child(kindNum).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.child("Teacher").getChildren()) {
                    Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "in for");
                    if (data.child("todayTeacher").getValue(Boolean.class) == true) { // 만약 오늘의 지도교사이면
                        T_num = data.getKey(); // 선생님 전화번호 받아오기
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", T_num);
                        T_name = data.child("name").getValue(String.class); // 선생님 이름 받아오기
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", T_name);
                        T_img = data.child("imgPath").getValue(String.class);
                        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", T_img);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void infoSetting(String timg, String name, String num) {

        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "info_setting");

        View view = (View) getLayoutInflater().inflate(R.layout.layout_dialogue_teach, null);
        ImageView img = (ImageView)  view.findViewById(R.id.T_img);
        TextView NAME = (TextView) view.findViewById(R.id.T_name);
        TextView NUM = (TextView) view.findViewById(R.id.T_num);


        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "before uri");
        img.setImageURI(Uri.parse(timg));
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "uri"); // 여길 왜 못넘지...
        NAME.setText("NAME : " + name);
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "name");
        NUM.setText("PHONE NUM : " + num);
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "num");

    }
}
