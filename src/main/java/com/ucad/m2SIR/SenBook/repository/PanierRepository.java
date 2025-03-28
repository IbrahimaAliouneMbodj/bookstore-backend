package com.ucad.m2SIR.SenBook.repository;


import com.ucad.m2SIR.SenBook.model.DetailsLivre;
import com.ucad.m2SIR.SenBook.model.Panier;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Integer> {

    List<Panier> findAllByUtilisateurId(int idUser);

    void deleteAllByUtilisateur(Utilisateur user);

    Boolean existsByUtilisateur(Utilisateur user);

    boolean existsByUtilisateurAndDetailLivre(Utilisateur user, DetailsLivre livre);

    void deleteAllByUtilisateurAndDetailLivre(Utilisateur user, DetailsLivre livre);

    Optional<Panier> findByUtilisateurAndDetailLivre(Utilisateur user, DetailsLivre livre);
}
