package com.iot.bookshoppingproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.sql.DriverManager.println;

/**
 * Created by test2 on 2017-05-22.
 */

public class SignInActivity extends AppCompatActivity{
    private static String DATABASE_NAME = "store";
    private static String TABLE_NAME = "customer";
    private static int DATABASE_VERSION = 6;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    String _id;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        openDatabase();

        final EditText editTextID = (EditText)findViewById(R.id.EditTextID);
        final EditText editTextPW = (EditText)findViewById(R.id.EditTextPW);

        final Button checkingID = (Button)findViewById(R.id.CheckingID);
        checkingID.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _id = editTextID.getText().toString();
                        checkingID(TABLE_NAME, _id);
                    }
                }
        );

        Button btnConfirmed = (Button)findViewById(R.id.BtnConfirmed);
        btnConfirmed.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _id = editTextID.getText().toString();
                        password = editTextPW.getText().toString();

                        insertRecord(TABLE_NAME, _id, password);
                        finish();
                    }
                }
        );
    }

    private void insertRecord(String TABLE_NAME, String _id, String password){
        try {
            db.execSQL("" +
                    "insert into " + TABLE_NAME +
                    " ( _id, password ) " +
                    " values ( '" + _id + "' , '" + password + "'); ");
        }catch (Exception e){
            Toast.makeText(
                    getApplicationContext(),
                    "Insert 실패",
                    Toast.LENGTH_LONG
            ).show();
            e.printStackTrace();
            e.getMessage().toString();
        }
    }

    private void checkingID(String TABLE_NAME, String _id){
        Cursor cursor = db.rawQuery(
                " select _id from " +
                        TABLE_NAME +
                        " where _id like " + "'" + _id + "'"
                        , null
        );
        int recordCount = cursor.getCount();
        if(recordCount > 0){
            Toast.makeText(
                    getApplicationContext(),
                    "아이디를 생성할 수 없습니다.",
                    Toast.LENGTH_LONG
            ).show();
        } else{
            Toast.makeText(
                    getApplicationContext(),
                    "아이디를 생성할 수 있습니다.",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private boolean openDatabase(){
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        return true;
    }

    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String DROP_SQL =
                    " drop table if exists " + TABLE_NAME;
            String CREATE_TABLE_SQL =
                    " create table " + TABLE_NAME + "(" + " _id text PRIMARY KEY, " +
                            " password text )";
            try
            {
                db.execSQL(DROP_SQL);
                println("DB가 생성");
            }catch (Exception e){
                e.printStackTrace();
                println("테이블 삭제 에러");
                println((e.getMessage().toString()));
            }
            try
            {
                db.execSQL(CREATE_TABLE_SQL);
                println("테이블이 생성");
            }catch (Exception e){
                e.printStackTrace();
                println("테이블 생성 에러");
                println(e.getMessage().toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }
    }
}
