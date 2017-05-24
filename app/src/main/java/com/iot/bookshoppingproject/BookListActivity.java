package com.iot.bookshoppingproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.iot.bookshoppingproject.R.id.UserText;
import static java.sql.DriverManager.println;

public class BookListActivity extends AppCompatActivity {

    ArrayList<RecycleData> recycleDataSet = new ArrayList<>();
    private static String DATABASE_NAME = "store";
    private static String TABLE_NAME = "books";
    TextView textViewTt;
    String userName;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDatabase(DATABASE_NAME);
        createTable(TABLE_NAME);

        textViewTt = (TextView) findViewById(UserText);
        Button button = (Button) findViewById(R.id.bt01);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ContentParcel content3 = (ContentParcel) bundle.getParcelable("title");

        if(content3.getTitle().equals("admin")){
            textViewTt.setText("[        모           드      -       관           리           자      ]");
            button.setVisibility(View.VISIBLE);
        }else {
            textViewTt.setText("[        모           드      -       사           용           자      ]");
            button.setVisibility(View.INVISIBLE);
        }

        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentAddBook = new Intent(
                                getApplicationContext(), CameraActivity.class
                        );
                        startActivityForResult(intentAddBook, 100);
                    }
                }
        );


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Recyclerview);

        InsertData(TABLE_NAME, 15);

        recyclerView.setAdapter(new RecyclerAdapter(recycleDataSet));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



    }

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

    private void createTable(String TABLE_NAME){
        try {
            db.execSQL("create table if not exists " + TABLE_NAME +
                    " ( " +
                    " title text PRIMARY KEY, " +
                    " price text, " +
                    " barcode text);"
            );
        } catch (Exception e){
            e.printStackTrace();
            println(TABLE_NAME + " 테이블 생성 실패");
            println((e.getMessage().toString()));
        }
    }


    public void InsertData(String TABLE_NAME, int i) {
       for (int j = 0; j < i; j++) {
           String SQL = "select price " + " from " + TABLE_NAME;
           Cursor cursor = db.rawQuery(SQL, null);
//           int recordCount = cursor.getCount();
           cursor.moveToNext();
           String title = cursor.getString(0);
           RecycleData recycleData = new RecycleData();
           recycleData.setTextview_text(title);
           recycleDataSet.add(recycleData);
        }
    }
}

