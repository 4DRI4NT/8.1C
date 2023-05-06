package com.example.a81c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a81c.data.DatabaseHelper;
import com.example.a81c.data.User;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText nameText, usernameText, passwordText, confirmPasswordText;
    Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //link elements to id
        nameText = findViewById(R.id.newNameEditText);
        usernameText = findViewById(R.id.newUsernameEditText);
        passwordText = findViewById(R.id.newPasswordEditText);
        confirmPasswordText = findViewById(R.id.confirmPasswordEditText);
        createAccountButton = findViewById(R.id.createAccountButton);

        db = new DatabaseHelper(this);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get check for duplicate user
                Boolean dupCheck = db.userDupCheck(nameText.getText().toString());

                String password = passwordText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();

                //catch invalid responses
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                } else if (dupCheck) {
                    Toast.makeText(SignUpActivity.this, "An account is already under this name", Toast.LENGTH_LONG).show();
                } else if ((TextUtils.isEmpty(nameText.getText())) | (TextUtils.isEmpty(usernameText.getText())) | (TextUtils.isEmpty(passwordText.getText()))) {
                    Toast.makeText(SignUpActivity.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                } else {
                    //define and get user elements
                    String name, username;
                    name = nameText.getText().toString();
                    username = usernameText.getText().toString();

                    //combine to user and add to database
                    long result = db.insertUser(new User(name, username, password, ""));

                    //confirm result
                    if (result > 0) {
                        Toast.makeText(SignUpActivity.this, "Account creation successful", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(SignUpActivity.this, "Account creation unsuccessful", Toast.LENGTH_LONG).show();
                    }

                    finish();
                }
            }
        });
    }
}