package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.Auteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuteurRepository extends JpaRepository<Auteur, Integer> {
    List<Auteur> findByNomContaining(String nom);
}
