package com.example.students;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BaseAdapter extends android.widget.BaseAdapter {
    ArrayList<student>list;
    Context context;
    public BaseAdapter(Context context,ArrayList<student>list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_single_row,null);
        TextView nameview = view.findViewById(R.id.student_text_view);
        nameview.setText(list.get(position).getName());
        return view;
    }
}
