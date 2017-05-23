package com.iot.bookshoppingproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hdj on 2017-05-23.
 */

public class WebScraping {

    private String ISBNnumber;
    private String title;
    private int price;

    public WebScraping(String ISBNnumber) {
        this.ISBNnumber = ISBNnumber;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    private void getBookInfoUseURL(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line + "\n";
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pattern pattern_title = Pattern.compile("<meta[^>]*property=[\"]og:title[\"][^>]*content=[\"]?([^>]+)[\"]>"); // 책제목을 찾기위한 정규식
        Pattern pattern_price = Pattern.compile("<del>?([^>\"']+)</del>"); // 책가격을 찾기위한 정규식

        Matcher matcher_title = pattern_title.matcher(result);
        Matcher matcher_price = pattern_price.matcher(result);

        if (matcher_title.find()) {
            title = matcher_title.group(1);
        }
        else
            System.out.println("매칭 타이틀 없음");

        if (matcher_price.find()) {
            String a = matcher_price.group(1);
            price = Integer.parseInt(a.replace(",", ""));
        }
        else
            System.out.println("매칭 가격 없음");

    }

    public void ThreadForwebconnect() {
        Thread thread = new Thread() {
            public void run() {
                String url = "http://book.daum.net/detail/book.do?bookid=KOR"+ISBNnumber;
                getBookInfoUseURL(url);
            }
        };
        thread.start();
    }

}
