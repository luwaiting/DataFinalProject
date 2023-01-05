package com.example.demo2.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Key {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String textValue;
    public String tryRes1(){
        return "first try";
    }
    public String tryRes2(){
        return "second try";
    }
    public String tryPost(String textValue){
        return "get "+textValue+" success";
    }
    public List<String> tryPost2(String textValue){
        List<String>list=new ArrayList<String>();
        List<String>list2=new ArrayList<String>();
        list.add("xddx");
        list.add("xxdd");
        list.add("axaxd");
        for(String x:list){
            if(x.contains(textValue)){
                list2.add(x);
            }
        }
        return list2;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
