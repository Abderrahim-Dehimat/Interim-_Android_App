package com.example.interim;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivityEmployee extends AppCompatActivity {

    TextView firstTxt;
    Button saveButton;
    EditText nomEmp, mailEmp, telEmp, villeEmp, lienEmp, motDePasseEmp, confirmationMdp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration_employee);

        firstTxt = findViewById(R.id.first_text_view);
        saveButton = findViewById(R.id.enregister_button_employeur);
        nomEmp = findViewById(R.id.nom_employeur_edit_text);
        mailEmp = findViewById(R.id.email_employeur_edit_text);
        telEmp = findViewById(R.id.telephone_utilisateur_edit_text);
        villeEmp = findViewById(R.id.VilleEditText);
        lienEmp = findViewById(R.id.lien_public_employer);
        motDePasseEmp = findViewById(R.id.motDePasse_utilisateur);
        confirmationMdp = findViewById(R.id.confiramtion_mot_de_passe_employeur);


    }
}