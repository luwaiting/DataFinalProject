package com.example.datafinalproject.domain;

import java.util.ArrayList;

public class Record implements Lists<String>{
    public static ArrayList<String>records=new ArrayList<String>();
    public void add(String record){
        records.add(record);
    }
    public ArrayList<String>getList(){
        return records;
    }
}
