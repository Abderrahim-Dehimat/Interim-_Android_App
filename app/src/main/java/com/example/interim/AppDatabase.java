package com.example.interim;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Employer.class, JobOffer.class, Application.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract EmployerDAO employerDAO();
    public abstract JobOfferDAO jobOfferDAO();
    public abstract ApplicationDAO applicationDAO();

    // Migration from version 1 to version 2
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `employeur` (`id_employeur` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nom_employeur` TEXT, `email_employeur` TEXT, `numero_telephone_employeur` TEXT, `ville_employeur` TEXT, `lien_publique_employeur` TEXT, `mot_de_passe_employeur` TEXT)");
        }
    };

    // Correcting MIGRATION_2_3 based on your structure
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create JobOffer table
            database.execSQL("CREATE TABLE IF NOT EXISTS `offre` (`id_offre` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id_employeur` INTEGER NOT NULL, `nom_industrie` TEXT, `nom_employeur` TEXT, `intitule_offre` TEXT, `ville_offre` TEXT, `type_contrat` TEXT, `periode_offre` TEXT, `remuneration_offre` REAL, `description_offre` TEXT, FOREIGN KEY(`id_employeur`) REFERENCES `employeur`(`id_employeur`) ON DELETE CASCADE)");
            // Create Application table
            database.execSQL("CREATE TABLE IF NOT EXISTS `candidature` (`id_candidature` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `id_offre` INTEGER NOT NULL, `id_utilisateur` INTEGER NOT NULL, `date_candidature` TEXT, `statut_candidature` TEXT, `reponse_candidat` TEXT, FOREIGN KEY(`id_offre`) REFERENCES `offre`(`id_offre`) ON DELETE CASCADE, FOREIGN KEY(`id_utilisateur`) REFERENCES `utilisateur`(`id_utilisateur`) ON DELETE CASCADE)");
        }
    };

    // Example migration adding fields to User
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Adding new columns to the utilisateur table
            database.execSQL("ALTER TABLE `utilisateur` ADD COLUMN `cv_utilisateur` TEXT");
            database.execSQL("ALTER TABLE `utilisateur` ADD COLUMN `lettre_motivation_utilisateur` TEXT");
        }
    };

}

