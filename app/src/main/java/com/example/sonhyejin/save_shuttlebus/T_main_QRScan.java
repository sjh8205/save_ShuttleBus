package com.example.sonhyejin.save_shuttlebus;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class T_main_QRScan extends AppCompatActivity{
//선생님은 원생들 qr 스캔하는 페이지?

    public boolean loadItems(ArrayList<ListViewQR> list) {
        ListViewQR item ;

        if (list == null) {
            list = new ArrayList<ListViewQR>() ;
        }

        // 아이템 생성.
        item = new ListViewQR() ;
        item.setimg(ContextCompat.getDrawable(this,R.drawable.busstop));
        item.setsName("a") ;
        item.setsClass("b") ;
        list.add(item) ;

        item = new ListViewQR() ;
        item.setimg(ContextCompat.getDrawable(this,R.drawable.busstop));
        item.setsName("c") ;
        item.setsClass("d") ;
        list.add(item) ;

        item = new ListViewQR() ;
        item.setimg(ContextCompat.getDrawable(this,R.drawable.busstop));
        item.setsName("e") ;
        item.setsClass("f") ;
        list.add(item) ;

        return true ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main_qrscan);

        ListView child = (ListView)findViewById(R.id.tChildList);

        AdapterQR adapter;
        ArrayList<ListViewQR> items = new ArrayList<ListViewQR>() ;

        loadItems(items) ;

        adapter = new AdapterQR(this, R.layout.layout_attendance, items,null) ;

        child.setAdapter(adapter);

        child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {

            }
        }) ;

    }
}
