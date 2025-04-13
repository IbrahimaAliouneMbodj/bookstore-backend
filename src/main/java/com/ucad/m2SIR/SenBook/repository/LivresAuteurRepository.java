package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.Auteur;
import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.model.LivresAuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivresAuteurRepository extends JpaRepository<LivresAuteur, Integer> {
    void deleteAllByLivreId(int livreId);

    @Query("SELECT li FROM Livre li join LivresAuteur l ON li.id = l.livre.id WHERE l.auteur.nom LIKE %:author%")
    List<Livre> findAllByAuteurNom(@Param("author") String author);

    @Query("SELECT at FROM Auteur at join LivresAuteur l ON at.id = l.auteur.id WHERE l.livre.id =:id")
    List<Auteur> findAllAuteurByLivreId(@Param("id") Integer id);
}
