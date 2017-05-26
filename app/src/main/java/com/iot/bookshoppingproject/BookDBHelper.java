package com.iot.bookshoppingproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hdj on 2017-05-24.
 */

public class BookDBHelper extends SQLiteOpenHelper{

    private static String DATABASE_NAME = "store";
    private static int DATABASE_VERSION = 6;

    public BookDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBases.CreateDB.BOOKS_CREATE);
        db.execSQL(DataBases.CreateDB.CUSTOMER_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public boolean insertRecord(SQLiteDatabase db, String TABLE_NAME, String _id, String password){
        try {
            db.execSQL("" +
                    "insert into " + TABLE_NAME +
                    " ( _id, password ) " +
                    " values ( '" + _id + "' , '" + password + "'); ");
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage().toString();
            return false;
        }
        return true;
    }

    public boolean insertBook(SQLiteDatabase db, String TABLE_NAME, String title, int price, String barcode){
        try {
            db.execSQL("" +
                    "insert into " + TABLE_NAME +
                    " ( title, price , barcode) " +
                    " values ( '" + title + "' , '" + price + "' , '"+ barcode +"'); ");
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage().toString();
            return false;
        }
        return true;
    }
}

