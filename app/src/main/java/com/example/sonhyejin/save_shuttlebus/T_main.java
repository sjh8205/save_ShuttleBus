package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class T_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main);

        final ListView route = (ListView)findViewById(R.id.tBusList);

        AdapterRoute Adapter = new AdapterRoute();
        Button seeTotal = (Button)findViewById(R.id.tViewTotal) ;

        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산1단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산2단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산3단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산4단지");

        route.setAdapter(Adapter);

        route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewRoute item = (ListViewRoute) parent.getItemAtPosition(position);
                String row = (String) route.getItemAtPosition(position);

                String stStr = item.getstname();
                Drawable img = item.getimg();

                Toast.makeText(getApplicationContext(), "You selected : " + row, Toast.LENGTH_SHORT).show(); // 클릭한 해당 위치 받아오기
                //받은 위치의 버스 이미지만 보이게 하고 없애기
//                route.getItemAtPosition(row).getimg

            }
        });

        seeTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),T_main_Totalchild.class);
                startActivity(intent);
            }
        });
    }
}
