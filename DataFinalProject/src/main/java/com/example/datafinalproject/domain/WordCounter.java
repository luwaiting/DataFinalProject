package com.example.datafinalproject.domain;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
public class WordCounter {
    private String urlStr;
    private String content;

    public WordCounter(String urlStr){
        this.urlStr = urlStr;
    }

    public String fetchContent(){
        URL url = null;
        InputStream in=null;
        String retVal = "";
        try {
            url = new URL(this.urlStr);
            URLConnection conn = url.openConnection();
            in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = br.readLine()) != null)
            {
                retVal = retVal + line + "\n";
            }
        } catch (IOException e) {
           retVal=null;
        }
        return retVal;
//        HttpURLConnection conn = null;
//        String retVal = "";
//        try {
//            URL url = new URL(this.urlStr);
////        URLConnection conn = url.openConnection();
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-type","application/X-WWW-form-urlencoded");
//            conn.connect();
//            InputStream in = conn.getInputStream();
//            try (BufferedReader br = (conn.getResponseCode() >= 400
//                    ? new BufferedReader(new InputStreamReader(conn.getErrorStream()))
//                    : new BufferedReader(new InputStreamReader(conn.getInputStream())))) {
////        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    retVal = retVal + line + "\n";
//                }
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            if (conn != null) {
//                conn.disconnect();
//            }
//        }
////        in.close();
////        br.close();
//        return retVal;
////
    }

    public int countKeyword(String keyword){
        if (content == null){
            content = fetchContent();
            if(content == null){
                return 0;
            }
        }
        content = content.toUpperCase();
        keyword = keyword.toUpperCase();
        int retVal = 0;
        retVal=BoyerMoore(content,keyword);
        return retVal;
    }
    public int countForExtra(String content,String keyword){
        content = content.toUpperCase();
        keyword = keyword.toUpperCase();
        int retVal = 0;
        retVal=BoyerMoore(content,keyword);
        return retVal;
    }
    public int last(char c, String P){
        // Bonus: Implement last occurence function
        for(int i=P.length()-1;i>=0;i--) {
            if(c==P.charAt(i)) {
                return i;
            }
        }
        return -1;
    }
    public int min(int a, int b){
        if (a < b)
            return a;
        else if (b < a)
            return b;
        else
            return a;
    }
    public int BoyerMoore(String T, String P){
        int i = P.length() -1;
        int j = P.length() -1;
        int retVal=0;
        while(i<T.length()) {
            if(T.charAt(i)==P.charAt(j)) {
                if(j==0) {
                    retVal++;
                    i++;
                }else {
                    i--;
                    j--;

                }
            }else {
                i=i+P.length()-min(j,1+last(T.charAt(i),P));
                j=P.length()-1;
            }
        }
        return retVal;
    }
}
