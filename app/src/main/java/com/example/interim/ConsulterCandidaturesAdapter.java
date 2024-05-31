package com.example.interim;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsulterCandidaturesAdapter extends RecyclerView.Adapter<ConsulterCandidaturesAdapter.ViewHolder> {

    private Context context;
    private List<Application> candidatures;
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // Executor for background tasks


    public ConsulterCandidaturesAdapter(Context context, List<Application> candidatures) {
        this.context = context;
        this.candidatures = candidatures;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout3, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Application model = candidatures.get(position);
        holder.nomCandidat.setText(model.nomCandidat + " " + model.prenomCandidat);
        holder.dateCandidature.setText(model.dateCandidature);

        holder.accepterBtn.setOnClickListener(v -> {
            updateCandidatureStatus(model.idCandidature, "accepted");
        });

        holder.refuserBtn.setOnClickListener(v -> {
            updateCandidatureStatus(model.idCandidature, "rejected");
        });

        holder.consulterBtn.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), CansulterActivity.class);
            i.putExtra("idUtilisateur", model.idUtilisateur);
            i.putExtra("idOffre", model.idOffre);
            ((AppCompatActivity) context).startActivity(i);
        });

    }

    private void updateCandidatureStatus(int candidatureId, String status) {
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(context).getAppDatabase();
            db.applicationDAO().updateStatus(candidatureId, status);

            ((AppCompatActivity) context).runOnUiThread(() -> {
                Toast.makeText(context, "Status updated to: " + status, Toast.LENGTH_SHORT).show();
                // Optionally notify dataset changed if the UI needs to refresh
                notifyDataSetChanged();
            });
        });
    }


    @Override
    public int getItemCount() {
        return candidatures.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomCandidat, dateCandidature, offerInfosTxt;
        private Button consulterBtn, accepterBtn, refuserBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomCandidat = itemView.findViewById(R.id.offer_name_txt);
            dateCandidature = itemView.findViewById(R.id.comapny_name_txt);
            consulterBtn = itemView.findViewById(R.id.consulter_button);
            accepterBtn = itemView.findViewById(R.id.modifier_button);
            refuserBtn = itemView.findViewById(R.id.supprimer_button);
        }
    }
}

