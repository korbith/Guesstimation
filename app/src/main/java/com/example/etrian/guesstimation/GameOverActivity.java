package com.example.etrian.guesstimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {



    int finalScore;
    TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        setText();
        System.out.println(((ApplicationController) this.getApplication()).getScore());
    }

    public void setText() {
        finalScore = (((ApplicationController) this.getApplication()).getScore());
        score = (TextView) findViewById(R.id.scoreTextView);

        score.setText(Integer.toString(finalScore));
    }

    public void playAgainButtonPressed(View view){

        ((ApplicationController) this.getApplication()).setScore(0);
        Intent intent = new Intent(GameOverActivity.this, SelectTeamActivity.class);
        startActivity(intent);
        finish();

    }

}
