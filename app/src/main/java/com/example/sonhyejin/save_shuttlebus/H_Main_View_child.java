package com.example.sonhyejin.save_shuttlebus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

        /*

        Button but = (Button)findViewById(R.id.next);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(H_Main_View_child.this,H_Main_View_Teacher.class));
            }
        });

        */
        //상단 주석처리된 구문은 리스트뷰가 개별적으로 잘 나오는지 확인하기 위함

    }
}
