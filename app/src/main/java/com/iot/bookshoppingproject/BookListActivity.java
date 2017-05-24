package com.iot.bookshoppingproject;
// 1

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.iot.bookshoppingproject.R.id.UserText;
import static java.sql.DriverManager.println;

public class BookListActivity extends AppCompatActivity {

    ArrayList<Book> bookSet = new ArrayList<>();

    private static String DATABASE_NAME = "store";
    private static String TABLE_NAME = "books";
    TextView textViewTt;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);
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

        recyclerView = (RecyclerView) findViewById(R.id.Recyclerview);

        BooklistSetData(TABLE_NAME);
        recyclerAdapter = new RecyclerAdapter(bookSet);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecycleViewItemClickListener(getApplicationContext(), recyclerView,
                new RecycleViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intentitem = new Intent(getApplicationContext(), ClientBuyActivity.class);
                        startActivity(intentitem);
                    }
                    @Override
                    public void onItemLongClick(View v, int position) {
                    }
                }));

    }

    // 리사이클러뷰 리스트 업데이트를 위한 코드
    @Override
    protected void onResume() {
        super.onResume();
        bookSet.clear();
        recyclerAdapter.upDateItemList(BooklistSetData(TABLE_NAME));
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
                    " title text, " +
                    " price int, " +
                    " barcode text PRIMARY KEY);"
            );
        } catch (Exception e){
            e.printStackTrace();
            println(TABLE_NAME + " 테이블 생성 실패");
            println((e.getMessage().toString()));
        }
    }


    public ArrayList<Book> BooklistSetData(String TABLE_NAME) {
        String SQL = "select title, price, barcode " + " from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(SQL, null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();

            String title = cursor.getString(0);
            int price = cursor.getInt(1);
            String barcode = cursor.getString(2);
            Book book = new Book(title, price, barcode);
            bookSet.add(book);
        }

        return bookSet;
    }

    // 아이템 클릭 리스너 코드부분
    public static class RecycleViewItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

        private OnItemClickListener mListener;
        private GestureDetector mGestureDetector;

        public RecycleViewItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            this.mListener = listener;

            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null && mListener != null) {
                        mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(child, rv.getChildAdapterPosition(child));
                return true;
            }
            return false;
        }

        public interface OnItemClickListener {
            void onItemClick(View v, int position);

            void onItemLongClick(View v, int position);
        }
    }
}


