package com.example.datafinalproject.domain;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class HtmlHandler {
    private String urlStr;
    private String content;

    public HtmlHandler(String urlStr) {
        this.urlStr = urlStr;
    }

    public String fetchContent() {
        URL url = null;
        InputStream in = null;
        String retVal = "";
        try {
            url = new URL(this.urlStr);
            URLConnection conn = url.openConnection();
            in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = br.readLine()) != null) {
                retVal = retVal + line + "\n";
            }
        } catch (IOException e) {
//            retVal=null;
        }
        return retVal;
    }

    public ArrayList<WebPage> findSubWeb() {
//        String content = null;
        if (content == null) {
            content = fetchContent();
            if (content == null) {
                System.out.print("fetch error");
            }
        }
        ArrayList<WebPage> x = new ArrayList<WebPage>();
        Document doc = Jsoup.parse(content);
        Elements lists = doc.select("a[href]");
//        out :
        for (Element li : lists) {
            try {
                String subWeb = li.select("a").get(0).attr("href");
                //&&!subWeb.contains("twitter")&&!subWeb.contains("instagram")&&!subWeb.contains("book")&&!subWeb.contains("youtube")&&!subWeb.contains("imdb")) {
                if (subWeb.startsWith("http")&&subWeb.length()>=8) {//&&!subWeb.contains("mirror")&&!subWeb.contains("5music")&&!subWeb.contains("mymusic")
                    int y = subWeb.indexOf('/', 8);
                    String alt = subWeb;
                    if (y == -1) {
                        alt += "/";
                        y = alt.indexOf('/', 8);
                    }
                    String xx = alt.substring(0, y);
                    String xxx = xx + "/robots.txt";
//                    System.out.println("子網頁測試");
//                    System.out.println(xxx);
                    URL url = null;
                    url = new URL(xxx);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String r = "";
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        r = r + line + "\n";
                    }
                    if (r.contains("Disallow: /")) {
                        continue;
                    }else{
                        if (x.size() <= 2) {
                            System.out.println(subWeb);
                            x.add(new WebPage(subWeb));
                        } else {
                            break;
                        }
                    }
                } else {
                    continue;
                }
            } catch (MalformedURLException e) {
                continue;
//                e.printStackTrace();
            } catch (IOException e) {
                continue;
            }
//            String title = li.select("a").get(0).select(".vvjwJb").text();
        }
        //after get subWeb url
        return x;
    }
    public String findSubWebTitle() {
        if (content == null) {
            content = fetchContent();
            if (content == null) {
                System.out.print("fetch error");
            }
        }
        String title = "";
        Document doc = Jsoup.parse(content);
        title = doc.title();
        return title;
    }

    public String findDescription() {
        if (content == null) {
            content = fetchContent();
            if (content == null) {
                return "none";
            }
        }
        if (content.contains("name=\"description\"")) {
            String des = "";
            Document doc = Jsoup.parse(content);
            Elements elements = doc.select("meta[name=description]");
            if (elements != null) {
                Element element = elements.get(0);
                des = element.attr("content");
            }
            return des;
        } else {
            return "none";
        }
//        des=doc.select("meta[name=description]").get(0).attr("content");
//        System.out.println(des);
//        return des;
    }
}
