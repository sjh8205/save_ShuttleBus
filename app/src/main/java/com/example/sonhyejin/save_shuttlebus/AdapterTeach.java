package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTeach extends BaseAdapter {
    private ArrayList<ListViewTeach> listViewItemList = new ArrayList<ListViewTeach>();

    public AdapterTeach() {

    }

    public int getCount(){
        return listViewItemList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_view_teach,parent, false);
        }

        ImageView imgView = (ImageView) convertView.findViewById(R.id.img_teach);
        TextView nameView = (TextView) convertView.findViewById((R.id.viewName));
        TextView classView = (TextView) convertView.findViewById((R.id.viewClass));
        TextView numView = (TextView) convertView.findViewById((R.id.viewNum));

        ListViewTeach listViewItem = listViewItemList.get(position);

        nameView.setText(listViewItem.gettName());
        classView.setText(listViewItem.gettClass());
        numView.setText(listViewItem.gettNum());
        imgView.setImageDrawable((listViewItem.getimg()));

        return convertView;
    }

    public long getItemId(int position){
        return position;
    }

    public Object getItem(int position){
        return listViewItemList.get(position);
    }

    public void addItem(Uri image, String name, String tclass, String num){
        ListViewTeach item = new ListViewTeach();

        item.setimg(image);
        item.settName(name);
        item.settClass(tclass);
        item.settNum(num);

        listViewItemList.add(item);
    }
}
