package com.iot.bookshoppingproject;

import android.content.Intent;
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

    public static final String LoginId = "admin";
    public static final String LiginPW = "admin";
    EditText NameInput;
    EditText PasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        NameInput = (EditText)findViewById(R.id.userNameInput);
        PasswordInput = (EditText)findViewById(R.id.passwordInput);

        Button loginButton = (Button)findViewById(R.id.LogInButton);
        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName =
                                NameInput.getText().toString();
                        String password =
                                PasswordInput.getText().toString();
                        if(LoginId.equals(userName) && LiginPW.equals(password)){
                            Intent intent = new Intent(
                                    getApplicationContext(),
                                    SignIn.class
                            );
                            startActivity(intent);
                        }else{
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
                                SignIn.class
                        );
                        startActivity(intent);
                    }
                }
        );
    }
}
