package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.dto.InventaireDTO;
import com.ucad.m2SIR.SenBook.dto.LivreDTO;
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
        List<LivreDTO> livres = livreService.getAllLivre();
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Récupérer les détails d'un livre par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getLivreById(@PathVariable int id) {
        LivreDTO livre = livreService.getLivre(id);
        return livre != null
                ? new ResponseEntity<>(livre, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Livre avec l'id " + id + " est introuvable", HttpStatus.NOT_FOUND);
    }

    // Récupérer les livres par genre
    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> getLivreByGenre(@PathVariable String genre) {
        List<LivreDTO> livres = livreService.getLivreByGenre(genre);
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Récupérer les livres par titre
    @GetMapping("/titre/{titre}")
    public ResponseEntity<Object> getLivreByTitle(@PathVariable String titre) {
        List<LivreDTO> livres = livreService.getLivreByTitle(titre);
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Récupérer les livres par Auteur
    @GetMapping("/auteur/{nom}")
    public ResponseEntity<Object> getLivreByAuthor(@PathVariable String nom) {
        List<LivreDTO> livres = livreService.getLivreByAuthorName(nom);
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Object> getLivreByISBN(@PathVariable String isbn) {
        LivreDTO livre = livreService.getLivreParISBN(isbn);
        return livre != null
                ? new ResponseEntity<>(livre, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Livre avec l'isbn " + isbn + " est introuvable", HttpStatus.NOT_FOUND);

    }

    @GetMapping("/similar/{text}")
    public ResponseEntity<Object> getSimilarBooks(@PathVariable String text){
        List<LivreDTO> livres = livreService.getSimilarBooks(text);
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchBooks(@RequestParam(name = "text", required = false, defaultValue = " ") String text){
        List<LivreDTO> livres = livreService.searchBooks(text);
        return livres != null
                ? new ResponseEntity<>(livres, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de l'execution", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/stock/{detailsLivreId}")
    public ResponseEntity<Object> getStockByDetailsLivreId(@PathVariable int detailsLivreId) {
        InventaireDTO inventaire = livreService.getStockByDetailsLivreId(detailsLivreId);
        return inventaire != null
                ? new ResponseEntity<>(inventaire, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Stock du livre introuvable", HttpStatus.NOT_FOUND);

    }
}
