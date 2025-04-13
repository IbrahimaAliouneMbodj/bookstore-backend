package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.Commande;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findByUtilisateur(Utilisateur utilisateur);

    @Query("select count(c.id) from Commande c")
    Integer getOrderCount();
}
