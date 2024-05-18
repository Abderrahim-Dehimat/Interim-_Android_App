package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivityUser extends AppCompatActivity {
    ImageView appLogo;
    Button logInButton, RegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lo_gin_user);
        // declaring the views
        appLogo = findViewById(R.id.app_logo3);
        logInButton = findViewById(R.id.buttonConnexionEmp);
        RegisterButton = findViewById(R.id.RegisterButtonEmp);
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