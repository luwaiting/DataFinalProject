package com.example.datafinalproject.domain;

import java.io.IOException;
import java.util.*;

public class Manager {
    //    public PresetKeywords pre;
//    public Map<String,String>google;
    //get google result
    public ArrayList<Sorter> integrate(String userInputKeyword) {
//        Keyword input=new Keyword(userInputKeyword,10.0);
//        if(!PresetKeywords.keys.contains(input)){
//            PresetKeywords keywords=new PresetKeywords();
//            keywords.addPresetKeywordList(input);
//        }
        //after getting a urlList from google
        ArrayList<Sorter> eachGooglePageScore = new ArrayList<Sorter>();
        Lists<Keyword> pre = new PresetKeywords();
        if(pre.getList().isEmpty()){
            setPreSetKey(pre);
        }
        GetGoogle getGoogle = new GetGoogle(userInputKeyword);
        Map<String, String> googleResult = null;
        googleResult = getGoogle.query();
        Set<String> set = googleResult.keySet();
        Iterator<String> it = set.iterator();
        int sd = 0;
        //建各個google頁面的tree
        while (it.hasNext()) {
            WebTree tree;
            String key = it.next();
            String eachRootUrl = googleResult.get(key);
            HtmlHandler htmlHandler = new HtmlHandler(eachRootUrl);
            String desc = htmlHandler.findDescription();
//            System.out.println(desc);
            WebNode rootWeb = new WebNode(new WebPage(eachRootUrl));
//            int extraScore = 0;
//過濾後的小孩-->robots.txt
            ArrayList<WebPage> subWeb = htmlHandler.findSubWeb();
            for (WebPage w : subWeb) {
                WebNode child = new WebNode(w);
                child.parent = rootWeb;
                rootWeb.addChild(child);
//                    HtmlHandler childHtmlHandler = new HtmlHandler(child.web.url);
//                    WordCounter countExtra=new WordCounter(child.web.url);
//                    for(Keyword k:PresetKeywords.keys){
//                        String childTitle=childHtmlHandler.findSubWebTitle();
//                        extraScore+=k.weight*countExtra.countForExtra(childTitle,k.name);
//                    }
//                    List<WebPage>subsub=childHtmlHandler.findSubSubWeb();
//                    for (WebPage wc : subsub) {
//                        WebNode grandChild = new WebNode(wc);
//                        grandChild.parent = child;
//                        child.addChild(grandChild);
//                    }
            }
            tree = new WebTree(rootWeb);
            tree.setPostOrderScore(pre.getList());
//            tree.setPostOrderScore(PresetKeywords.keys);
//                rootWeb.totalScore+=extraScore;
            //controller can get total score from each google link and url
            eachGooglePageScore.add(new Sorter(key, desc, rootWeb));
//               sd++;
        }
//        for (Sorter s:
//             eachGooglePageScore) {
//            System.out.println(s.title);
//        }
//            System.out.print(sd);
        sortWebByScore(eachGooglePageScore, 0, eachGooglePageScore.size() - 1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return eachGooglePageScore;
    }

    //quickSort
    public void sortWebByScore(ArrayList<Sorter> list, int start, int end) {
        if (end > start) {
            int pivot = partition(list, start, end);
            sortWebByScore(list, start, pivot - 1);
            sortWebByScore(list, pivot + 1, end);
        }
    }

    public int partition(ArrayList<Sorter> list, int start, int end) {
        int pivot = list.get(start).root.totalScore;
        int left = start + 1;
        int right = end;
        boolean done = false;
        while (done == false) {
            while (left <= right && list.get(left).root.totalScore >= pivot) {
                left++;
            }
            while (left <= right && list.get(right).root.totalScore <= pivot) {
                right--;
            }
            if (left > right) {
                done = true;
            } else {
                swap(list, left, right);
            }
        }
        swap(list, start, right);
        return right;
    }

    public void swap(ArrayList<Sorter> list, int x, int y) {
        Sorter temp = list.get(x);
        list.set(x, list.get(y));
        list.set(y, temp);
    }

    public void setPreSetKey(Lists<Keyword> pre) {
        pre.add(new Keyword("維基百科", 21));
        pre.add(new Keyword("網紅", 20));
        pre.add(new Keyword("明星", 20));
        pre.add(new Keyword("巨星", 20));
        pre.add(new Keyword("演員", 20));
        pre.add(new Keyword("音樂", 20));
//        pre.add(new Keyword("樂曲", 10));
        pre.add(new Keyword("歌手", 20));
        pre.add(new Keyword("藝人", 20));
        pre.add(new Keyword("藝名", 20));
        pre.add(new Keyword("歌曲", 20));
        pre.add(new Keyword("歌詞", 30));
        pre.add(new Keyword("饒舌", 20));
        pre.add(new Keyword("唱片", 30));
        pre.add(new Keyword("演藝圈", 30));
        pre.add(new Keyword("創作", 30));
        pre.add(new Keyword("金曲獎", 30));
        pre.add(new Keyword("樂團", 30));
        pre.add(new Keyword("紅人", 30));
        pre.add(new Keyword("曲", 30));
        pre.add(new Keyword("作品", 30));
        pre.add(new Keyword("樂", 30));
        pre.add(new Keyword("專輯", 30));
        pre.add(new Keyword("走紅", 30));
        pre.add(new Keyword("主持人", 20));
        pre.add(new Keyword("新聞", 5));
        pre.add(new Keyword("實況", 5));
        pre.add(new Keyword("rapper", 20));
        pre.add(new Keyword("singer", 30));
        pre.add(new Keyword("spotify", 10));
        pre.add(new Keyword("youtuber", 30));
        pre.add(new Keyword("music", 30));
        //opposite
        pre.add(new Keyword("分手", -1));
        pre.add(new Keyword("官司", -1));
        pre.add(new Keyword("離婚", -1));
        pre.add(new Keyword("大腦", -1));
        pre.add(new Keyword("研究", -1));
        pre.add(new Keyword("和解", -1));
        pre.add(new Keyword("爆出", -1));
        pre.add(new Keyword("誤會", -1));
        pre.add(new Keyword("環境", -1));
        pre.add(new Keyword("食物", -10));
        pre.add(new Keyword("食品", -10));
        pre.add(new Keyword("飲料", -10));
        pre.add(new Keyword("商品", -10));
        pre.add(new Keyword("雞蛋", -10));
        pre.add(new Keyword("熱狗堡", -10));
        pre.add(new Keyword("好吃", -10));
        pre.add(new Keyword("口味", -10));
        pre.add(new Keyword("氣味", -10));
        pre.add(new Keyword("美食", -10));
        pre.add(new Keyword("食譜", -10));
        pre.add(new Keyword("食", -5));
        pre.add(new Keyword("肉", -10));
        pre.add(new Keyword("早餐店", -10));
        pre.add(new Keyword("餐", -10));
        pre.add(new Keyword("麥當勞", -10));
        pre.add(new Keyword("詞彙", -10));
        pre.add(new Keyword("教育", -10));
        pre.add(new Keyword("翻譯", -10));
        pre.add(new Keyword("解釋", -10));
        pre.add(new Keyword("購物", 1));
        pre.add(new Keyword("網購", 1));
        pre.add(new Keyword("販售", 1));
        pre.add(new Keyword("動畫", 1));
        pre.add(new Keyword("運動用品", -2));
        pre.add(new Keyword("警", -20));
        pre.add(new Keyword("執勤", -20));
        pre.add(new Keyword("逮捕", -10));
        pre.add(new Keyword("拍賣", -3));
        pre.add(new Keyword("餐廳", -10));
        pre.add(new Keyword("店家", -10));
        pre.add(new Keyword("店員", -10));
        pre.add(new Keyword("translation", -10));
        pre.add(new Keyword("dictionary", -10));
        pre.add(new Keyword("delete", -10));
        pre.add(new Keyword("environment", -1));

    }


}
