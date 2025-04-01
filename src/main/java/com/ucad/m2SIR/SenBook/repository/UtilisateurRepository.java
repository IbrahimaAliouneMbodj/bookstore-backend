package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.customTypes.UserRole;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Optional<Utilisateur> findByNomUtilisateur(String username);

    List<Utilisateur> findAllByRole(UserRole client);

    boolean existsByNomUtilisateur(String username);

    boolean existsByEmail(String email);
}
