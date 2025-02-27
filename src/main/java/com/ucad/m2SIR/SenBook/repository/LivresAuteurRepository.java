package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.LivresAuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivresAuteurRepository extends JpaRepository<LivresAuteur, Integer> {
}
