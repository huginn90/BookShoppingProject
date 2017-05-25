package com.iot.bookshoppingproject;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CHJ on 2017-05-24.
 */

public class ClientBuyActivity extends AppCompatActivity{

    ImageView imageViewBook;
    TextView textViewTitle;
    TextView textViewPrice;
    String barcodeNumber;
    String bookTitle;
    int bookPrice;
    SQLiteDatabase db;
    private Bitmap bitmap;

    private PopupActivity mPopupActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientbuy);

        Intent intent = getIntent();
        if(intent != null) {
            barcodeNumber = intent.getStringExtra("barcodeNumber");
            bookTitle = intent.getStringExtra("booktitle");
            bookPrice = intent.getIntExtra("bookprice", 0);
        }

        openDatabase("store");

        try {
            getBookimageThread(barcodeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imageViewBook = (ImageView) findViewById(R.id.imageView);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        Button buttonBuy = (Button) findViewById(R.id.button_buy);
        Button buttonExit = (Button) findViewById(R.id.button_exit);

        imageViewBook.setImageBitmap(bitmap);
        textViewTitle.setText(bookTitle);
        textViewPrice.setText(bookPrice+" 원");

    }

    public void onClickView(View v) {
        switch (v.getId()) {
            case R.id.button_buy:
                mPopupActivity = new PopupActivity(this,
                        "[구매 확인]", // 제목
                        "구매하시겠습니까?", // 내용
                        leftListener, // 왼쪽 버튼 이벤트
                        rightListener); // 오른쪽 버튼 이벤트
                mPopupActivity.show();
                break;
        }
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            // TODO : 디비에서 해당되는 책을 delete
            Toast.makeText(getApplicationContext(), "왼쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();
            mPopupActivity.dismiss();
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "취소버튼 클릭",
                    Toast.LENGTH_SHORT).show();
            mPopupActivity.dismiss();
        }
    };


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

    public void getBookimageThread(final String barcode) throws InterruptedException {
        Thread thread = new Thread() {
            public void run() {
                try {
                    URL url = new URL("http://t1.daumcdn.net/book/KOR"+barcode);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.connect();

                    InputStream inputStream = con.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread.join();
    }



}
