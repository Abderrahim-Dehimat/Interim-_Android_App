package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivityEmployeur extends AppCompatActivity {

    ImageView logo3;
    EditText emailLogInEmp, passwordLogInEmp;
    Button logInBtnEmployeur, registerBtnEmployeur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_employeur);

        logo3 = findViewById(R.id.app_logo3);
        emailLogInEmp = findViewById(R.id.emailEmployeurET);
        passwordLogInEmp = findViewById(R.id.motDePasseEmp);
        logInBtnEmployeur = findViewById(R.id.buttonConnexionEmp);
        registerBtnEmployeur = findViewById(R.id.RegisterButtonEmp);

        logo3.setImageResource(R.drawable.logo);

        registerBtnEmployeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistrationActivityEmployee.class);
                startActivity(i);
            }
        });
    }
}