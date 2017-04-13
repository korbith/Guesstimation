package com.example.etrian.guesstimation;

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
        String incorrect1 = ((EditText)findViewById(R.id.incorrect1)).getText().toString();
        String incorrect2 = ((EditText)findViewById(R.id.incorrect2)).getText().toString();
        String incorrect3 = ((EditText)findViewById(R.id.incorrect3)).getText().toString();
        String correctAnswer = ((EditText)findViewById(R.id.correctanswer)).getText().toString();

        String QueryThis = String.format("Insert into Question (Question,A,B,C,D,Points,CorrectAnswer,CorrectAnswerPoints) Values ('%s','%s','%s','%s','%s','300','%s','500')", question, incorrect1, incorrect2, incorrect3, correctAnswer, correctAnswer);

        String result = ((ApplicationController) this.getApplication()).manipulateQuery(QueryThis);
        if (result.equals("query successful")) {
            Toast.makeText(AdminConsoleActivity.this, "Question Successfully Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AdminConsoleActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }




    }
}



