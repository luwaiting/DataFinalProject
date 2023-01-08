package com.example.datafinalproject.domain;

import java.util.ArrayList;
import java.util.List;

public class PresetKeywords implements Lists<Keyword> {
        public static ArrayList<Keyword>list=new ArrayList<>();
    public void add(Keyword keyword) {
        list.add(keyword);
    }
    public ArrayList<Keyword> getList() {
        return list;
    }
    public int size(){return list.size();}
}
