package com.example.interim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

public class ConsulterCandidaturesActivity extends AppCompatActivity {

    TextView welcomeTxt;
    RecyclerView recyclerView;
    ConsulterCandidaturesAdapter consulterCandidaturesAdapter;
    private ExecutorService executorService;  // ExecutorService declaration
    private List<Application> candidatures;
    int idOffre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consulter_candidatures);
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
        idOffre = intent.getIntExtra("idOffre", -1);
        Toast.makeText(getApplicationContext(), "id offre: "+ idOffre, Toast.LENGTH_SHORT).show();

        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase();
            candidatures = db.applicationDAO().getApplicationByJobOfferId(idOffre);

            runOnUiThread(() -> {
                consulterCandidaturesAdapter = new ConsulterCandidaturesAdapter(this, candidatures);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(consulterCandidaturesAdapter);
            });
        });



    }
}