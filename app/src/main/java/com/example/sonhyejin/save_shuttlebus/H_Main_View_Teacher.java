package com.example.sonhyejin.save_shuttlebus;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class H_Main_View_Teacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_view_teacher);

        ListView people = (ListView)findViewById(R.id.hTeachList);

        AdapterTeach adapter = new AdapterTeach();

        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"꽃","0101","00");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"매","0101","00");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.busstop),"장","0101","00");

        people.setAdapter(adapter);

        people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewTeach item = (ListViewTeach) parent.getItemAtPosition(position);

                Drawable img = item.getimg();
                String nameStr = item.gettName();
                String classStr = item.gettClass();
                String numStr = item.gettNum();
            }
        });
    }
}
