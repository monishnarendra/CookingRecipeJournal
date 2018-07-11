package com.example.asus.mnmgrecipejournal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.List;

/**
 * Created by Asus on 23-03-2018.
 */

public class SpinnerAdapterItem extends BaseAdapter {

    private List<String> lsData;
    private Activity activity;
    private LayoutInflater inflater;

    public SpinnerAdapterItem(List<String> lsData, Activity activity) {
        this.lsData = lsData;
        this.activity = activity;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lsData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null){
            view = inflater.inflate(R.layout.spinner_level_items,null);
            TextView tv = (TextView) view.findViewById(R.id.textView4);
            tv.setText(lsData.get(position));
            return view;
        }
        return convertView;
    }
}
