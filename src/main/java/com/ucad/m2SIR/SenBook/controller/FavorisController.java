package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.model.Favoris;
import com.ucad.m2SIR.SenBook.service.favorisService;
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
    public ResponseEntity<List<Favoris>> getUserFavoris(@PathVariable Integer idUser) {
        List<Favoris> favoris = favorisService.getUserFavoris(idUser);
        return ResponseEntity.ok(favoris);
    }


    // Ajouter un livre aux favoris
    @PostMapping("/{idUtilisateur}/{idLivre}")
    public ResponseEntity<Favoris> addLivreFavori(@PathVariable Integer idUtilisateur, @PathVariable Integer idLivre) {
        Favoris favoris = favorisService.addLivreFavori(idUtilisateur, idLivre);
        return ResponseEntity.ok(favoris);
    }

    // Supprimer un livre des favoris
    @DeleteMapping("/{idUtilisateur}/{idLivre}")
    public ResponseEntity<Void> removeFromFavorites(@PathVariable Integer idUtilisateur, @PathVariable Integer idLivre) {
        favorisService.RemoveFromFavorite(idUtilisateur, idLivre);
        return ResponseEntity.noContent().build();
    }
}
