package com.example.interim;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Offers2Fragment extends Fragment {

    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private List<JobOffer> jobOffers;
    private JobOfferAdapter adapter;

    public Offers2Fragment() {
        // Required empty public constructor
    }

    public static Offers2Fragment newInstance(String param1, String param2) {
        Offers2Fragment fragment = new Offers2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offers2, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        // Retrieve search criteria from arguments, handle null case
        Bundle args = getArguments();
        String industrie = args != null ? args.getString("industrie", "") : "";
        String intituleDuPoste = args != null ? args.getString("intitule_du_poste", "") : "";
        String zoneGeo = args != null ? args.getString("zone_geo", "") : "";
        String typeContrat = args != null ? args.getString("type_contrat", "") : "";
        String periode = args != null ? args.getString("periode", "") : "";

        // Perform database operations in the background
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getContext()).getAppDatabase();

            // Determine which query to use based on provided criteria
            if (!industrie.isEmpty() && !intituleDuPoste.isEmpty() && !zoneGeo.isEmpty() && !typeContrat.isEmpty() && !periode.isEmpty()) {
                jobOffers = db.jobOfferDAO().getJobOfferByAllFilters(industrie, intituleDuPoste, zoneGeo, typeContrat, periode);
            } else if (!industrie.isEmpty() && !intituleDuPoste.isEmpty() && !zoneGeo.isEmpty() && !typeContrat.isEmpty()) {
                jobOffers = db.jobOfferDAO().getJobOfferByIndustrieIntituleVilleType(industrie, intituleDuPoste, zoneGeo, typeContrat);
            } else if (!industrie.isEmpty() && !intituleDuPoste.isEmpty() && !zoneGeo.isEmpty()) {
                jobOffers = db.jobOfferDAO().getJobOfferByIndustrieIntituleVille(industrie, intituleDuPoste, zoneGeo);
            } else if (!industrie.isEmpty() && !intituleDuPoste.isEmpty()) {
                jobOffers = db.jobOfferDAO().getJobOfferByIndustrieIntitule(industrie, intituleDuPoste);
            } else if (!industrie.isEmpty()) {
                jobOffers = db.jobOfferDAO().getJobOfferByNomIndustrie(industrie);
            } else if (!intituleDuPoste.isEmpty()) {
                jobOffers = db.jobOfferDAO().getJobOfferByIntitule(intituleDuPoste);
            } else if (!zoneGeo.isEmpty()) {
                jobOffers = db.jobOfferDAO().getJobOfferByVille(zoneGeo);
            } else if (!typeContrat.isEmpty()) {
                jobOffers = db.jobOfferDAO().getJobOfferByTypeContrat(typeContrat);
            } else if (!periode.isEmpty()) {
                jobOffers = db.jobOfferDAO().getJobOfferByPeriode(periode);
            } else {
                jobOffers = db.jobOfferDAO().gellAllJobOffer();
            }

            // Update the UI on the main thread
            getActivity().runOnUiThread(() -> {
                adapter = new JobOfferAdapter(getContext(), jobOffers);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            });
        });

        return view;
    }
}
