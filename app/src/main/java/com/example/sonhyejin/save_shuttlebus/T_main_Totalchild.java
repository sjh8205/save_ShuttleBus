package com.example.sonhyejin.save_shuttlebus;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class T_main_Totalchild extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main_totalchild);

        final ListView total = (ListView) findViewById(R.id.tChildList);

        AdapterQR Adapter = new AdapterQR();

        Adapter.addItem("지우", "매화", ContextCompat.getDrawable(this, R.drawable.busstop));
        Adapter.addItem("혜진", "튤립", ContextCompat.getDrawable(this, R.drawable.busstop));
        Adapter.addItem("승희", "장미", ContextCompat.getDrawable(this, R.drawable.busstop));

        total.setAdapter(Adapter);

        total.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewQR item = (ListViewQR) parent.getItemAtPosition(position);

                String sName = item.getsName();
                String sClass = item.getsClass();
                Drawable img = item.getimg();
            }
        });
    }
}
