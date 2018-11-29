package com.example.sonhyejin.save_shuttlebus;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class T_main_QRScan extends AppCompatActivity {

    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_main_qrscan);

        final ListView qr = (ListView) findViewById(R.id.tChildList);
        Button scanner = (Button) findViewById(R.id.tQrScan);
        Button submit = (Button) findViewById(R.id.tCompletScan);

        qrScan = new IntentIntegrator(this);

        AdapterQR Adapter = new AdapterQR();

        Adapter.addItem("지우", "매화", ContextCompat.getDrawable(this, R.drawable.imhere));
        Adapter.addItem("혜진", "튤립", ContextCompat.getDrawable(this, R.drawable.imnothere));
        Adapter.addItem("승희", "장미", ContextCompat.getDrawable(this, R.drawable.imhere));

        qr.setAdapter(Adapter);

        qr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ListViewQR item = (ListViewQR) parent.getItemAtPosition(position);
                String row = (String) qr.getItemAtPosition(position);

                String sName = item.getsName();
                String sClass = item.getsClass();
                Drawable img = item.getimg();

                Toast.makeText(getApplicationContext(), "You selected : " + row, Toast.LENGTH_SHORT).show(); // 클릭한 해당 위치 받아오기
                //받은 위치의 버스 이미지만 보이게 하고 없애기
                //route.getItemAtPosition(row).getimg

            }
        });

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.setPrompt("Scanning...");
                //qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),T_main.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String str;
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null && resultCode == RESULT_OK) {
            // if user scanned and the result is valid, do your stuff here
            str = result.getContents().toString();
            Toast.makeText(this, "Scanned: " + str, Toast.LENGTH_LONG).show();
        } else {
            // if user pressed back or there's error, do your stuff here
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }

    }
}