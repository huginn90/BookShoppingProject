package com.iot.bookshoppingproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by CHJ on 2017-05-24.
 */

public class ClientBuyActivity extends AppCompatActivity{

    ImageView imageViewBook;
    TextView textViewTitle;
    TextView textViewPrice;
    String barcodeNumber;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientbuy);

        Intent intent = getIntent();
        if(intent != null) {
            barcodeNumber = intent.getStringExtra("barcodeNumber");
        }

        imageViewBook = (ImageView) findViewById(R.id.imageView);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        Button buttonBuy = (Button) findViewById(R.id.button_buy);
        Button buttonExit = (Button) findViewById(R.id.button_exit);


    }



}
