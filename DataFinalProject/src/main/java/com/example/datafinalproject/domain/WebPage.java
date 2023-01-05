package com.example.datafinalproject.domain;

import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
    public String url;
    public int score;
    public WordCounter count;
    public WebPage (String url){
        this.url=url;
        count=new WordCounter(url);
    }
    public void setScore(ArrayList<Keyword>keywords)throws IOException {
        score=0;
        for(Keyword k:keywords){
            score+=k.weight*count.countKeyword(k.name);
        }
    }
}
