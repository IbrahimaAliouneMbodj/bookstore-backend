package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByNomUtilisateur(String username);

    boolean existsByNomUtilisateur(String username);

    boolean existsByEmail(String email);

    @Query("select count(u.id) from Utilisateur u")
    Integer getUserCount();

    @Query("select u from Utilisateur u where u.nomUtilisateur LIKE %:text% OR u.email LIKE %:text%")
    List<Utilisateur> findAllByText(String text);
}
