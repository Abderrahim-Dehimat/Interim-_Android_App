package com.example.interim;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivityUser extends AppCompatActivity {
    EditText nomUtilisateur, prenomUtilisateur, villeUtilisateur, numeroTelephoneUtilisateur, emailUtilisateur, motDePasseUtilisateur;
    Button registerButton;

    private Calendar calendar;
    private TextInputEditText birthdate;

    private String cvBase64;
    private String letterBase64;

    private final ActivityResultLauncher<Intent> cvPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            handleFileSelection(result.getData(), PICK_CV_REQUEST);
                        }
                    });

    private final ActivityResultLauncher<Intent> letterPickerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            handleFileSelection(result.getData(), PICK_LETTER_REQUEST);
                        }
                    });

    private static final int PICK_CV_REQUEST = 1;
    private static final int PICK_LETTER_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);

        // Initialisation des vues
        nomUtilisateur = findViewById(R.id.nom_utilisateur_edit_text);
        prenomUtilisateur = findViewById(R.id.prenom_utilisateur_edit_text);
        villeUtilisateur = findViewById(R.id.Ville_utilisateurEditText);
        numeroTelephoneUtilisateur = findViewById(R.id.telephone_utilisateur_edit_text);
        emailUtilisateur = findViewById(R.id.email_utilisateurET);
        motDePasseUtilisateur = findViewById(R.id.motDePasse_utilisateur);
        registerButton = findViewById(R.id.register_button_utilisateur);

        birthdate = findViewById(R.id.dateEditText);
        calendar = Calendar.getInstance();

        Button selectCvButton = findViewById(R.id.select_cv_button);
        Button selectPdfButton = findViewById(R.id.select_pdf_button);

        selectCvButton.setOnClickListener(v -> openFilePicker(PICK_CV_REQUEST));
        selectPdfButton.setOnClickListener(v -> openFilePicker(PICK_LETTER_REQUEST));

        final DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateEditText();
        };

        birthdate.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            Calendar maxDate = (Calendar) calendar.clone();
            maxDate.set(Calendar.YEAR, year - 18);
            DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivityUser.this, dateSetListener, year - 18, month, day);
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            datePickerDialog.show();
        });

        AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
        registerButton.setOnClickListener(v -> {
            if (validateInputs()) {
                // Insert a new user
                User newUser = new User(
                        nomUtilisateur.getText().toString(),
                        prenomUtilisateur.getText().toString(),
                        birthdate.getText().toString(),
                        villeUtilisateur.getText().toString(),
                        numeroTelephoneUtilisateur.getText().toString(),
                        emailUtilisateur.getText().toString(),
                        motDePasseUtilisateur.getText().toString(),
                        cvBase64,
                        letterBase64
                );
                new Thread(() -> db.userDAO().addUser(newUser)).start();
                Toast.makeText(getApplicationContext(), "Création du compte avec succès", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), LoginActivityUser.class);
                startActivity(i);
            }
        });
    }

    private void updateDateEditText() {
        String dateFormat = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        birthdate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private boolean validateInputs() {
        if (nomUtilisateur.getText().toString().isEmpty() ||
                prenomUtilisateur.getText().toString().isEmpty() ||
                villeUtilisateur.getText().toString().isEmpty() ||
                numeroTelephoneUtilisateur.getText().toString().isEmpty() ||
                emailUtilisateur.getText().toString().isEmpty() ||
                motDePasseUtilisateur.getText().toString().isEmpty() ||
                birthdate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tous les champs doivent être remplis", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void openFilePicker(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (requestCode == PICK_CV_REQUEST) {
            cvPickerLauncher.launch(intent);
        } else if (requestCode == PICK_LETTER_REQUEST) {
            letterPickerLauncher.launch(intent);
        }
    }

    private void handleFileSelection(Intent data, int requestCode) {
        Uri uri = data.getData();
        if (uri != null) {
            String fileName = getFileName(uri);
            String base64String = convertPdfToBase64(uri);
            if (base64String != null) {
                if (requestCode == PICK_CV_REQUEST) {
                    cvBase64 = base64String;
                    Toast.makeText(this, "CV sélectionné : " + fileName, Toast.LENGTH_SHORT).show();
                } else if (requestCode == PICK_LETTER_REQUEST) {
                    letterBase64 = base64String;
                    Toast.makeText(this, "Lettre sélectionnée : " + fileName, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private String convertPdfToBase64(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] pdfBytes = outputStream.toByteArray();
            return Base64.encodeToString(pdfBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Échec de la conversion du PDF", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


}