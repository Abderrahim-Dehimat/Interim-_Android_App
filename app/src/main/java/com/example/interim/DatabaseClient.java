package com.example.interim;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient instance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        this.context = context;

        // Create the Room database with pre-population and fallback
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "InterimPlusDatabase")
                .addCallback(prepopulateDatabase())
                .fallbackToDestructiveMigration()
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    private RoomDatabase.Callback prepopulateDatabase() {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Executors.newSingleThreadExecutor().execute(() -> {
                    AppDatabase database = getInstance(context).getAppDatabase();
                    // Insert initial data
                    database.userDAO().addUser(new User("Dehimat", "Abderrahim", "15/07/2001", "Montpellier", "0745396485", "dehimat.abderrahim@gmail.com", "123", "cv", "lettre"));
                    database.employerDAO().addEmployer(new Employer("DH interim", "dehimatinterim@gmail.com", "0745396485", "Montpellier", "https://www.linkedin.com/in/abderrahim-dehimat/", "123"));
                    database.jobOfferDAO().addJobOffer(new JobOffer(1, "logistique", "DH interim", "Vendeur", "Montpellier", "CDI", "3 mois", 1300, "on propose un poste de preparateur de commande pour l'entreprise DH interim"));
                    database.jobOfferDAO().addJobOffer(new JobOffer(1, "Grande distribution", "Darty", "Agent polyvalent", "Montpellier", "CDD", "6 mois", 1500, "on propose un poste de preparateur de commande pour l'entreprise Darty"));
                    database.jobOfferDAO().addJobOffer(new JobOffer(1, "Grande distribution", "Auchan", "Agent de caisse", "Toulouse", "CDD", "4 mois", 800, "on propose un poste de preparateur de commande pour l'entreprise Auchan"));
                    database.jobOfferDAO().addJobOffer(new JobOffer(1, "Grande distribution", "Aldi", "Agent polyvalent", "Toulouse", "CDD", "2 mois", 1300, "on propose un poste de preparateur de commande pour l'entreprise Aldi"));
                    database.jobOfferDAO().addJobOffer(new JobOffer(1, "Logistiquen", "DH interim", "Preparateur de commandes", "Paris", "CDD", "2 mois", 1300, "on propose un poste de preparateur de commande pour l'entreprise Aldi"));
                    database.jobOfferDAO().addJobOffer(new JobOffer(1, "Logistiquenn", "AB interim", "Inventorist", "Montpellier", "CDD", "2 mois", 1300, "on propose un poste de preparateur de commande pour l'entreprise Aldi"));
                });
            }
        };
    }
}
