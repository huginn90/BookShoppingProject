package com.iot.bookshoppingproject;
// 1

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

import java.util.ArrayList;

import static com.iot.bookshoppingproject.R.id.UserText;

public class BookListActivity extends AppCompatActivity {

    /*private static String DATABASE_NAME = "store";*/
    private static String TABLE_NAME = "books";
    ArrayList<Book> bookSet = new ArrayList<>();
    TextView textViewTt;
    BookDBHelper dbHelper;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);
        openDatabase();

        textViewTt = (TextView) findViewById(UserText);
        Button button = (Button) findViewById(R.id.button_addbook);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final ContentParcel content3 = (ContentParcel) bundle.getParcelable("title");

        if (content3.getTitle().equals("admin")) {
            textViewTt.setText("[        모           드      -       관           리           자      ]");
            button.setVisibility(View.VISIBLE);
        } else {
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
                        startActivity(intentAddBook);
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
                                // 상세화면으로 아이템 정보를 넘김.
                                Intent intentitem = new Intent(getApplicationContext(), ClientBuyActivity.class);
                                Book book = bookSet.get(position);
                                intentitem.putExtra("barcodeNumber", book.getBookbarcode());
                                intentitem.putExtra("booktitle", book.getBookTitle());
                                intentitem.putExtra("bookprice", book.getBookPrice());
                                intentitem.putExtra("username", content3.getTitle());
                                startActivity(intentitem);
                            }

                            @Override
                            public void onItemLongClick(View v, int position) {
                            }
                        }
                )
        );

    }

    // 리사이클러뷰 리스트 업데이트를 위한 코드
    @Override
    protected void onResume() {
        super.onResume();
        bookSet.clear();
        recyclerAdapter.upDateItemList(BooklistSetData(TABLE_NAME));
    }

    private void openDatabase(){
        dbHelper = new BookDBHelper(this);
        db = dbHelper.getWritableDatabase();
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


