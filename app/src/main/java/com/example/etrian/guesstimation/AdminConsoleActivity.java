package com.example.etrian.guesstimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdminConsoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_console);
    }

    public void submitQuestionPressed(View view) {

        String question = ((EditText)findViewById(R.id.questionEditText)).getText().toString();
        String correctAnswer = ((EditText)findViewById(R.id.correctanswer)).getText().toString();

        String QueryThis = String.format("Insert into Question (Question,A,B,C,D,Points,CorrectAnswer,CorrectAnswerPoints) Values ('%s','%s','%s','%s','%s','300','%s','500')", question, "", "", "", correctAnswer, correctAnswer);

        String result = ((ApplicationController) this.getApplication()).manipulateQuery(QueryThis);
        if (result.equals("query successful")) {
            Toast.makeText(AdminConsoleActivity.this, "Question Successfully Added", Toast.LENGTH_SHORT).show();
            ((EditText) findViewById(R.id.questionEditText)).setText("");
            ((EditText) findViewById(R.id.correctanswer)).setText("");
        } else {
            Toast.makeText(AdminConsoleActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    public void goBackPressed(View view) {

        Intent intent = new Intent(AdminConsoleActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}



