package com.iot.bookshoppingproject;

import android.content.Intent;
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

public class LogInActivity extends AppCompatActivity {

    private static String TABLE_NAME = "customer";
    public static final String LoginId = "admin";
    public static final String LiginPW = "admin";

    EditText NameInput;
    EditText PasswordInput;
    private BookDBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        openDatabase();

        NameInput = (EditText)findViewById(R.id.userNameInput);
        PasswordInput = (EditText)findViewById(R.id.passwordInput);

        Button loginButton = (Button)findViewById(R.id.LogInButton);



        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = NameInput.getText().toString();
                        String password = PasswordInput.getText().toString();
                        if(LoginId.equals(userName) && LiginPW.equals(password)){
                            Intent intent = new Intent(getApplicationContext(),
                                    BookListActivity.class
                            );
                            ContentParcel content3 = new ContentParcel(userName, password);
                            intent.putExtra("title", content3);
                            startActivity(intent);
                        } else if(executeLogIn(TABLE_NAME, userName, password)){
                            Intent intent = new Intent(
                                    getApplicationContext(),
                                    BookListActivity.class
                            );
                            ContentParcel content3 = new ContentParcel(userName, password);
                            intent.putExtra("title", content3);
                            startActivity(intent);
                         //   startActivity(intent);
                        } else{
                            Toast.makeText(
                                    getApplicationContext(),
                                    "로그인에 실패했습니다.",
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                    }
                }
        );

        Button signInButton = (Button)findViewById(R.id.SignInButton);
        signInButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(
                                getApplicationContext(),
                                SignInActivity.class
                        );
                        startActivity(intent);
                    }
                }
        );
    }

    private void openDatabase(){
        dbHelper = new BookDBHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    private boolean executeLogIn(String TABLE_NAME, String _id, String password){
        Cursor cursor = db.rawQuery(
                "select _id, password from " + TABLE_NAME +
                        " where (_id like '" + _id +
                        "') AND (password like '" + password + "')",
                null
        );
        if(cursor.getCount() == 1){
            return true;
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "아이디 혹은 비번이 일치하지 않습니다.",
                    Toast.LENGTH_LONG
            ).show();
        }
        return false;
    }
}
