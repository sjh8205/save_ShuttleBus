package com.example.sonhyejin.save_shuttlebus;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class T_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main);

        ListView route = (ListView)findViewById(R.id.tBusList);

        AdapterRoute Adapter = new AdapterRoute();

        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산1단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산2단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산3단지");
        Adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"일산4단지");

        route.setAdapter(Adapter);

        route.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewRoute item = (ListViewRoute) parent.getItemAtPosition(position);

                String stStr = item.getstname();
                Drawable img = item.getimg();
            }
        });
    }
}
