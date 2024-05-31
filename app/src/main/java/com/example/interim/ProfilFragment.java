package com.example.interim;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {
    TextView nom_text_view,prenom_text_view;
    Button first_button,second_button,third_button;
    private ExecutorService executorService;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
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
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view= inflater.inflate(R.layout.fragment_profil, container, false);
        nom_text_view=view.findViewById(R.id.nom_text_view);
        prenom_text_view=view.findViewById(R.id.prenom_text_view);
        first_button=view.findViewById(R.id.first_button);
        second_button=view.findViewById(R.id.second_button);
        third_button=view.findViewById(R.id.third_button);

        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of ModifierInfosFragment
                ModifierInfosFragment modifierInfosFragment = new ModifierInfosFragment();

                // Create a FragmentManager
                FragmentManager manager = getActivity().getSupportFragmentManager();

                // Begin a FragmentTransaction
                FragmentTransaction transaction = manager.beginTransaction();

                // Replace the current fragment with ModifierInfosFragment
                transaction.replace(R.id.fragment_profile, modifierInfosFragment);

                // Add the transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });


        // database
        executorService.execute(() -> {
            AppDatabase db = DatabaseClient.getInstance(getContext()).getAppDatabase();
            User user = db.userDAO().getUserById(LoginActivityUser.idUtilisateur);

            // Update the UI on the main thread
            getActivity().runOnUiThread(() -> {
                nom_text_view.setText(user.nom);
                prenom_text_view.setText(user.prenom);
            });
        });

        return view;
    }
}