package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.DetailsLivre;
import com.ucad.m2SIR.SenBook.model.Inventaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventaireRepository extends JpaRepository<Inventaire, Integer> {
    Optional<Inventaire> findByDetailLivre(DetailsLivre detailsLivre);

    boolean existsByDetailLivre(DetailsLivre detailsLivre);

    Optional<Inventaire> findByDetailLivreId(int id);

    List<Inventaire> findByQuantiteLessThan(int seuil);
}
