package com.example.datafinalproject.domain;
import java.io.IOException;
import java.util.ArrayList;
public class WebTree {
    public WebNode root;
    public WebTree(WebNode root){
        this.root=root;
    }
    public void setPostOrderScore(ArrayList<Keyword> keywords){
        try {
            setPostOrderScore(root, keywords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException{
        if(startNode.isTheLastChild()||startNode.children!=null) {
            for(WebNode w:startNode.children) {
                setPostOrderScore(w,keywords);
            }
        }
        startNode.setTotalScore(keywords);
    }

}
