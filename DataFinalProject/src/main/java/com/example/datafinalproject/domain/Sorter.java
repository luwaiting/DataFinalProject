package com.example.datafinalproject.domain;

public class Sorter {
    public String title;
    public String description;
    public WebNode root;
    public Sorter(String title,String description,WebNode webNode){
        this.title=title;
        this.description=description;
        this.root=webNode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WebNode getRoot() {
        return root;
    }

    public void setRoot(WebNode root) {
        this.root = root;
    }
}
