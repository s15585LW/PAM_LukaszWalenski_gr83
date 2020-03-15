package com.example.bmi_calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class KalorieActivity extends AppCompatActivity {
    TextView kalorie, waga_value, wzrost_value, sex_value;
    Double waga, wzrost, wiek, plec;
    Button btn_gotoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalorie);

        Intent intent = getIntent();
        waga = intent.getDoubleExtra("WAGA_value", 0.0);
        wzrost = intent.getDoubleExtra("WZROST_value", 0.0);
        wiek = intent.getDoubleExtra("WIEK_value", 0.0);
        plec = intent.getDoubleExtra("PLEC_value", 0.0);

        kalorie = findViewById(R.id.txt_valueKalorie);
        waga_value = findViewById(R.id.txt_valueWaga);
        wzrost_value = findViewById(R.id.txt_valueWzrost);
        btn_gotoBack = findViewById(R.id.button_back);
        sex_value = findViewById(R.id.txt_sex);

        if (plec == 0.0){
            kalorie.setText(String.format("%.0f", (655.1+(9.567*waga)+(1.85*wzrost)+(4.68*wiek))));
            sex_value.setText("♀");
        }else{
            kalorie.setText(String.format("%.0f", (66.47+(13.7*waga)+(5.0*wzrost)+(4.68*wiek))));
            sex_value.setText("♂");
        }

        waga_value.setText(String.format("%.0f", waga));
        wzrost_value.setText(String.format("%.0f", wzrost));

        btn_gotoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
