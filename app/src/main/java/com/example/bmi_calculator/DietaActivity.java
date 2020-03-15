package com.example.bmi_calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DietaActivity extends AppCompatActivity {
    Double BMI_status;
    TextView BMI_txt, BMI_przepisy;
    Button button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dieta);
        BMI_txt = findViewById(R.id.BMI_txt2);
        BMI_przepisy = findViewById(R.id.BMI_przepis);
        button_back = findViewById(R.id.button_back);

        Intent intent = getIntent();
        BMI_status = intent.getDoubleExtra("BMI_value", 0.0);
        BMI_txt.setText("Twoje BMI wynosi: " + String.format("%.2f", BMI_status));

        if (BMI_status < 18.5) {
            BMI_przepisy.setText("Niedowaga - jedz posiłki wysoko węglowodanowe");
        } else if (BMI_status < 24.9) {
            BMI_przepisy.setText("Waga prawidłowa - jedz posiłki zrównoważone");
        } else if (BMI_status < 29.9) {
            BMI_przepisy.setText("Nadwaga - jedz posiłki lekkie");
        } else {
            BMI_przepisy.setText("Otyłość - Szlaban na jedzenie");
        }
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
