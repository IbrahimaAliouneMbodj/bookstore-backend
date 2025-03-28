package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.service.LivreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livres")
public class LivreController {
    private final LivreService livreService;
    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }
    @GetMapping
    // Récupérer la liste de tous les livres
    public ResponseEntity<Object> getAllLivre() {
        List<Livre> livres = livreService.getAllLivre();
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Récupérer les détails d'un livre par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getLivreById(@PathVariable int id) {
        Livre livre = livreService.getLivre(id);
        return livre != null
                ? new ResponseEntity<>(livre, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Récupérer les livres par genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> getLivreByGenre(@PathVariable String genre) {
        List<Livre> livres = livreService.getLivreByGenre(genre);
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Récupérer les livres par titre
    @GetMapping("/titre/{titre}")
    public ResponseEntity<Object> getLivreByTitle(@PathVariable String titre) {
        List<Livre> livres = livreService.getLivreByTitle(titre);
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Récupérer les livres par Auteur
    @GetMapping("/auteur/{nom}")
    public ResponseEntity<Object> getLivreByAuthor(@PathVariable String nom) {
        List<Livre> livres = livreService.getLivreByAuthorName(nom);
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Object> getLivreByISBN(@PathVariable String isbn) {
        Livre livre = livreService.getLivreParISBN(isbn);
        return livre != null
                ? new ResponseEntity<>(livre, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
