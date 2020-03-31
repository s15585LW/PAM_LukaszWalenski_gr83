package com.example.bmi_calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KoronaMenuActivity extends AppCompatActivity {

    Button button_back, button_chart, button_quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korona_menu);
        button_quiz = findViewById(R.id.btn_quiz);
        button_back = findViewById(R.id.button_back);
        button_chart = findViewById(R.id.btn_chart);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchQuizActivity();
            }
        });
        button_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchChartaActivity();
            }
        });

    }

    private void launchChartaActivity() {

        Intent intent = new Intent(this, ChartsActivity.class);
        startActivity(intent);
    }

    private void launchQuizActivity() {

        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

}
