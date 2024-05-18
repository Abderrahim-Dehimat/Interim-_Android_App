package com.example.interim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    // the attributes
    private Context context;
    private ArrayList<OfferModelClass> offers;

    // the constructor

    public CustomAdapter(Context context, ArrayList<OfferModelClass> offers) {
        this.context = context;
        this.offers = offers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfferModelClass model = offers.get(position);
        holder.companyImg.setImageResource(model.getCompanyImg());
        holder.offerNameTxt.setText(model.getOfferName());
        holder.companyNameTxt.setText(model.getCompanyName());
        holder.offerInfosTxt.setText(model.getOfferInfos());

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView companyImg;
        private TextView offerNameTxt, companyNameTxt, offerInfosTxt;
        private Button applyBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyImg = itemView.findViewById(R.id.company_image_view);
            offerNameTxt = itemView.findViewById(R.id.offer_name_txt);
            companyNameTxt = itemView.findViewById(R.id.comapny_name_txt);
            offerInfosTxt = itemView.findViewById(R.id.offer_infos_txt);
            applyBtn = itemView.findViewById(R.id.apply_button);

        }
    }

}
