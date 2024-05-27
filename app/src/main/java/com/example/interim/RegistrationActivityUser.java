package com.example.interim;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Insert;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivityUser extends AppCompatActivity {

    EditText nomUtilisateur, prenomUtilisateur, villeUtilisateur, numeroTelephoneUtilisateur, emailUtilisateur, motDePasseUtilisateur;
    Button registerButton;

    private Calendar calendar;
    private TextInputEditText birthdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration_user);

        // declaring the views
        nomUtilisateur = findViewById(R.id.nom_utilisateur_edit_text);
        prenomUtilisateur = findViewById(R.id.prenom_utilisateur_edit_text);
        villeUtilisateur = findViewById(R.id.Ville_utilisateurEditText);
        numeroTelephoneUtilisateur = findViewById(R.id.telephone_utilisateur_edit_text);
        emailUtilisateur = findViewById(R.id.email_utilisateurET);
        motDePasseUtilisateur = findViewById(R.id.motDePasse_utilisateur);
        registerButton = findViewById(R.id.register_button_utilisateur);

        birthdate = findViewById(R.id.dateEditText);
        calendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateEditText();
            }
        };

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Calendar maxDate = (Calendar) calendar.clone();
                maxDate.set(Calendar.YEAR, year - 18);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivityUser.this, dateSetListener, year - 18, month, day);
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                datePickerDialog.show();
            }
        });
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
         //   Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          //  v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
          //  return insets;
        //});

        // saving the user inside the database
        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    // Insert a new user
                    User newUser = new User();
                    newUser.nom = nomUtilisateur.getText().toString();
                    newUser.prenom = prenomUtilisateur.getText().toString();
                    newUser.dateDeNaissance = birthdate.getText().toString();
                    newUser.ville = villeUtilisateur.getText().toString();
                    newUser.numeroTelephone = numeroTelephoneUtilisateur.getText().toString();
                    newUser.emailUtilisateur = emailUtilisateur.getText().toString();
                    newUser.motDePasse = motDePasseUtilisateur.getText().toString();
                    new Thread(() -> {
                        db.userDAO().addUser(newUser);
                    }).start();
                    Toast.makeText(getApplicationContext(), "Création du compte avec succès", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), LoginActivityUser.class);
                    startActivity(i);
                }
            }
        });

    }
    private void updateDateEditText() {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        birthdate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private boolean validateInputs() {
        if (nomUtilisateur.getText().toString().isEmpty() || prenomUtilisateur.getText().toString().isEmpty() ||
                villeUtilisateur.getText().toString().isEmpty() || numeroTelephoneUtilisateur.getText().toString().isEmpty() ||
                emailUtilisateur.getText().toString().isEmpty() || motDePasseUtilisateur.getText().toString().isEmpty() ||
                birthdate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}