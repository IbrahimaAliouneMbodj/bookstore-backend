package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.model.Favoris;
import com.ucad.m2SIR.SenBook.service.favorisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoris")
public class FavorisController {

    private final favorisService favorisService;

    public FavorisController(favorisService favorisService) {
        this.favorisService = favorisService;
    }

    // Récupérer la liste des favoris d'un utilisateur
    @GetMapping("/{idUser}")
    public ResponseEntity<Object> getUserFavoris(@PathVariable Integer idUser) {
        List<Favoris> favoris = favorisService.getUserFavoris(idUser);
        return favoris != null
                ? new ResponseEntity<>(favoris, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // Ajouter un livre aux favoris
    @PostMapping("/{idUtilisateur}/{idLivre}")
    public ResponseEntity<Object> addLivreFavori(@PathVariable Integer idUtilisateur, @PathVariable Integer idLivre) {
        String response = favorisService.addLivreFavori(idUtilisateur, idLivre);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // Supprimer un livre des favoris
    @DeleteMapping("/{idUtilisateur}/{idLivre}")
    public ResponseEntity<Object> removeFromFavorites(@PathVariable Integer idUtilisateur, @PathVariable Integer idLivre) {
        String response = favorisService.removeFromFavorite(idUtilisateur, idLivre);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}