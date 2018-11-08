package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class H_Main_View_child extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_main_view_child);

        ListView people = (ListView)findViewById(R.id.hChildList);

        PeopleAdapter adapter = new PeopleAdapter();

        adapter.addItem("방승희","꽃잎","0101");
        adapter.addItem("손혜진","매화","0101");
        adapter.addItem("최지우","장미","0101");

        people.setAdapter(adapter);

        people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PeopleListViewItem item = (PeopleListViewItem) parent.getItemAtPosition(position);

                String nameStr = item.getpName();
                String classStr = item.getpClass();
                String numStr = item.getpNum();
            }
        });
    }
}
