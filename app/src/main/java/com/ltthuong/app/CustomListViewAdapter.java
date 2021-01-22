package com.ltthuong.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<Object> {
    private Context context;
    private int resource;
    private List<Object> arr;

    public CustomListViewAdapter(Context context, int resource, ArrayList<Object> arr) {
        super(context, resource, arr);
        this.context = context;
        this.resource = resource;
        this.arr = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.one_inlist_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtKey = (TextView) convertView.findViewById(R.id.tvKey);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Object label = arr.get(position);
        viewHolder.txtKey.setText(label.getKey());
        viewHolder.txtName.setText(label.getName());
        return convertView;
    }

    public class ViewHolder {
        TextView txtKey, txtName;
    }
}

