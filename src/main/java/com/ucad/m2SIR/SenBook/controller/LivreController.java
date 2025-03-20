package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.service.LivreService;
import org.springframework.web.bind.annotation.*;

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
    //@PreAuthorize("hasRole('ADMIN')") C'est pour securiser une methode
    @GetMapping("/{id}")
    public Livre getLivreById(@PathVariable int id) {
        return livreService.getLivre(id);
    }
    // Récupérer les livres par genre
    @GetMapping("/genre")
    public List<Livre> getLivreByGenre(@RequestParam String genre) {
        return livreService.getLivreByGenre(genre);
    }
    // Récupérer les livres par titre
    @GetMapping("/titre")
    public List<Livre> getLivreByTitle(@RequestParam String titre) {
        return livreService.getLivreByTitle(titre);
    }
    // Récupérer les livres par Auteur
    @GetMapping("/auteur")
    public List<Livre> getLivreByAuthor(@RequestParam String author) {
        return livreService.getLivreByAuthor(author);
    }

    @GetMapping("/isbn")
    public Livre getLivreByISBN(@RequestParam String isbn) {
        return livreService.getLivreParISBN(isbn);
    }
}
