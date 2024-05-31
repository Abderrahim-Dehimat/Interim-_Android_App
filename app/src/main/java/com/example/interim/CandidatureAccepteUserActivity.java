package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CandidatureAccepteUserActivity extends AppCompatActivity {

    TextView welcomeTxt;
    RecyclerView recyclerView;
    private ExecutorService executorService;
    private List<JobOffer> pendingApplications;
    private AdapterCandidatureAccept adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_candidature_accepte_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        welcomeTxt = findViewById(R.id.welcome);
        recyclerView = findViewById(R.id.recycler_view);
        // Initialize the ExecutorService
        executorService = Executors.newSingleThreadExecutor();
        Intent intent = getIntent();
        int idOffre = intent.getIntExtra("idOffre", -1);

        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
            pendingApplications  = db.applicationDAO().getAllAcceptedJobOffer(LoginActivityUser.idUtilisateur);

            runOnUiThread(() -> {
                adapter = new AdapterCandidatureAccept(this, pendingApplications);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            });
        });

    }
}