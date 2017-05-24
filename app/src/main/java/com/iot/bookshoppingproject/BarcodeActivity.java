package com.iot.bookshoppingproject;
// 1

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class BarcodeActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextPrice;
    EditText editTextISBN;
    String barcodeNumber;
    SQLiteDatabase db;
    WebScraping webScraping;

    private void openDatabase(String DATABASE_NAME){
        try{
            db = openOrCreateDatabase(
                    DATABASE_NAME,
                    Activity.MODE_PRIVATE,
                    null);
        }catch (Exception e){
            Toast.makeText(
                    getApplicationContext(),
                    "DB 오픈 실패",
                    Toast.LENGTH_LONG
            ).show();
            e.printStackTrace();
            e.getMessage().toString();
        }
    }

    private void insertBook(String TABLE_NAME, String title, int price, String barcode){
        try {
            db.execSQL("" +
                    "insert into " + TABLE_NAME +
                    " ( title, price , barcode) " +
                    " values ( '" + title + "' , '" + price + "' , '"+ barcode +"'); ");
        }catch (Exception e){
            Toast.makeText(
                    getApplicationContext(),
                    "(동일한 도서가 존재합니다.)",
                    Toast.LENGTH_LONG
            ).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        Intent intent = getIntent();
        if(intent != null) {
            barcodeNumber = intent.getStringExtra("barcodeNumber");
        }

        webScraping = new WebScraping(barcodeNumber);
        webScraping.ThreadForwebconnect(barcodeNumber);

        openDatabase("store");
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        editTextISBN = (EditText) findViewById(R.id.editTextBarcode);
        Button buttonAddBook = (Button) findViewById(R.id.btnAddbook);
        Button buttonSetBookInfo = (Button) findViewById(R.id.btnSetBookInfo);

        buttonSetBookInfo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editTextTitle.setText(webScraping.getTitle());
                        editTextPrice.setText(""+webScraping.getPrice());
                        editTextISBN.setText(webScraping.getISBNnumber());
                    }
                }
        );

        buttonAddBook.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = editTextTitle.getText().toString();
                        int price = Integer.parseInt(editTextPrice.getText().toString());
                        String barcode = editTextISBN.getText().toString();
                        insertBook("books", title, price, barcode);

                        finish();

                    }
                }
        );

    }
}
