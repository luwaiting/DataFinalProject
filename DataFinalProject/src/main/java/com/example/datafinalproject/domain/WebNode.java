package com.example.datafinalproject.domain;

import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
    public WebNode parent;
    public WebPage web;
    public ArrayList<WebNode>children;
    public int totalScore;
    public WebNode(WebPage rootPage){
        this.web=rootPage;
        children=new ArrayList<>();
    }
    public void setTotalScore(ArrayList<Keyword>keywords)throws IOException {
        web.setScore(keywords);
        totalScore=web.score;
        //注意可能有錯
        if(!children.isEmpty()){
            for (WebNode c:children){
                totalScore+=c.totalScore;
            }
        }
    }
    public int getDepth(){
        int retVal = 1;
        WebNode currNode = this;
        while(currNode.parent != null){
            retVal++;
            currNode = currNode.parent;
        }
        return retVal;
    }
    public boolean isTheLastChild(){
        if(this.parent == null) return true;
        ArrayList<WebNode> siblings = this.parent.children;

        return this.equals(siblings.get(siblings.size() - 1));
    }
    public void addChild(WebNode webNode){
        children.add(webNode);
    }
//    public void dealSubWeb(){
//        addChild(this);
//        HtmlHandler subWeb=new HtmlHandler(web.url);
//        List<WebPage>subPage=  subWeb.findSubWeb();
//        if(subPage!=null) {
//            WebNode node;
//            for (WebPage s : subPage) {
//                node = new WebNode(s);
//                node.parent = this;
//                children.add(node);
//            }
//        }
//    }
//    public void addChild(WebNode n){
//        HtmlHandler subWeb=new HtmlHandler(n.web.url);
//        List<WebPage>subPage=  subWeb.findSubWeb();
//        if(subPage!=null){
//            WebNode node;
//            for (WebPage s:subPage){
//                node=new WebNode(s);
//                node.parent = this;
//                n.children.add(node);
//            }
//            for(int i=0;i<children.size();i++){
//                addChild(children.get(i));
//            }
//        }
//    }
}
