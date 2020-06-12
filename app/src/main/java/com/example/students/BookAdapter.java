package com.example.students;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BookAdapter extends android.widget.BaseAdapter {
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    ArrayList<Book>list;
    Context context;
    String idstudent ;
    public BookAdapter(Context context, ArrayList<Book>list,String idstudent){
        this.context=context;
        this.list=list;
        this.idstudent=idstudent;
        database = FirebaseDatabase.getInstance();
        myRef=database.getReference("Students");
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_single_row,null);
        TextView nameview = view.findViewById(R.id.book_text_view);
        TextView doctorview = view.findViewById(R.id.doctor_text_view);
        nameview.setText(list.get(position).getBookname());
        doctorview.setText(list.get(position).getDoctorname());
        final ImageButton delete = view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(idstudent).child("books").child(list.get(position).getId()).removeValue();
            }
        });
        return view;


    }

}
