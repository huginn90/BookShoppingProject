package com.iot.bookshoppingproject;
// 1
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BarcodeActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextPrice;
    EditText editTextISBN;
    String barcodeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        Intent intent = getIntent();
        if(intent != null) {
            barcodeNumber = intent.getStringExtra("barcodeNumber");
        }

        final WebScraping webScraping = new WebScraping(barcodeNumber);
        webScraping.ThreadForwebconnect(barcodeNumber);

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

                    }
                }
        );


    }
}
