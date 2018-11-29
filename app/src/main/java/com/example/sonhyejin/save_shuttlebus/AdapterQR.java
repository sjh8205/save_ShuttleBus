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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AdapterQR extends BaseAdapter{
    private ArrayList<ListViewQR> listViewItemList = new ArrayList<ListViewQR>();

    public AdapterQR(){ }

    public int getCount(){
        return listViewItemList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_attendance, parent, false);
        }

        ImageView imgView = (ImageView) convertView.findViewById(R.id.checkChildState);
        TextView nameView = (TextView) convertView.findViewById((R.id.checkChildName));
        TextView classView = (TextView) convertView.findViewById((R.id.checkChildClass));

        ListViewQR listViewItem = listViewItemList.get(position);

        imgView.setImageDrawable((listViewItem.getimg()));
        nameView.setText(listViewItem.getsName());
        classView.setText(listViewItem.getsClass());

        return convertView;
    }

    public long getItemId(int position){
        return position;
    }

    public Object getItem(int position){
        return listViewItemList.get(position);
    }

    public void addItem(String name, String sclass, Drawable image){
        ListViewQR item = new ListViewQR();

        item.setimg(image);
        item.setsName(name);
        item.setsClass(sclass);

        listViewItemList.add(item);
    }
}
