package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.model.LivresAuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivresAuteurRepository extends JpaRepository<LivresAuteur, Integer> {
    List<LivresAuteur> findAllByLivre_Id(Integer livreId);

    void deleteAllByLivreId(int livreId);

    List<Livre> findAllByAuteur(String author);
}
