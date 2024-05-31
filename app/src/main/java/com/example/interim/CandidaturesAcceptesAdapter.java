package com.example.interim;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
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
import java.util.function.Consumer;

public class CandidaturesAcceptesAdapter extends RecyclerView.Adapter<CandidaturesAcceptesAdapter.ViewHolder> {

    private Context context;
    private List<Application> candidatures;
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // Executor for background tasks

    public CandidaturesAcceptesAdapter(Context context, List<Application> candidatures) {
        this.context = context;
        this.candidatures = candidatures;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout4, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Application model = candidatures.get(position);
        holder.nomCandidat.setText(model.nomCandidat + " " + model.prenomCandidat);
        holder.dateCandidature.setText(model.dateCandidature);

        holder.consulterBtn.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), CansulterActivity.class);
            i.putExtra("idUtilisateur", model.idUtilisateur);
            i.putExtra("idOffre", model.idOffre);
            ((AppCompatActivity) context).startActivity(i);
        });

        holder.contacterBtn.setOnClickListener(v -> {
            getEmailForUser(model.idUtilisateur, email -> {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact from Employer regarding your application");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear " + model.nomCandidat + ",\n\n");

                try {
                    context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void getEmailForUser(int userId, Consumer<String> callback) {
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(context.getApplicationContext()).getAppDatabase();
            User user = db.userDAO().getUserById(userId);
            String email = user != null ? user.emailUtilisateur : "";
            // Switch back to the UI Thread
            new Handler(Looper.getMainLooper()).post(() -> {
                callback.accept(email);
            });
        });
    }

    @Override
    public int getItemCount() {
        return candidatures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomCandidat, dateCandidature;
        private Button consulterBtn, contacterBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomCandidat = itemView.findViewById(R.id.offer_name_txt);
            dateCandidature = itemView.findViewById(R.id.comapny_name_txt);
            consulterBtn = itemView.findViewById(R.id.consulter_button);
            contacterBtn = itemView.findViewById(R.id.modifier_button);
        }
    }
}
