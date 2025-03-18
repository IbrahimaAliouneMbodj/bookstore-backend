package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
}
