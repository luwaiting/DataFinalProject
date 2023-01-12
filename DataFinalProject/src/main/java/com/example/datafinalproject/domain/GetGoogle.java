package com.example.datafinalproject.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class GetGoogle {
    public String searchKeyword;
    public String url;
    public String content;

    public GetGoogle(String searchKeyword) {
        this.searchKeyword = searchKeyword;
        this.url = String.format("http://www.google.com/search?q=%s&oe=utf8&num=40",searchKeyword);
//        this.url = "http://www.google.com/search?q=" + searchKeyword + "&oe=utf8&num=40";
    }

    private String fetchContent(){
        String retVal = "";
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            //set HTTP header
            conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
            InputStream in = conn.getInputStream();

            InputStreamReader inReader = new InputStreamReader(in, "utf-8");
            BufferedReader bufReader = new BufferedReader(inReader);
            String line = null;

            while ((line = bufReader.readLine()) != null) {
                retVal += line;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
//        in.close();
//        bufReader.close();
        return retVal;
    }

    public HashMap<String, String> query(){
        HashMap<String, String> retVal = new HashMap<String, String>();
        if (content == null) {
            content = fetchContent();
        }
        Document doc = Jsoup.parse(content);
//		select particular element(tag) which you want
        Elements lis = doc.select("div");
        lis = lis.select(".kCrYT");
        for (Element li : lis) {
            try {
                String citeUrl = li.select("a").get(0).attr("href");
//				citeUrl=citeUrl.sp
                citeUrl = "https" + citeUrl.split("https")[1];
                citeUrl = citeUrl.split("&sa").length > 0 ? citeUrl.split("&sa")[0] : citeUrl;
                citeUrl = citeUrl.replace("%25", "%");
                String title = li.select("a").get(0).select(".vvjwJb").text();
                if (title.equals("")) {
                    continue;
                }
//				System.out.println("Title: " + title + " , url: " + citeUrl);
                int x = citeUrl.indexOf('/', 8);
                String alt = citeUrl;
                if (x == -1) {
                    alt += "/";
                    x = alt.indexOf('/', 8);
                }
//				  System.out.println(x);
                String xx = alt.substring(0, x);
                String xxx = xx + "/robots.txt";
//		          System.out.println(xxx);
                URL url = new URL(xxx);
                URLConnection conn = url.openConnection();
                InputStream in = conn.getInputStream();
//		            	if(in==null) {
//		            		 retVal.put(title, citeUrl);
//		            		 continue;
//		            	}
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String r = "";
                String line = null;
                while ((line = br.readLine()) != null) {
                    r = r + line + "\n";
                }
//			          System.out.println(r);
                if (r.contains("Disallow: /")) {
//			                    int i = r.indexOf("Disallow: /");
//			                    String j = r.substring(i, i + 12);
//			                    System.out.println(j);
                    continue;
                } else {
                    System.out.println(citeUrl);
                    retVal.put(title, citeUrl);
                }
                // put title and pair into HashMap
//				retVal.put(title, citeUrl);
            } catch (IndexOutOfBoundsException e) {
//				e.printStackTrace();
            }catch (IOException e){
                continue;
            }

        }



        return retVal;
    }
}
