package com.example.interim;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "offre", foreignKeys = @ForeignKey(entity = Employer.class, parentColumns = "id_employeur", childColumns = "id_employeur", onDelete = ForeignKey.CASCADE))
public class JobOffer {

    @ColumnInfo(name = "id_offre")
    @PrimaryKey(autoGenerate = true)
    public int idOffre;
    @ColumnInfo(name = "id_employeur")
    public int idEmployeur;
    @ColumnInfo(name = "nom_industrie")
    public String industrieOffre;
    @ColumnInfo(name = "nom_employeur")
    public String nomEmployeur;
    @ColumnInfo(name = "intitule_offre")
    public String intituleOffre;
    @ColumnInfo(name = "ville_offre")
    public String villeOffre;
    @ColumnInfo(name = "type_contrat")
    public String typeContrat;
    @ColumnInfo(name = "periode_offre")
    public String periode;
    @ColumnInfo(name = "remuneration_offre")
    public double remunerationOffre;
    @ColumnInfo(name = "description_offre")
    public String descriptionOffre;

    @Ignore
    public JobOffer() {
    }

    public JobOffer(int idEmployeur, String industrieOffre, String nomEmployeur, String intituleOffre, String villeOffre, String typeContrat, String periode, double remunerationOffre, String descriptionOffre) {
        this.idEmployeur = idEmployeur;
        this.industrieOffre = industrieOffre;
        this.nomEmployeur = nomEmployeur;
        this.intituleOffre = intituleOffre;
        this.villeOffre = villeOffre;
        this.typeContrat = typeContrat;
        this.periode = periode;
        this.remunerationOffre = remunerationOffre;
        this.descriptionOffre = descriptionOffre;
    }
}
