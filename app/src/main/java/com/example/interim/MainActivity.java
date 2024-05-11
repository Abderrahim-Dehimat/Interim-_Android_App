package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView appLogo;
    TextView bienvenueTxt, bienvenue2Txt, lastTxt, connectezVousTxt;
    Button firstBtn, secondBtn, thirdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaring the views
        appLogo = findViewById(R.id.app_logo);
        bienvenueTxt = findViewById(R.id.bienvenue_text_view);
        bienvenue2Txt = findViewById(R.id.bienvenue2_text_view);
        lastTxt = findViewById(R.id.last_text_view);
        connectezVousTxt = findViewById(R.id.connectez_vous_txt);
        firstBtn = findViewById(R.id.first_button);
        secondBtn = findViewById(R.id.second_button);
        thirdBtn = findViewById(R.id.third_button);

        // Adding the functionalities
        appLogo.setImageResource(R.drawable.logo);

        // Explorez l'application button
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivityUser.class);
                startActivity(i);
            }
        });
        thirdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LocationActivity.class);
                startActivity(i);
            }
        });


    }
}