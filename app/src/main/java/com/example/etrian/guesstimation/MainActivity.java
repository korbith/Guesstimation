package com.example.etrian.guesstimation;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.ResultSet;


public class MainActivity extends AppCompatActivity {

    public void adminLoginPressed(View view) {

        EditText email = (EditText) findViewById(R.id.emailEditText);
        EditText pass = (EditText) findViewById(R.id.passEditText);

        String QueryThis = String.format("Select * from Admin where Email = '%s'", email.getText().toString());
        System.out.println(QueryThis);
        System.out.println(((ApplicationController) this.getApplication()).pass);
        System.out.println(pass.getText().toString());
        String checkpass = ((ApplicationController) this.getApplication()).selectQueryLogin(QueryThis);
        System.out.println(checkpass);
        try {
            if ((pass.getText().toString()).equals(checkpass)) {
                    Intent intent = new Intent(MainActivity.this, AdminConsoleActivity.class);
                    startActivity(intent);
                    finish();
            }


        } catch (Exception ex) {

        }
    }


    public void logInAdminPressed(View view) {

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.loginLayout);
        TextView playButton = (TextView) findViewById(R.id.playNowButton);
        playButton.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);

    }
    public void playNowPressed(View view) {

        Intent intent = new Intent(MainActivity.this, SelectTeamActivity.class);
        startActivity(intent);
        finish();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView guessLogo = (ImageView) findViewById(R.id.guessLogo);
        TextView playNowButton = (TextView) findViewById(R.id.playNowButton);
        TextView logInAdminButton = (TextView) findViewById(R.id.loginTextView);

        guessLogo.setTranslationY(-1000f);
        playNowButton.setAlpha(0f);
        logInAdminButton.setAlpha(0f);

        guessLogo.animate().translationY(0f).setDuration(2000);
        playNowButton.animate().alpha(1f).setDuration(3000);
        logInAdminButton.animate().alpha(1f).setDuration(3500);
    }



}
