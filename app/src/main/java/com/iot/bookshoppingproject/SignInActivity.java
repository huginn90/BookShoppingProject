package com.iot.bookshoppingproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by test2 on 2017-05-22.
 */

public class SignInActivity extends AppCompatActivity{
    private static String TABLE_NAME = "customer";
    private SQLiteDatabase db;
    private BookDBHelper dbHelper;
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
        dbHelper = new BookDBHelper(this);
        db = dbHelper.getWritableDatabase();
        return true;
    }
}
