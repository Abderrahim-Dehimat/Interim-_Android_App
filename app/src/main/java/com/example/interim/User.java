package com.example.interim;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "utilisateur")
public class User {

    @ColumnInfo(name = "id_utilisateur")
    @PrimaryKey(autoGenerate = true)
    public int idUtilisateur;
    @ColumnInfo(name = "nom_utilisateur")
    public String nom;
    @ColumnInfo(name = "prenom_utilisateur")
    public String prenom;
    @ColumnInfo(name = "date_de_naissance")
    public String dateDeNaissance;
    @ColumnInfo(name = "ville_utilisateur")
    public String ville;
    @ColumnInfo(name = "numero_telephone_utilisateur")
    public String numeroTelephone;
    @ColumnInfo(name = "email_utilisateur")
    public String emailUtilisateur;
    @ColumnInfo(name = "mot_de_passe_utilisateur")
    public String motDePasse;
    @ColumnInfo(name = "cv_utilisateur")
    public String cvUtilisateurBase64;
    @ColumnInfo(name = "lettre_motivation_utilisateur")
    public String lettreMotivationBase64;

    @Ignore
    public User() {
    }

    public User(String nom, String prenom, String dateDeNaissance, String ville, String numeroTelephone, String emailUtilisateur, String motDePasse, String cvUtilisateurBase64, String lettreMotivationBase64) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.ville = ville;
        this.numeroTelephone = numeroTelephone;
        this.emailUtilisateur = emailUtilisateur;
        this.motDePasse = motDePasse;
        this.idUtilisateur = 0;
        this.cvUtilisateurBase64 = cvUtilisateurBase64;
        this.lettreMotivationBase64 = lettreMotivationBase64;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getEmailUtilisateur() {
        return emailUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getCvUtilisateur() {
        return cvUtilisateurBase64;
    }

    public void setCvUtilisateur(String cvUtilisateur) {
        this.cvUtilisateurBase64 = cvUtilisateur;
    }

    public String getLetteMotivation() {
        return lettreMotivationBase64;
    }

    public void setLetteMotivation(String letteMotivation) {
        this.lettreMotivationBase64 = letteMotivation;
    }
}
