package com.ucad.m2SIR.SenBook.repository;

import com.ucad.m2SIR.SenBook.model.Livre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Integer> {

    List<Livre> findByTitreContaining(String title);

    List<Livre> findByGenreContaining(String genre);

    Optional<Livre> findByIsbn(String ISBN);

}
