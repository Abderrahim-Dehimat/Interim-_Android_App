package com.example.interim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class OffersFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<OfferModelClass> offersList;
    CustomAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OffersFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OffersFragment newInstance(String param1, String param2) {
        OffersFragment fragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_offers, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        // Data Source
        offersList = new ArrayList<>();
        offersList.add(new OfferModelClass(R.drawable.logo, "Preparateur de commandes", "DH interim", "Temps plein / 3 mois/ Montpellier "));
        offersList.add(new OfferModelClass(R.drawable.logo, "Inventoriste", "AB interim", "Temps partiel / 4 mois/ Toulouse "));
        offersList.add(new OfferModelClass(R.drawable.logo, "Conseiller de vente", "Darty", "Temps plein / 6 mois/ Paris "));
        offersList.add(new OfferModelClass(R.drawable.logo, "Vendeur", "Gifi", "Temps plein / 6 mois/ Nice "));
        offersList.add(new OfferModelClass(R.drawable.logo, "Agent de caisse", "Lidl", "Temps plein / 6 mois/ Nice "));
        offersList.add(new OfferModelClass(R.drawable.logo, "Employee polyvalent", "Auchan", "Temps plein / 6 mois/ Nice "));
        offersList.add(new OfferModelClass(R.drawable.logo, "Manager", "Action", "Temps plein / 6 mois/ Nice "));

        // Adapter
        adapter = new CustomAdapter(getContext(), offersList);
        RecyclerView.LayoutManager layoutManager = new
                LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}