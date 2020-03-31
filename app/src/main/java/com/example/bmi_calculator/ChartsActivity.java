package com.example.bmi_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class ChartsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        Button back = findViewById(R.id.button_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        WebView meWebView = findViewById(R.id.chart_browser);
        WebSettings webSettings = meWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        meWebView.loadUrl("file:///android_asset/Chart.html");
    }


}
