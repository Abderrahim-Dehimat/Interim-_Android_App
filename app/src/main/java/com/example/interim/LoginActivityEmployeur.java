package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivityEmployeur extends AppCompatActivity {

    ImageView logo3;
    EditText emailLogInEmp, passwordLogInEmp;
    Button logInBtnEmployeur, registerBtnEmployeur;
    public static int idEmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_employeur);

        logo3 = findViewById(R.id.app_logo3);
        emailLogInEmp = findViewById(R.id.email_utilisateurET);
        passwordLogInEmp = findViewById(R.id.motDePasse_utilisateur);
        logInBtnEmployeur = findViewById(R.id.buttonConnexionEmp);
        registerBtnEmployeur = findViewById(R.id.RegisterButtonEmp);

        logo3.setImageResource(R.drawable.logo8);

        registerBtnEmployeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistrationActivityEmployee.class);
                startActivity(i);
            }
        });

        logInBtnEmployeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailEmployer = emailLogInEmp.getText().toString();
                String passwordEmployer = passwordLogInEmp.getText().toString();
                logInEmployer(emailEmployer, passwordEmployer);
            }
        });

    }

    private void logInEmployer(String email, String password) {
        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        new Thread(() -> {
            Employer employer = db.employerDAO().getEmployerByEmailAndPassword(email, password);
            runOnUiThread(() -> {
                if (employer != null) {
                    Toast.makeText(getApplicationContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), welcome_employer.class);
                    idEmp = employer.idEmployeur;
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

}