package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.repository.LivreRepository;
import com.ucad.m2SIR.SenBook.repository.LivresAuteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    private final LivreRepository livreRepository;
    private final LivresAuteurRepository livresAuteurRepository;

    public LivreService(LivreRepository livreRepository, LivresAuteurRepository livresAuteurRepository) {
        this.livreRepository = livreRepository;
        this.livresAuteurRepository = livresAuteurRepository;
    }

    public List<Livre> getAllLivre()
    {
        return livreRepository.findAll();
    }
    public Livre getLivre(int id){
        return livreRepository.findById(id).orElse(null);
    }
    public Livre getLivreParISBN(String ISBN){
        return livreRepository.findByIsbn(ISBN).orElse(null);
    }
    public List<Livre> getLivreByTitle(String title){
        return livreRepository.findByTitreContaining(title);
    }
    public List<Livre> getLivreByAuthorName(String authorName){
        return livresAuteurRepository.findAllByAuteurNom(authorName);
    }
    public List<Livre> getLivreByGenre(String genre){
        return livreRepository.findByGenreContaining(genre);
    }
}
