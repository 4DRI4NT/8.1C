package com.example.a81c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a81c.data.DatabaseHelper;
import com.example.a81c.data.User;
import com.example.a81c.data.Util;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button loginButton, signUpButton;
    EditText usernameText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //testing
//        Context context = getApplicationContext();
//        context.deleteDatabase(Util.DATABASE_NAME);

        //link elements to id
        usernameText = findViewById(R.id.usernameEditText);
        passwordText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        db = new DatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save primary key as variable
                String username = usernameText.getText().toString();

                //check for username/password match
                User user = db.loginCheck(usernameText.getText().toString(), passwordText.getText().toString());

                //filter invalid responses
                if (user == null) {
                    Toast.makeText(MainActivity.this, "Username and/or password incorrect", Toast.LENGTH_LONG).show();
                } else {
                    //create and start HomeActivity, passing primary key
                    Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                    homeIntent.putExtra("name", user.getFullname());

                    startActivity(homeIntent);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create and start SignUpActivity
                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}