package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavorisRepository extends JpaRepository<Favoris, Integer> {
    List<Favoris> findByUtilisateurId(Integer id);

    boolean existsByUtilisateurIdAndLivreId(int idUtilisateur, int idlivre);

    void deleteByUtilisateurIdAndLivreId(int idUtilisateur, int idlivre);
}
