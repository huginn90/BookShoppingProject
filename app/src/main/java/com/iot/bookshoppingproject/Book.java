package com.iot.bookshoppingproject;

/**
 * Created by hdj on 2017-05-17.
 */

public class Book {

    public Book(String bookTitle, int bookPrice, String bookbarcode) {
        this.bookTitle = bookTitle;
        this.bookPrice = bookPrice;
        this.bookbarcode = bookbarcode;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookbarcode() {
        return bookbarcode;
    }

    public void setBookbarcode(String bookbarcode) {
        this.bookbarcode = bookbarcode;
    }

    String bookTitle;
    int bookPrice;
    String bookbarcode;

}
