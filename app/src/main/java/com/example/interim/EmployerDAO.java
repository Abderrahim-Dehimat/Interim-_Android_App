package com.example.interim;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployerDAO {
    @Insert
    public void addEmployer(Employer employer);
    @Update
    public void updateEmployer(Employer employer);
    @Delete
    public void deleteEmployer(Employer employer);

    @Query("select * from employeur")
    public List<Employer> getAllEmployers();
    @Query("select * from employeur where id_employeur = :employerId")
    public Employer getEmployerById(int employerId);
    @Query("select * from employeur where email_employeur = :emailEmp and mot_de_passe_employeur = :passwordEmp")
    public Employer getEmployerByEmailAndPassword(String emailEmp, String passwordEmp);

}
