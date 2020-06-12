package com.example.students;

public class Book {
    private String id;
    private String bookname;
    private String doctorname;

    public Book() {
    }


    public Book(String bookname,String doctorname,String id) {
        this.bookname = bookname;
        this.doctorname=doctorname;
        this.id=id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
