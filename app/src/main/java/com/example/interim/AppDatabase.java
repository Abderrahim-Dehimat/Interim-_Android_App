package com.example.interim;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Employer.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract EmployerDAO employerDAO();

    // Migration from version 1 to version 2
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `employeur` (`id_employeur` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nom_employeur` TEXT, `email_employeur` TEXT, `numero_telephone_employeur` TEXT, `ville_employeur` TEXT, `lien_publique_employeur` TEXT, `mot_de_passe_employeur` TEXT)");
        }
    };
}

