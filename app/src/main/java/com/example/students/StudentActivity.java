package com.example.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    String idstudent ;
    DatabaseReference myRef = null;
    ListView bookview ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        bookview = findViewById(R.id.bookview);
        Bundle b = getIntent().getExtras();
        idstudent = b.getString("id_student_key");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef= database.getReference("Students");

        final FloatingActionButton newbook= findViewById(R.id.add_book);
        final AlertDialog.Builder Alert = new AlertDialog.Builder(this);

        newbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.add_book,null);
                Alert.setView(view);
                final AlertDialog Build = Alert.create();
                Build.show();

                final EditText bookname = view.findViewById(R.id.edit_book_name);
                final EditText doctorname = view.findViewById(R.id.edit_doctor_name);
                final Button addbook = view.findViewById(R.id.add_book);
                addbook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String b = bookname.getText().toString();
                        String d = doctorname.getText().toString();
                        if (b.isEmpty()&&d.isEmpty()){
                            Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_LONG).show();
                        }
                        else{
                            String id = myRef.child(idstudent).child("books").push().getKey();
                            Book book = new Book(b,d,id);
                            myRef.child(idstudent).child("books").child(id).setValue(book);
                            Build.dismiss();


                        }


                    }
                });

            }
        });


        getbooks();
    }

    public void getbooks(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Book> booklist = new ArrayList<>();
                Iterable<DataSnapshot> m = dataSnapshot.child(idstudent).child("books").getChildren();
                for (DataSnapshot i:m) {
                    Book n = i.getValue(Book.class);
                    booklist.add(0,n);
                }
                BookAdapter Adapter = new BookAdapter(getApplicationContext(),booklist,idstudent);
                bookview.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
