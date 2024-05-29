package com.example.interim;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ApplicationDAO {
    @Insert
    public void addApplication(Application application);
    @Update
    public void upadateApplication(Application application);
    @Delete
    public void deleteApplication(Application application);
    @Query("select * from candidature")
    public List<Application> getAllApplication();
    @Query("select * from candidature where id_candidature = :idApplication")
    public Application getApplicationById(int idApplication);

    @Query("select * from candidature where statut_candidature = 'accepted' ")
    public List<Application> getAcceptedApplication();
    @Query("select * from candidature where statut_candidature = 'pending' ")
    public List<Application> getPendingApplication();
    @Query("select * from candidature where id_offre = :jobOfferId")
    public List<Application> getApplicationByJobOfferId(int jobOfferId);
    @Query("Select u.id_utilisateur ,u.nom_utilisateur, u.prenom_utilisateur, u.date_de_naissance, u.numero_telephone_utilisateur, u.email_utilisateur, u.ville_utilisateur, u.cv_utilisateur, u.lettre_motivation_utilisateur from utilisateur u join candidature c on u.id_utilisateur = c.id_utilisateur where c.id_candidature = :applicationId  ")
    public User getUserInfosByApplicationId(int applicationId);

    // Candidatures en cours et sans réponse
    @Query("select o.id_offre, o.id_employeur, o.nom_industrie, o.nom_employeur, o.intitule_offre, o.ville_offre, o.type_contrat, o.periode_offre, o.remuneration_offre, o.description_offre from offre o join candidature c on o.id_offre = c.id_offre where c.id_utilisateur = :userId and c.statut_candidature = 'pending' ")
    public List<JobOffer> getAllPendingJobOffer(int userId);

    // Candidatures en cours acceptées
    @Query("select o.id_offre, o.id_employeur, o.nom_industrie, o.nom_employeur, o.intitule_offre, o.ville_offre, o.type_contrat, o.periode_offre, o.remuneration_offre, o.description_offre from offre o join candidature c on o.id_offre = c.id_offre where c.id_utilisateur = :userId and c.statut_candidature = 'accepted' ")
    public List<JobOffer> getAllAcceptedJobOffer(int userId);


}
