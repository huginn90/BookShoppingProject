package com.iot.bookshoppingproject;

import android.provider.BaseColumns;

/**
 * Created by test2 on 2017-05-26.
 */

public final class DataBases {

    public static final class CreateDB implements BaseColumns {

        private static String CUSTOMER_TABLE = "customer";
        private static String BOOKS_TABLE = "books";
        public static final String CUSTOMER_CREATE =
                " create table " + CUSTOMER_TABLE + "(" + " _id text PRIMARY KEY, " +
                        " password text )";
        public static  final String BOOKS_CREATE =
                " create table " + BOOKS_TABLE + "(" + " title text PRIMARY KEY, " +
                        " price integer, barcode text )";
    }
}
