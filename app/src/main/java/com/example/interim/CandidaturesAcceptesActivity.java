package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CandidaturesAcceptesActivity extends AppCompatActivity {

    TextView welcomeTxt;
    RecyclerView recyclerView;
    CandidaturesAcceptesAdapter candidaturesAcceptesAdapter;
    private ExecutorService executorService;  // ExecutorService declaration
    private List<Application> candidatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_candidatures_acceptes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom);
            return insets;
        });

        welcomeTxt = findViewById(R.id.welcomeTxt);
        recyclerView = findViewById(R.id.recyclerview);
        // Initialize the ExecutorService
        executorService = Executors.newSingleThreadExecutor();
        Intent intent = getIntent();
        int idOffre = intent.getIntExtra("idOffre", -1);

        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
            candidatures = db.applicationDAO().getAcceptedApplication();

            runOnUiThread(() -> {
                candidaturesAcceptesAdapter = new CandidaturesAcceptesAdapter(this, candidatures);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(candidaturesAcceptesAdapter);
            });
        });

    }
}