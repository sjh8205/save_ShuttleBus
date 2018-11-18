package com.example.sonhyejin.save_shuttlebus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterChild extends BaseAdapter{
    private ArrayList<ListViewChild> listViewItemList = new ArrayList<ListViewChild>();

    public AdapterChild(){

    }

    public int getCount(){
        return listViewItemList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_view_child, parent, false);
        }

        TextView nameView = (TextView) convertView.findViewById((R.id.viewName));
        TextView classView = (TextView) convertView.findViewById((R.id.viewClass));
        TextView numView = (TextView) convertView.findViewById((R.id.viewNum));

        ListViewChild listViewItem = listViewItemList.get(position);

        nameView.setText(listViewItem.getpName());
        classView.setText(listViewItem.getpClass());
        numView.setText(listViewItem.getpNum());

        return convertView;
    }

    public long getItemId(int position){
        return position;
    }

    public Object getItem(int position){
        return listViewItemList.get(position);
    }

    public void addItem(String name, String pclass, String num){
        ListViewChild item = new ListViewChild();

        item.setpName(name);
        item.setpClass(pclass);
        item.setpNum(num);

        listViewItemList.add(item);
    }

}
