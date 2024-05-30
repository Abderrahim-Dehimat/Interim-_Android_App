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

public class GererOffresAdapter extends RecyclerView.Adapter<GererOffresAdapter.ViewHolder> {

    private Context context;
    private List<JobOffer> offers;
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // Executor for background tasks


    public GererOffresAdapter(Context context, List<JobOffer> offers) {
        this.context = context;
        this.offers = offers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout2, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobOffer model = offers.get(position);
        holder.offerNameTxt.setText(model.intituleOffre);
        holder.companyNameTxt.setText(model.nomEmployeur);
        holder.offerInfosTxt.setText(model.typeContrat +" / "+model.periode+" / "+model.villeOffre);

        // Set listeners
        holder.consulterBtn.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ConsulterCandidaturesActivity.class);
            i.putExtra("idOffre", model.idOffre);
            ((AppCompatActivity) context).startActivity(i);

        });

        holder.modifierBtn.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ModifierOffreActivity.class);
            i.putExtra("idOffre", model.idOffre);
            ((AppCompatActivity) context).startActivity(i);
        });
        holder.supprimerBtn.setOnClickListener(v -> {
            // Execute database operation in the background
            executorService.execute(() -> {
                try {
                    AppDatabase db = DatabaseClient.getInstance(v.getContext()).getAppDatabase();
                    db.jobOfferDAO().deleteOfferById(model.idOffre);

                    // Remove the item from the list and notify adapter on the UI thread
                    ((AppCompatActivity) context).runOnUiThread(() -> {
                        int adapterPosition = holder.getAdapterPosition();
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            offers.remove(adapterPosition);
                            notifyItemRemoved(adapterPosition);
                            Toast.makeText(context, "Offer deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    ((AppCompatActivity) context).runOnUiThread(() ->
                            Toast.makeText(context, "Failed to delete offer: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView offerNameTxt, companyNameTxt, offerInfosTxt;
        private Button consulterBtn, modifierBtn, supprimerBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            offerNameTxt = itemView.findViewById(R.id.offer_name_txt);
            companyNameTxt = itemView.findViewById(R.id.comapny_name_txt);
            offerInfosTxt = itemView.findViewById(R.id.offer_infos_txt);
            consulterBtn = itemView.findViewById(R.id.consulter_button);
            modifierBtn = itemView.findViewById(R.id.modifier_button);
            supprimerBtn = itemView.findViewById(R.id.supprimer_button);
        }
    }
}
