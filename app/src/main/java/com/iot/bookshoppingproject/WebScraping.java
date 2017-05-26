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

    public String getISBNnumber() {
        return ISBNnumber;
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

        // Pattern으로 HTML에서 책제목, 가격을 추출.
        Pattern pattern_title = Pattern.compile("<meta property=\"og:title\" content=\"([^\"]+)\">");
        Pattern pattern_price = Pattern.compile("<del>([^<]+)</del>");

        Matcher matcher_title = pattern_title.matcher(result);
        Matcher matcher_price = pattern_price.matcher(result);

        if (matcher_title.find()) {
            title = matcher_title.group(1);
            System.out.println("제목 : "+ title);
        }
        else
            System.out.println("매칭 타이틀 없음");

        if (matcher_price.find()) {
            String a = matcher_price.group(1);
            price = Integer.parseInt(a.replace(",", ""));
            System.out.println("가격 : "+ price);
        }
        else
            System.out.println("매칭 가격 없음");

    }
    // 안드로이드에서 URL에 접속하기위해 쓰레드 생성.
    public void ThreadForwebconnect(final String barcodenumber) {
        Thread thread = new Thread() {
            public void run() {
                String url = "http://book.daum.net/detail/book.do?bookid=KOR"+barcodenumber;
                getBookInfoUseURL(url);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
