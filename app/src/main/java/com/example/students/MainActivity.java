package com.example.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseReference myRef = null;
    ArrayList<student> stulist = new ArrayList<>();
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef=database.getReference("Students");

        final FloatingActionButton add = findViewById(R.id.add_student);
        final AlertDialog.Builder Alert = new AlertDialog.Builder(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.add_student,null);
                Alert.setView(view);
                final AlertDialog Build = Alert.create();
                Build.show();

                final EditText studentname = view.findViewById(R.id.edit_student);
                final Button studentadd = view.findViewById(R.id.add_btn);

                studentadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String n = studentname.getText().toString();

                        if (n.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_LONG).show();

                        }
                        else {
                            idd = myRef.push().getKey();
                            student Student = new student(idd,n,new HashMap<String, Book>());
                            myRef.child(idd).setValue(Student);
                            Build.dismiss();
                        }
                    }
                });


            }
        });
        final ListView list =findViewById(R.id.listview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                student j = stulist.get(position);
                Intent intent = new Intent(getApplicationContext(),StudentActivity.class);
                intent.putExtra("id_student_key",j.getId());
                intent.putExtra("name_student_key",j.getName());
                //intent.putExtra("list_books_key",j.getBooks());
                startActivity(intent);
            }
        });

        final AlertDialog.Builder deletedialog = new AlertDialog.Builder(this);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                View viewe = getLayoutInflater().inflate(R.layout.delete,null);
                deletedialog.setView(viewe);
                final AlertDialog Builde = deletedialog.create();
                Builde.show();

                final Button dele = viewe.findViewById(R.id.sure_delete);
                final Button cancle = viewe.findViewById(R.id.cancle);

                dele.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef.child(stulist.get(position).getId()).removeValue();
                        Builde.dismiss();
                    }
                });

                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Builde.dismiss();
                    }
                });
                return true;
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        final ListView list =findViewById(R.id.listview);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> x = dataSnapshot.getChildren();
                 stulist.clear();
                for (DataSnapshot i:x) {
                    student y = i.getValue(student.class);
                    stulist.add(0,y);
                }
                BaseAdapter Adapter = new BaseAdapter(getApplicationContext(),stulist);
                list.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
