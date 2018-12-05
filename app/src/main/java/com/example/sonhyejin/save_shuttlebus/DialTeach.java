package com.example.sonhyejin.save_shuttlebus;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DialTeach extends AppCompatActivity {

    String T_name;
    String T_num;
    String T_img;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dial_teach);

        ImageView img = (ImageView) findViewById(R.id.T_img);
        TextView NAME = (TextView) findViewById(R.id.T_name);
        TextView NUM = (TextView) findViewById(R.id.T_num);

        String name = "";
        String num = "";
        String img_Path = "";

        img.setImageURI(Uri.parse(img_Path));
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "uri"); // 여길 왜 못넘지...
        NAME.setText("NAME : " + name);
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "name");
        NUM.setText("PHONE NUM : " + num);
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "num");

    }

    public void NAMEsetting(TextView NAME, String name) {
        NAME.setText("NAME : " + name);
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "name");

    }


    public void infoSetting(String timg, String name, String num) {

        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "info_setting");

    ImageView img = (ImageView) findViewById(R.id.T_img);
    TextView NAME = (TextView) findViewById(R.id.T_name);
    TextView NUM = (TextView) findViewById(R.id.T_num);


/*        View view = (View) getLayoutInflater().inflate(R.layout.layout_dialogue_teach, null);
        ImageView img = (ImageView)  view.findViewById(R.id.T_img);
        TextView NAME = (TextView) view.findViewById(R.id.T_name);
        TextView NUM = (TextView) view.findViewById(R.id.T_num);
*/
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "before uri");
        img.setImageURI(Uri.parse(timg));
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "uri"); // 여길 왜 못넘지...
        NAME.setText("NAME : " + name);
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "name");
        NUM.setText("PHONE NUM : " + num);
        Log.v("ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ", "num");

    }
}
