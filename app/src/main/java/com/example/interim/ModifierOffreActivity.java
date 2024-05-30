package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModifierOffreActivity extends AppCompatActivity {

    EditText industrie, nomeEntreprise, intitule, Zone, periode, Renumeration, description;
    Button modifierOffreBtn;
    Spinner spinnerTypeContrat;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_offre);
        executorService = Executors.newSingleThreadExecutor();

        setupUI();
        Intent intent = getIntent();
        int idOffre = intent.getIntExtra("idOffre", -1);
        if (idOffre != -1) {
            loadInitialData(idOffre);
        }
    }

    private void setupUI() {
        industrie = findViewById(R.id.industrie);
        nomeEntreprise = findViewById(R.id.nomeEntreprise);
        intitule = findViewById(R.id.intitule);
        Zone = findViewById(R.id.Zone);
        periode = findViewById(R.id.periode);
        Renumeration = findViewById(R.id.Renumeration);
        description = findViewById(R.id.description);
        modifierOffreBtn = findViewById(R.id.deposer_button_employeur);

        spinnerTypeContrat = findViewById(R.id.spinner_type_contrat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_contrat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeContrat.setAdapter(adapter);

        modifierOffreBtn.setOnClickListener(v -> saveOfferChanges());
    }

    private void loadInitialData(int idOffre) {
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
            JobOffer jobOffer = db.jobOfferDAO().getJobOfferById(idOffre);

            runOnUiThread(() -> {
                if (jobOffer != null) {
                    industrie.setText(jobOffer.industrieOffre);
                    nomeEntreprise.setText(jobOffer.nomEmployeur);
                    intitule.setText(jobOffer.intituleOffre);
                    Zone.setText(jobOffer.villeOffre);
                    periode.setText(jobOffer.periode);
                    Renumeration.setText(String.valueOf(jobOffer.remunerationOffre));
                    description.setText(jobOffer.descriptionOffre);
                }
            });
        });
    }

    private void saveOfferChanges() {
        int idOffre = getIntent().getIntExtra("idOffre", -1);
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
            JobOffer jobOffer = db.jobOfferDAO().getJobOfferById(idOffre);

            // Update fields
            jobOffer.industrieOffre = industrie.getText().toString();
            jobOffer.nomEmployeur = nomeEntreprise.getText().toString();
            jobOffer.intituleOffre = intitule.getText().toString();
            jobOffer.villeOffre = Zone.getText().toString();
            jobOffer.typeContrat = spinnerTypeContrat.getSelectedItem().toString();
            jobOffer.periode = periode.getText().toString();
            jobOffer.remunerationOffre = Double.parseDouble(Renumeration.getText().toString());
            jobOffer.descriptionOffre = description.getText().toString();

            // Update database
            db.jobOfferDAO().updateJobOffer(jobOffer);
            runOnUiThread(() -> {
                Toast.makeText(getApplicationContext(), "Offer successfully updated", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(getApplicationContext(), EmployerGererOffresActivity.class);
                startActivity(i2);
                //finish(); // Close this activity and return
            });
        });
    }
}
