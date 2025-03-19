package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.service.LivreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/livre")
public class LivreController {
    private final LivreService livreService;
    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }
    @GetMapping
    // Récupérer la liste de tous les livres
    public List<Livre> getAllLivre() {
        return livreService.getAllLivre();
    }
    // Récupérer les détails d'un livre par son ID
    @GetMapping("/{id}")
    public Livre getLivreById(@RequestParam int id) {
        return livreService.getLivre(id);
    }
    // Récupérer les livres par genre
    public List<Livre> getLivreByGenre(@RequestParam String genre) {
        return livreService.getLivreByGenre(genre);
    }
    // Récupérer les livres par genre
    public List<Livre> getLivreByTitle(@RequestParam String title) {
        return livreService.getLivreByTitle(title);
    }

}
