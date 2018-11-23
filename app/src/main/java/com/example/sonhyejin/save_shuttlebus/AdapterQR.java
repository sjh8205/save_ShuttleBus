package com.example.sonhyejin.save_shuttlebus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AdapterQR extends ArrayAdapter implements View.OnClickListener{
    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }

    // 생성자로부터 전달된 resource id 값을 저장.
    int resourceId ;
    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private ListBtnClickListener listBtnClickListener ;


    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    AdapterQR(Context context, int resource, ArrayList<ListViewQR> list, ListBtnClickListener clickListener) {
        super(context, resource, list) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource ;

        this.listBtnClickListener = clickListener ;
    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position ;
        final Context context = parent.getContext();

        // 생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.listview_btn_item*/, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        final ImageView cState = (ImageView) convertView.findViewById(R.id.checkChildState);
        final TextView cClass = (TextView) convertView.findViewById(R.id.checkChildClass);
        final TextView cName = (TextView) convertView.findViewById(R.id.checkChildName);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewQR listViewItem = (ListViewQR) getItem(position);

        // 아이템 내 각 위젯에 데이터 반영
        cState.setImageDrawable(listViewItem.getimg());
        cName.setText(listViewItem.getsName());
        cClass.setText(listViewItem.getsClass());

        // button1 클릭 시 TextView(textView1)의 내용 변경.
        Button btn = (Button) convertView.findViewById(R.id.checkChildNone);
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //cState.setImageDrawable(ContextCompat.getDrawable(H_Main_View_Teacher.class,R.drawable.busstop));
            }
        });
        return convertView;
    }

    public void onClick(View v) {
        // ListBtnClickListener(MainActivity)의 onListBtnClick() 함수 호출.
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)v.getTag()) ;
        }
    }

}
