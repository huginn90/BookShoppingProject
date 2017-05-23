package com.iot.bookshoppingproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.iot.bookshoppingproject.R.id.UserText;

public class BookList extends AppCompatActivity {

    ArrayList<RecycleData> recycleDataSet = new ArrayList<>();

    TextView textViewTt;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTt = (TextView) findViewById(UserText);
        Button button = (Button) findViewById(R.id.bt01);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ContentParcel content3 = (ContentParcel) bundle.getParcelable("title");

        if(content3.getTitle().equals("admin")){
            textViewTt.setText("[        모           드      -       관           리           자      ]");
            button.setVisibility(View.VISIBLE);
        }else {
            textViewTt.setText("[        모           드      -       사           용           자      ]");
            button.setVisibility(View.INVISIBLE);
        }


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Recyclerview);

        InsertData(15);

        recyclerView.setAdapter(new RecyclerAdapter(recycleDataSet));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



    }

    public void InsertData(int i) {
       for (int j = 0; j < i; j++) {
            RecycleData recycleData = new RecycleData();
            recycleData.setTextview_text("textView"+(j+1));
           recycleDataSet.add(recycleData);
        }
    }


}


