package com.example.interim;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class searchFragment extends Fragment {
    EditText industrie, intitule_du_poste, zone_geo, type_contrat, periode;
    Button search;

    public searchFragment() {
        // Required empty public constructor
    }

    public static searchFragment newInstance(String param1, String param2) {
        searchFragment fragment = new searchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        industrie = view.findViewById(R.id.industrie);
        intitule_du_poste = view.findViewById(R.id.intitule_du_poste);
        zone_geo = view.findViewById(R.id.zone_geo);
        type_contrat = view.findViewById(R.id.type_contrat);
        periode = view.findViewById(R.id.periode);

        search = view.findViewById(R.id.search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Offers2Fragment offers2Fragment = new Offers2Fragment();

                Bundle args = new Bundle();
                args.putString("industrie", industrie.getText().toString());
                args.putString("intitule_du_poste", intitule_du_poste.getText().toString());
                args.putString("zone_geo", zone_geo.getText().toString());
                args.putString("type_contrat", type_contrat.getText().toString());
                args.putString("periode", periode.getText().toString());
                offers2Fragment.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_search, offers2Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}
