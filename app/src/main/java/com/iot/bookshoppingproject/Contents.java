package com.iot.bookshoppingproject;

import java.io.Serializable;

public class Contents implements Serializable {
    String userName;

    public Contents(String title) {
        this.userName = title;
    }

    public String getTitle() {
        return userName;
    }

}
