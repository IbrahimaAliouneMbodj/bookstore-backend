package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.Commande;
import com.ucad.m2SIR.SenBook.model.DetailsCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailsCommandeRepository extends JpaRepository<DetailsCommande, Integer> {
    List<DetailsCommande> findByCommande(Commande commande);

    List<DetailsCommande> findAllByCommande(Commande commande);
}
