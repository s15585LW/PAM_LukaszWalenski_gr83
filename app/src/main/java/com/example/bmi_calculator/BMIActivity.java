package com.example.bmi_calculator;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class BMIActivity extends AppCompatActivity {

    EditText input1, input2, input3;
    TextView wynik, wynik_opis;
    Button button, button2;
    Switch plec;

    double BMI, wzrost, waga, wiek, KorM;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        input1 = findViewById(R.id.editText);
        input2 = findViewById(R.id.editText2);
        input3 = findViewById(R.id.editText3);

        wynik = (TextView) findViewById(R.id.result);
        button = (Button) findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        wynik_opis = (TextView) findViewById(R.id.result2);
        plec = findViewById(R.id.Sex);
        KorM = 0.0;

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BMI > 0) {
                    wzrost = Integer.parseInt(input2.getText().toString());
                    waga = Integer.parseInt(input1.getText().toString());
                    wiek = Integer.parseInt(input3.getText().toString());
                    if (plec.isChecked()){ KorM = 1.0;}

                    Intent intent = new Intent();
                    intent.putExtra("BMI_value", BMI);
                    intent.putExtra("WZROST_value", wzrost);
                    intent.putExtra("WAGA_value", waga);
                    intent.putExtra("WIEK_value", wiek);
                    intent.putExtra("PLEC_value", KorM);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (input1.getText().length() == 0 || input2.getText().length() == 0) {
                    Snackbar.make(v, "Uzupełnij Dane", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    wzrost = Integer.parseInt(input2.getText().toString());
                    waga = Integer.parseInt(input1.getText().toString());

                    if (wzrost == 0 || waga == 0) {
                        Snackbar.make(v, "Wprowadź wartość większa od 0", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {

                        BMI = ((waga) /  ((wzrost * wzrost) / 10000));

                        wynik.setText("BMI: " + String.format("%.2f", BMI));
                        if (BMI < 18.5) {
                            wynik_opis.setText("Niedowaga");
                        } else if (BMI < 24.9) {
                            wynik_opis.setText("Waga prawidłowa");
                        } else if (BMI < 29.9) {
                            wynik_opis.setText("Nadwaga");
                        } else {
                            wynik_opis.setText("Otyłość");
                        }
                    }
                }
            }
        });

    }

}
