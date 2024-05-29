package com.example.interim;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "candidature", foreignKeys = {
        @ForeignKey(entity = JobOffer.class, parentColumns = "id_offre", childColumns = "id_offre", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class, parentColumns = "id_utilisateur", childColumns = "id_utilisateur", onDelete = ForeignKey.CASCADE)
})
public class Application {
    @ColumnInfo(name = "id_candidature")
    @PrimaryKey(autoGenerate = true)
    public int idCandidature;
    @ColumnInfo(name = "id_offre")
    public int idOffre;
    @ColumnInfo(name = "id_utilisateur")
    public int idUtilisateur;
    @ColumnInfo(name = "nom_candidat")
    public String nomCandidat;
    @ColumnInfo(name = "prenom_candidat")
    public String prenomCandidat;
    @ColumnInfo(name = "date_candidature")
    public String dateCandidature;
    @ColumnInfo(name = "statut_candidature")
    public String statut; // accepte, refuse, en attente
    @ColumnInfo(name = "reponse_candidat")
    public String reponseCandidat;

    @Ignore
    public Application() {
    }

    public Application(int idOffre, int idUtilisateur, String dateCandidature, String statut, String nomCandidat, String prenomCandidat, String reponseCandidat) {
        this.idOffre = idOffre;
        this.idUtilisateur = idUtilisateur;
        this.dateCandidature = dateCandidature;
        this.statut = statut;
        this.nomCandidat = nomCandidat;
        this.prenomCandidat = prenomCandidat;
        this.reponseCandidat = reponseCandidat;
    }
}
