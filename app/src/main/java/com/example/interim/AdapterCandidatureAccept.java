package com.example.interim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterCandidatureAccept extends RecyclerView.Adapter<AdapterCandidatureAccept.ViewHolder> {

    private Context context;
    private List<JobOffer> applications;

    public AdapterCandidatureAccept(Context context, List<JobOffer> offers) {
        this.context = context;
        this.applications = offers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout5, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobOffer model = applications.get(position);
        holder.offerNameTxt.setText(model.intituleOffre);
        holder.companyNameTxt.setText(model.nomEmployeur);
        holder.offerInfosTxt.setText(model.typeContrat +" / "+model.periode+" / "+model.villeOffre);

        holder.detailsBtn.setOnClickListener(v -> {
            showDetailsDialog(model);
        });
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    private void showDetailsDialog(JobOffer jobOffer) {
        // You can replace this with your DialogFragment logic
        new android.app.AlertDialog.Builder(context)
                .setTitle(jobOffer.intituleOffre)
                .setMessage(jobOffer.descriptionOffre)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView offerNameTxt, companyNameTxt, offerInfosTxt;
        private Button detailsBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            offerNameTxt = itemView.findViewById(R.id.offer_name_txt);
            companyNameTxt = itemView.findViewById(R.id.comapny_name_txt);
            offerInfosTxt = itemView.findViewById(R.id.offer_infos_txt);
            detailsBtn = itemView.findViewById(R.id.modifier_button);
        }
    }
}
