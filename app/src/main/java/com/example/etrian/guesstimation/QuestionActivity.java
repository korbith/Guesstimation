package com.example.etrian.guesstimation;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int sec=0;
    boolean wait = true;
    int numOfQuestions;
    String answer1;
    String answer2;
    String answer3;
    String answer4;
    String correctAnswer;
    int points = 300;
    int correctAnswerPoints = 500;
    String questionString;
    boolean questionSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        /*String string = ((ApplicationController) this.getApplication()).selectQueryLogin("Select * from Question");
        ResultSet questions = ((ApplicationController) this.getApplication()).results;
        try {
            ArrayList<String> questionsList = new ArrayList<String>();
            while (questions.next()){
                int i = 1;
                while (i <= 3) {
                    questionsList.add(questions.getString(i++));
                }
            }

        } catch (Exception ex) {
            System.out.println("Couldn't loop through data pulled from DB");
        }

        setQuestion();*/
        startRound();
    }

    public void setQuestionNum() {
        String QueryThis = "Select count(*) from Question";
         numOfQuestions = ((ApplicationController) this.getApplication()).getRowCount(QueryThis);
    }

    public void timer(int seconds){

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        final TextView time = (TextView)findViewById(R.id.timeTextView);
        mProgressBar.setProgress(sec);
        mProgressBar.setMax(seconds);
        mCountDownTimer=new CountDownTimer(11000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ sec + millisUntilFinished);
                sec++;
                mProgressBar.setProgress(sec);
                if((mProgressBar.getMax() - 5) <= sec){
                    int count = Math.abs(sec - 10);
                    time.setTextColor(Color.RED);
                    time.setText(Integer.toString(count));
                }

            }

            @Override
            public void onFinish() {
                sec++;
                mProgressBar.setProgress(sec);
                time.setText(null);
                sec = 0;
                setQuestion();
                wait = false;
                questionSet = false;

            }
        };
        mCountDownTimer.start();
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

    public void startRound() {

        setQuestionNum();

        //FORLOOP INSTEAD OF WHILE

        while(numOfQuestions > 0) {

            TextView questionNumEditText = (TextView) findViewById(R.id.questionNumTestView);
            questionNumEditText.setText("Question " + numOfQuestions);
            numOfQuestions--;
            ResultSet questions = getQuestions();
            try {
                while (questions.next()) {

                    if (questionSet == false) {
                        questionString = questions.getString("Question");
                        answer1 = questions.getString("A");
                        answer2 = questions.getString("B");
                        answer3 = questions.getString("C");
                        answer4 = questions.getString("D");
                        correctAnswer = questions.getString("CorrectAnswer");
                        setQuestion();
                        questionSet = true;
                    }
                    timer(10);
                    wait(10000);
                    if (wait == false){
                        startRound();
                    }
                }
                Toast.makeText(QuestionActivity.this, "Round Ended!", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Log.e("Exception:",ex.getMessage());

            }


        }
    }
}

