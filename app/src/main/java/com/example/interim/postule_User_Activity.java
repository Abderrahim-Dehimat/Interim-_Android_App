package com.example.interim;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class postule_User_Activity extends AppCompatActivity {
    EditText nomUtilisateur, prenomUtilisateur, villeUtilisateur, numeroTelephoneUtilisateur, emailUtilisateur;
    Button registerButton;
    private Uri pdfUri;
    private Calendar calendar;
    private TextInputEditText birthdate;
    private Uri cvUri, letterUri;
    private ExecutorService executorService; // Executor for background tasks

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_postule_user);

        executorService = Executors.newSingleThreadExecutor(); // Initialize the ExecutorService

        // Initialize views
        nomUtilisateur = findViewById(R.id.nom_utilisateur_edit_text);
        prenomUtilisateur = findViewById(R.id.prenom_utilisateur_edit_text);
        villeUtilisateur = findViewById(R.id.Ville_utilisateurEditText);
        numeroTelephoneUtilisateur = findViewById(R.id.telephone_utilisateur_edit_text);
        emailUtilisateur = findViewById(R.id.email_utilisateurET);
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
            DatePickerDialog datePickerDialog = new DatePickerDialog(postule_User_Activity.this, dateSetListener, year - 18, month, day);
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            datePickerDialog.show();
        });

        // Event handler for the register button
        registerButton.setOnClickListener(v -> {
            if (validateInputs()) {
                submitApplication();
            }
        });
    }

    private void submitApplication() {
        int jobOfferId = getIntent().getIntExtra("idOffre", -1);
        int userId = getIntent().getIntExtra("idUtilisateur", -1);

        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
            User user = db.userDAO().getUserById(userId);
            JobOffer jobOffer = db.jobOfferDAO().getJobOfferById(jobOfferId);

            if (user != null && jobOffer != null) {
                Application app = new Application();
                app.idOffre = jobOfferId;
                app.idUtilisateur = userId;
                app.nomCandidat = nomUtilisateur.getText().toString();
                app.prenomCandidat = prenomUtilisateur.getText().toString();
                app.dateCandidature = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? LocalDate.now().toString() : "";
                app.statut = "pending";
                app.reponseCandidat = "pending";

                db.applicationDAO().addApplication(app);
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Application submitted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Finish activity and return to previous screen
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Invalid User or Job Offer ID", Toast.LENGTH_LONG).show();
                });
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
                emailUtilisateur.getText().toString().isEmpty() || birthdate.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
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
            File pdfFile = savePdfToLocalStorage(uri, fileName);
            if (pdfFile != null) {
                if (requestCode == PICK_CV_REQUEST) {
                    cvUri = uri;
                    Toast.makeText(this, "CV Selected: " + fileName, Toast.LENGTH_SHORT).show();
                } else if (requestCode == PICK_LETTER_REQUEST) {
                    letterUri = uri;
                    Toast.makeText(this, "Letter Selected: " + fileName, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    //result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
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

    private File savePdfToLocalStorage(Uri uri, String fileName) {
        File file = new File(getFilesDir(), fileName);
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save PDF", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
