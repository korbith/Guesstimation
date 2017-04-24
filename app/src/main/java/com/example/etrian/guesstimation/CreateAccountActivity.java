package com.example.etrian.guesstimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void CreateAccountButtonPressed(View view) {
        String email = ((EditText) findViewById(R.id.emailEditText)).getText().toString();
        String pass = ((EditText) findViewById(R.id.passEditText)).getText().toString();

        String QueryThis = String.format("Insert into Admin (email,pass) Values ('%s','%s')", email, pass);

        String result = ((ApplicationController) this.getApplication()).manipulateQuery(QueryThis);
        if (result.equals("query successful")) {
            Toast.makeText(CreateAccountActivity.this, "Admin Account Created", Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.emailEditText)).setText("");
            ((EditText) findViewById(R.id.passEditText)).setText("");
        } else {
            Toast.makeText(CreateAccountActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }


    }
}

