package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.repository.LivreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    private final LivreRepository livreRepository;
    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public List<Livre> getAllLivre()
    {
        return livreRepository.findAll();
    }
    public Livre getLivre(int id){
        return livreRepository.findById(id).get();
    }
    public List<Livre> getLivreByTitle(String title){
        return livreRepository.findByTitleContainingIgnoreCase(title);
    }
   public List<Livre> getLivreByAuthor(String author){
        return livreRepository.findByAuthor(author);
   }
   public List<Livre> getLivreByGenre(String genre){
        return livreRepository.findByGenre(genre);
   }

}
