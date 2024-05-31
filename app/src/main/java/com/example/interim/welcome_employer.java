package com.example.interim;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class welcome_employer extends AppCompatActivity {
    Button deposerOffre, gererOffre, gererCandidature;
    TextView welcome;
    private ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_employer);
        //variable
        deposerOffre=findViewById(R.id.first_button);
        gererOffre=findViewById(R.id.second_button);
        gererCandidature=findViewById(R.id.third_button);
        welcome = findViewById(R.id.bienvenue_text_view);
        // Initialize the ExecutorService
        executorService = Executors.newSingleThreadExecutor();

        // database
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
            Employer employer = db.employerDAO().getEmployerById(LoginActivityEmployeur.idEmp);

            runOnUiThread(() -> {
                welcome.setText(employer.nomEmployeur);
            });
        });



        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        deposerOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), depo_offre.class);
                startActivity(i);
            }
        });

        gererOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EmployerGererOffresActivity.class);
                startActivity(i);
            }
        });

        gererCandidature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CandidaturesAcceptesActivity.class);
                startActivity(i);
            }
        });

    }
}