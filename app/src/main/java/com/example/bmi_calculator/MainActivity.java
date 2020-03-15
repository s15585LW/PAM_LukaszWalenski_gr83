package com.example.bmi_calculator;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.DomainCombiner;

public class MainActivity extends AppCompatActivity {

    Button btn_gotoBMI, btn_gotoDieta, btn_gotoKalorie, btn_Exit;
    Double BMI_status, WAGA_status, WZROST_status, PLEC_status, WIEK_status;
    TextView Txt_v_bmi;

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BMI_status = 0.0;
        Txt_v_bmi = findViewById(R.id.BMI_txt2);
        btn_gotoBMI = findViewById(R.id.btn_BMI);
        btn_gotoDieta = findViewById(R.id.btn_dieta);
        btn_gotoKalorie = findViewById(R.id.btn_Kalorie);
        btn_Exit = findViewById(R.id.btn_Exit);

        btn_gotoBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchBMIActivity();
            }
        });

        btn_gotoDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BMI_status == 0.0){
                    Snackbar.make(v, "Oblicz swoje BMI", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                   launchDietaActivity();
                }
            }
        });

        btn_gotoKalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BMI_status == 0.0){
                    Snackbar.make(v, "Oblicz swoje BMI", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    launchKalorieActivity();
                }
            }
        });

        btn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }

    private void launchBMIActivity() {

        Intent intent = new Intent(this, BMIActivity.class);
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);

    }

    private void launchDietaActivity() {

        Intent intent = new Intent(this, DietaActivity.class);
        intent.putExtra("BMI_value", BMI_status);
        startActivity(intent);
    }

    private void launchKalorieActivity() {

        Intent intent = new Intent(this, KalorieActivity.class);
        intent.putExtra("WAGA_value", WAGA_status);
        intent.putExtra("WZROST_value", WZROST_status);
        intent.putExtra("PLEC_value", PLEC_status);
        intent.putExtra("WIEK_value", WIEK_status);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                BMI_status = data.getDoubleExtra("BMI_value", 0.0);
                WAGA_status = data.getDoubleExtra("WAGA_value", 0.0);
                WZROST_status = data.getDoubleExtra("WZROST_value", 0.0);
                PLEC_status = data.getDoubleExtra("PLEC_value", 0.0);
                WIEK_status = data.getDoubleExtra("WIEK_value", 0.0);

                Txt_v_bmi.setText("Twoje BMI wynosi: " + String.format("%.2f", BMI_status));
            }
        }
    }
}
