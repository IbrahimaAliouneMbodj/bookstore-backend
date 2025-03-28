package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.dto.FavorisDTO;
import com.ucad.m2SIR.SenBook.service.favorisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoris")
@PreAuthorize("hasAuthority('CLIENT')")
public class FavorisController {

    private final favorisService favorisService;

    public FavorisController(favorisService favorisService) {
        this.favorisService = favorisService;
    }

    // Récupérer la liste des favoris d'un utilisateur
    @GetMapping
    public ResponseEntity<Object> getUserFavoris() {
        List<FavorisDTO> favoris = favorisService.getUserFavoris();
        return favoris != null
                ? new ResponseEntity<>(favoris, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // Ajouter un livre aux favoris
    @PostMapping("/{idLivre}")
    public ResponseEntity<Object> addLivreFavori(@PathVariable Integer idLivre) {
        String response = favorisService.addLivreFavori(idLivre);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // Supprimer un livre des favoris
    @DeleteMapping("/{idLivre}")
    public ResponseEntity<Object> removeFromFavorites(@PathVariable Integer idLivre) {
        String response = favorisService.removeFromFavorite(idLivre);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}