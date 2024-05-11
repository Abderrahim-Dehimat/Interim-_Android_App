package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivityUser extends AppCompatActivity {
    ImageView appLogo;
    Button logInButton, RegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lo_gin_user);
        // declaring the views
        appLogo = findViewById(R.id.app_logo);
        logInButton = findViewById(R.id.buttonConnexion);
        RegisterButton = findViewById(R.id.RegisterButton);
        // Adding the functionalities
        appLogo.setImageResource(R.drawable.logo);
        // Register Button
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistrationActivityUser.class);
                startActivity(i);
            }
        });
    }
}