package com.example.bmi_calculator;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText input1, input2;
    TextView wynik, wynik_opis;
    Button button;
 
    double BMI, wzrost, waga;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input1 = (EditText) findViewById(R.id.editText);
        input2 = (EditText) findViewById(R.id.editText2);
        wynik = (TextView) findViewById(R.id.result);
        button = (Button) findViewById(R.id.button);
        wynik_opis = (TextView) findViewById(R.id.result2);

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
