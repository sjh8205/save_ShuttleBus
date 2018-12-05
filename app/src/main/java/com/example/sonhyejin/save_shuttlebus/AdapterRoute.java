package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterRoute extends BaseAdapter{
    private ArrayList<ListViewRoute> listViewItemList = new ArrayList<ListViewRoute>();

    public AdapterRoute(){

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

        ImageView busView = (ImageView) convertView.findViewById(R.id.busiconimg);
        ImageView stationView = (ImageView) convertView.findViewById(R.id.busStationImg);
        TextView nameView = (TextView) convertView.findViewById((R.id.busStationName));

        ListViewRoute listViewItem = listViewItemList.get(position);

        busView.setImageDrawable((listViewItem.getbusimg()));
        stationView.setImageDrawable((listViewItem.getstimg()));
        nameView.setText(listViewItem.getstname());

        return convertView;
    }

    public long getItemId(int position){
        return position;
    }

    public Object getItem(int position){
        return listViewItemList.get(position);
    }

    public void addItem(Drawable busimg, Drawable stimg, String name){
        ListViewRoute item = new ListViewRoute();

        item.setbusimg(busimg);
        item.setstimg(stimg);
        item.setstname(name);

        listViewItemList.add(item);
    }
}
