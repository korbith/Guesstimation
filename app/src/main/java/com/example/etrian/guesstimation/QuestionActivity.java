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
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int sec=0;
    boolean wait = true;
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
        //return wait;
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
            Toast.makeText(QuestionActivity.this, "Found Values!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Log.e("Exception:",ex.getMessage());

        }

    }

    public void startRound() {

        setQuestionNum();
        getValues();
        Log.i("Check",Integer.toString(questionStringArray.size()));
        Log.i("Check",Integer.toString(numOfQuestions));

        //FORLOOP INSTEAD OF WHILE

        for(int i = 0; i < (numOfQuestions - 1); i++) {

            TextView questionNumEditText = (TextView) findViewById(R.id.questionNumTestView);
            questionNumEditText.setText("Question " + (i+1));
            questionString = questionStringArray.get(i);
            answer1 = answer1Array.get(i);
            answer2 = answer2Array.get(i);
            answer3 = answer3Array.get(i);
            answer4 = answer4Array.get(i);
            correctAnswer = correctAnswerArray.get(i);
            setQuestion();
            timer(10);
                try{
                    Log.i("Check","sleepy");
                    Thread.sleep(10000);
                } catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }








        }
        //rounds have ended! Go to results page
    }
}

