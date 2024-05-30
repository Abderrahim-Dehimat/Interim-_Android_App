package com.example.interim;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Offers2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Offers2Fragment extends Fragment {

    private ExecutorService executorService;
    private RecyclerView recyclerView;
    private List<JobOffer> jobOffers;
    private JobOfferAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Offers2Fragment() {
        // Required empty public constructor
    }

    public static Offers2Fragment newInstance(String param1, String param2) {
        Offers2Fragment fragment = new Offers2Fragment();
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

        // Initialize the ExecutorService
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers2, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        // Perform database operations in the background
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getContext()).getAppDatabase();
            jobOffers = db.jobOfferDAO().gellAllJobOffer();

            // Update the UI on the main thread
            getActivity().runOnUiThread(() -> {
                // Initialize the adapter
                adapter = new JobOfferAdapter(getContext(), jobOffers);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            });
        });

        return view;
    }
}