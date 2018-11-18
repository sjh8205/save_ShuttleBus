package com.example.sonhyejin.save_shuttlebus;

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

        AdapterChild adapter = new AdapterChild();

        adapter.addItem("김똥개","꽃잎","0101");
        adapter.addItem("김철수","매화","0101");
        adapter.addItem("김영희","장미","0101");

        people.setAdapter(adapter);

        people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewChild item = (ListViewChild) parent.getItemAtPosition(position);

                String nameStr = item.getpName();
                String classStr = item.getpClass();
                String numStr = item.getpNum();
            }
        });
    }
}
