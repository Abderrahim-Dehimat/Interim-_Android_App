package com.example.interim;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface JobOfferDAO {
    @Insert
    public void addJobOffer(JobOffer jobOffer);
    @Update
    public void updateJobOffer(JobOffer jobOffer);
    @Delete
    public void deleteJobOffer(JobOffer jobOffer);
    @Query("select * from offre")
    public List<JobOffer> gellAllJobOffer();
    @Query("select * from offre where id_offre = :jobOfferId")
    public JobOffer getJobOfferById(int jobOfferId);
    @Query("select * from offre where nom_industrie = :nomIndustrie")
    public List<JobOffer> getJobOfferByNomIndustrie(String nomIndustrie);
    @Query("select * from offre where intitule_offre = :jobOfferIntitule")
    public List<JobOffer> getJobOfferByIntitule(String jobOfferIntitule);
    @Query("select * from offre where ville_offre = :jobOfferVille")
    public List<JobOffer> getJobOfferByVille(String jobOfferVille);

    @Query("select * from offre where type_contrat = :jobOfferTypeContrat")
    public List<JobOffer> getJobOfferByTypeContrat(String jobOfferTypeContrat);

    @Query("select * from offre where periode_offre = :jobOfferPeriode")
    public List<JobOffer> getJobOfferByPeriode(String jobOfferPeriode);

    @Query("select * from offre where id_employeur = :idEmployeur")
    public List<JobOffer> getJobOfferByIdEmployeur(int idEmployeur);

    @Query("select * from offre where nom_employeur = :nomEmployeur")
    public List<JobOffer> getJobOfferByNomEmployeur(String nomEmployeur);

    @Query("select * from offre where nom_industrie = :nomIndustrie and intitule_offre = :intitule and ville_offre = :ville and type_contrat = :type and periode_offre = :periode")
    public List<JobOffer> getJobOfferByAllFilters(String nomIndustrie, String intitule, String ville, String type, String periode);

    @Query("select * from offre where nom_industrie = :nomIndustrie and intitule_offre = :intitule and ville_offre = :ville and type_contrat = :type")
    public List<JobOffer> getJobOfferByIndustrieIntituleVilleType(String nomIndustrie, String intitule, String ville, String type);

    @Query("select * from offre where nom_industrie = :nomIndustrie and intitule_offre = :intitule and ville_offre = :ville")
    public List<JobOffer> getJobOfferByIndustrieIntituleVille(String nomIndustrie, String intitule, String ville);

    @Query("select * from offre where nom_industrie = :nomIndustrie and intitule_offre = :intitule")
    public List<JobOffer> getJobOfferByIndustrieIntitule(String nomIndustrie, String intitule);


}
