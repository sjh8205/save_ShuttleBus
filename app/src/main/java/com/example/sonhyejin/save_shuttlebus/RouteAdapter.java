package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RouteAdapter extends BaseAdapter{
    private ArrayList<RouteListViewItem> listViewItemList = new ArrayList<RouteListViewItem>();

    public RouteAdapter(){

    }

    public int getCount(){
        return listViewItemList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_route, parent, false);
        }

        ImageView imgView = (ImageView) convertView.findViewById(R.id.busStationImg);
        TextView nameView = (TextView) convertView.findViewById((R.id.busStationName));

        RouteListViewItem listViewItem = listViewItemList.get(position);

        imgView.setImageDrawable((listViewItem.getimg()));
        nameView.setText(listViewItem.getstname());

        return convertView;
    }

    public long getItemId(int position){
        return position;
    }

    public Object getItem(int position){
        return listViewItemList.get(position);
    }

    public void addItem(Drawable image, String name){
        RouteListViewItem item = new RouteListViewItem();

        item.setimg(image);
        item.setstname(name);

        listViewItemList.add(item);
    }
}
