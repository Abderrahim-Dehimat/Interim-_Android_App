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

public class OffersFragment extends Fragment {

    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private List<JobOffer> jobOffers;
    private JobOfferAdapter adapter;
    public String cityUser;

    public static OffersFragment newInstance(String city) {
        OffersFragment fragment = new OffersFragment();
        Bundle args = new Bundle();
        args.putString("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityUser = getArguments().getString("city");
        }
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getContext()).getAppDatabase();
            jobOffers = db.jobOfferDAO().getJobOfferByVille(cityUser);

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
