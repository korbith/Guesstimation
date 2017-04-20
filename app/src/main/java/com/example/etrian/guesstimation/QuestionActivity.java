package com.example.etrian.guesstimation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class QuestionActivity extends AppCompatActivity {

    int sec=0;
    int i =0;
    int numOfQuestions;
    String answer1;
    List<String> answer1Array = new ArrayList<String>();
    String answer2;
    List<String> answer2Array = new ArrayList<String>();
    String answer3;
    List<String> answer3Array = new ArrayList<String>();
    String answer4;
    List<String> answer4Array = new ArrayList<String>();
    String correctAnswer;
    List<String> correctAnswerArray = new ArrayList<String>();
    int points = 300;
    int correctAnswerPoints = 500;
    String questionString;
    List<String> questionStringArray = new ArrayList<String>();
    boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        setActionBar();
        getValues();
        startRound();
    }

    public void setActionBar(){

        getSupportActionBar().setTitle(((ApplicationController) this.getApplication()).getYourTeam());
        if (((ApplicationController) this.getApplication()).getYourTeam() == "Team Red") {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));
        } else if (((ApplicationController) this.getApplication()).getYourTeam() == "Team Blue") {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
        } else if (((ApplicationController) this.getApplication()).getYourTeam() == "Team Green") {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
        } else if (((ApplicationController) this.getApplication()).getYourTeam() == "Team Yellow") {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.YELLOW));
        }

    }

    public void setQuestionNum() {
        String QueryThis = "Select count(*) from Question";
         numOfQuestions = ((ApplicationController) this.getApplication()).getRowCount(QueryThis);
    }

    public ResultSet getQuestions() {
        String QueryThis = "Select * from Question";
        ResultSet data = ((ApplicationController) this.getApplication()).getQuestions(QueryThis);
        return data;
    }

    public void setQuestion() {
        TextView question = (TextView) findViewById(R.id.questionTextView);
        RadioButton option1 = (RadioButton) findViewById(R.id.answer1);
        RadioButton option2 = (RadioButton) findViewById(R.id.answer2);
        RadioButton option3 = (RadioButton) findViewById(R.id.answer3);
        RadioButton option4 = (RadioButton) findViewById(R.id.answer4);
        question.setText(questionString);
        option1.setText(answer1);
        option2.setText(answer2);
        option3.setText(answer3);
        option4.setText(answer4);

    }

    public void getValues() {
        ResultSet questions = getQuestions();
        try {
            while (questions.next()) {
                    questionStringArray.add(questions.getString("Question"));
                    answer1Array.add(questions.getString("A"));
                    answer2Array.add(questions.getString("B"));
                    answer3Array.add(questions.getString("C"));
                    answer4Array.add(questions.getString("D"));
                    correctAnswerArray.add(questions.getString("CorrectAnswer"));
                    setQuestion();

            }
        } catch (Exception ex) {
            Log.e("Exception:",ex.getMessage());
        }

    }

    public void answerSelected(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int checkedRadio = radioGroup.getCheckedRadioButtonId();
        RadioButton radio = (RadioButton) findViewById(checkedRadio);
        String answer = radio.getText().toString();

        if (answer.equals(correctAnswer)) {
            ((ApplicationController) this.getApplication()).updateScore(correctAnswerPoints);
            Toast.makeText(QuestionActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            System.out.println(((ApplicationController) this.getApplication()).getScore());
            startRound();
        } else {
            ((ApplicationController) this.getApplication()).updateScore(points);
            Toast.makeText(QuestionActivity.this, "Aww... that was incorrect :/", Toast.LENGTH_SHORT).show();
            System.out.println(((ApplicationController) this.getApplication()).getScore());
            startRound();
        }


    }

    public void startRound() {
        if (i < questionStringArray.size()){
            System.out.println(i);
            if (!finished) {
                setQuestionNum();
                TextView questionNumEditText = (TextView) findViewById(R.id.questionNumTestView);
                questionNumEditText.setText("Question " + (i + 1));
                questionString = questionStringArray.get(i);
                answer1 = answer1Array.get(i);
                System.out.println(answer1);
                answer2 = answer2Array.get(i);
                System.out.println(answer2);
                answer3 = answer3Array.get(i);
                System.out.println(answer3);
                answer4 = answer4Array.get(i);
                System.out.println(answer4);
                correctAnswer = correctAnswerArray.get(i);
                setQuestion();
                i++;
            }
        } else {
            finished = true;
            Intent intent = new Intent(QuestionActivity.this, GameOverActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

