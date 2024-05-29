package com.example.interim;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class depo_offre extends AppCompatActivity {
    EditText industrie,nomeEntreprise,intitule,Zone,periode,Renumeration,description;
    Button deposer_button_employeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_depo_offre);
        //variable
        industrie=findViewById(R.id.industrie);
        nomeEntreprise=findViewById(R.id.nomeEntreprise);
        intitule=findViewById(R.id.intitule);
        Zone=findViewById(R.id.Zone);
        periode=findViewById(R.id.periode);
        Renumeration=findViewById(R.id.Renumeration);
        description=findViewById(R.id.description);
        deposer_button_employeur=findViewById(R.id.deposer_button_employeur);

        Spinner spinnerTypeContrat = findViewById(R.id.spinner_type_contrat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_contrat, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeContrat.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}