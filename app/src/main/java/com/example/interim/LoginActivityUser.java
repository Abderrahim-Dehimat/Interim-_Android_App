package com.example.interim;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

public class LoginActivityUser extends AppCompatActivity {
    private ExecutorService executorService;
    ImageView appLogo;
    EditText emailUtilisateur, motDePasseUtilisateur;
    Button logInButton, RegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lo_gin_user);
        // declaring the views
        emailUtilisateur = findViewById(R.id.email_utilisateurET);
        motDePasseUtilisateur = findViewById(R.id.motDePasse_utilisateur);
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

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailUtilisateur.getText().toString();
                String password = motDePasseUtilisateur.getText().toString();
                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {
        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        new Thread(() -> {
            User user = db.userDAO().getUserByEmailAndPassword(email, password);
            runOnUiThread(() -> {
                if (user != null) {
                    Toast.makeText(getApplicationContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), OffersFragment); // Redirect to your home activity
//                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

}