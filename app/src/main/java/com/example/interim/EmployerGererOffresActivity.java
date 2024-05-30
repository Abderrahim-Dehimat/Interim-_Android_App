package com.example.interim;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmployerGererOffresActivity extends AppCompatActivity {

    TextView welcomeTxt;
    RecyclerView recyclerView;
    GererOffresAdapter gererOffresAdapter;
    private ExecutorService executorService;  // ExecutorService declaration
    private List<JobOffer> jobOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_gerer_offres);
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        welcomeTxt = findViewById(R.id.gererOffresTxt);
        recyclerView = findViewById(R.id.gererOffresRecyclerview);

        // Initialize the ExecutorService
        executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
            jobOffers = db.jobOfferDAO().getJobOfferByIdEmployeur(LoginActivityEmployeur.idEmp);

            runOnUiThread(() -> {
                gererOffresAdapter = new GererOffresAdapter(this, jobOffers);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(gererOffresAdapter);
            });
        });
    }
}
