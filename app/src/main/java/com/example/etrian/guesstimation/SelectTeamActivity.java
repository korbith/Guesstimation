package com.example.etrian.guesstimation;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class SelectTeamActivity extends AppCompatActivity {

    public void selectTeamPressed(View view) {

        Spinner spin = (Spinner) findViewById(R.id.teamSpinner);
        String spinnerText = spin.getSelectedItem().toString();

        ((ApplicationController) this.getApplication()).setYourTeam(spinnerText);

        System.out.println(spinnerText);

        EditText classCodeEditText = (EditText) findViewById(R.id.classCodeTextView);
        int classCode = Integer.parseInt(classCodeEditText.getText().toString());

        ((ApplicationController) this.getApplication()).setclassCode(classCode);
        ((ApplicationController) this.getApplication()).setScore(0);
        Intent intent = new Intent(SelectTeamActivity.this, QuestionActivity.class);
        startActivity(intent);
        finish();
    }



    public void addItemsOnSpinner() {

        Spinner spin = (Spinner) findViewById(R.id.teamSpinner);
        List<String> list = new ArrayList<String>();
        list.add("Team Red");
        list.add("Team Blue");
        list.add("Team Green");
        list.add("Team Yellow");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_team);
        addItemsOnSpinner();
    }
}
