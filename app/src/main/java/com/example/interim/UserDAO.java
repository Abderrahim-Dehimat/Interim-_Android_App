package com.example.interim;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    public void addUser(User user);
    @Update
    public void updateUser(User user);
    @Delete
    public void deleteUser(User user);

    @Query("select * from utilisateur")
    public List<User> getAllUsers();
    @Query("select * from utilisateur where id_utilisateur = :userId")
    public User getUserById(int userId);

    @Query("select * from utilisateur where email_utilisateur = :email and mot_de_passe_utilisateur = :password")
    public User getUserByEmailAndPassword(String email, String password);

}
