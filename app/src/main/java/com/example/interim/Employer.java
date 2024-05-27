package com.example.interim;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "employeur")
public class Employer {
    @ColumnInfo(name = "id_employeur")
    @PrimaryKey(autoGenerate = true)
    public int idEmployeur;

    @ColumnInfo(name = "nom_employeur")
    public String nomEmployeur;

    @ColumnInfo(name = "email_employeur")
    public String emailEmployeur;

    @ColumnInfo(name = "numero_telephone_employeur")
    public String numTelEmployeur;

    @ColumnInfo(name = "ville_employeur")
    public String villeEmployeur;

    @ColumnInfo(name = "lien_publique_employeur")
    public String lienPubliqueEmployeur;

    @ColumnInfo(name = "mot_de_passe_employeur")
    public String motDePasseEmployeur;

    @Ignore
    public Employer() {
    }

    public Employer(String nomEmployeur, String emailEmployeur, String numTelEmployeur, String villeEmployeur, String lienPubliqueEmployeur, String motDePasseEmployeur) {
        this.nomEmployeur = nomEmployeur;
        this.emailEmployeur = emailEmployeur;
        this.numTelEmployeur = numTelEmployeur;
        this.villeEmployeur = villeEmployeur;
        this.lienPubliqueEmployeur = lienPubliqueEmployeur;
        this.motDePasseEmployeur = motDePasseEmployeur;
    }

    public int getIdEmployeur() {
        return idEmployeur;
    }

    public String getNomEmployeur() {
        return nomEmployeur;
    }

    public void setNomEmployeur(String nomEmployeur) {
        this.nomEmployeur = nomEmployeur;
    }

    public String getEmailEmployeur() {
        return emailEmployeur;
    }

    public void setEmailEmployeur(String emailEmployeur) {
        this.emailEmployeur = emailEmployeur;
    }

    public String getNumTelEmployeur() {
        return numTelEmployeur;
    }

    public void setNumTelEmployeur(String numTelEmployeur) {
        this.numTelEmployeur = numTelEmployeur;
    }

    public String getVilleEmployeur() {
        return villeEmployeur;
    }

    public void setVilleEmployeur(String villeEmployeur) {
        this.villeEmployeur = villeEmployeur;
    }

    public String getLienPubliqueEmployeur() {
        return lienPubliqueEmployeur;
    }

    public void setLienPubliqueEmployeur(String lienPubliqueEmployeur) {
        this.lienPubliqueEmployeur = lienPubliqueEmployeur;
    }

    public String getMotDePasseEmployeur() {
        return motDePasseEmployeur;
    }

    public void setMotDePasseEmployeur(String motDePasseEmployeur) {
        this.motDePasseEmployeur = motDePasseEmployeur;
    }
}
