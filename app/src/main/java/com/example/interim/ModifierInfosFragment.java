package com.example.interim;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.Executors;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;

public class ModifierInfosFragment extends Fragment {

    private TextInputEditText nomEditText, prenomEditText, dateEditText, villeEditText, telephoneEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button selectCvButton, selectPdfButton, registerButton;
    private Calendar myCalendar;
    private ExecutorService executorService;
    private DatePickerDialog.OnDateSetListener date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executorService = Executors.newSingleThreadExecutor();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modifier_infos, container, false);

        nomEditText = view.findViewById(R.id.nom_utilisateur_edit_text);
        prenomEditText = view.findViewById(R.id.prenom_utilisateur_edit_text);
        dateEditText = view.findViewById(R.id.dateEditText);
        villeEditText = view.findViewById(R.id.Ville_utilisateurEditText);
        telephoneEditText = view.findViewById(R.id.telephone_utilisateur_edit_text);
        emailEditText = view.findViewById(R.id.email_utilisateurET);
        passwordEditText = view.findViewById(R.id.motDePasse_utilisateur);
        confirmPasswordEditText = view.findViewById(R.id.confiramtion_mot_de_passe_utilisateur);
        selectCvButton = view.findViewById(R.id.select_cv_button);
        selectPdfButton = view.findViewById(R.id.select_pdf_button);
        registerButton = view.findViewById(R.id.register_button_utilisateur);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });

        loadUserInfo();

        return view;
    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void loadUserInfo() {
        // Using executorService to perform database operations off the main thread
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getContext()).getAppDatabase();
            User user = db.userDAO().getUserById(LoginActivityUser.idUtilisateur);

            if (user != null) {
                // Switch to the main thread to update UI components
                getActivity().runOnUiThread(() -> {
                    // Populate UI fields with user data
                    nomEditText.setText(user.nom);
                    prenomEditText.setText(user.prenom);
                    dateEditText.setText(user.dateDeNaissance);
                    villeEditText.setText(user.ville);
                    telephoneEditText.setText(user.numeroTelephone);
                    emailEditText.setText(user.emailUtilisateur);
                });
            }
        });
    }



    private void saveUserInfo() {
        // Retrieve user info from EditText fields
        String nom = nomEditText.getText().toString().trim();
        String prenom = prenomEditText.getText().toString().trim();
        String dateDeNaissance = dateEditText.getText().toString().trim();
        String ville = villeEditText.getText().toString().trim();
        String numeroTelephone = telephoneEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate password fields
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update user info in the database
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getContext()).getAppDatabase();
            User user = db.userDAO().getUserById(LoginActivityUser.idUtilisateur);

            user.nom = nom;
            user.prenom = prenom;
            user.dateDeNaissance = dateDeNaissance;
            user.ville = ville;
            user.numeroTelephone = numeroTelephone;
            user.emailUtilisateur = email;
            user.motDePasse = password;

            db.userDAO().updateUser(user);

            // Update UI on the main thread
            getActivity().runOnUiThread(() -> {
                // Show success message
                Toast.makeText(getContext(), "Informations modifiées avec succès", Toast.LENGTH_SHORT).show();
            });
        });
    }
}
