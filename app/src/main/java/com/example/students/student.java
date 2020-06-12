package com.example.students;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class student {
    private String id;
    private String name;
    Map<String,Book>books;


    public student() {
    }

    public student(String id, String name,Map<String,Book>books) {
        this.id = id;
        this.name = name;
        this.books = books;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String,Book> getBooks() {
        return books;
    }

    public void setBooks(Map<String,Book> books) {
        this.books = books;
    }
}


