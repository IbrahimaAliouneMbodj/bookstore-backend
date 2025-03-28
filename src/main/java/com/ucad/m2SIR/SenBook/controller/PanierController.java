package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.dto.PanierDTO;
import com.ucad.m2SIR.SenBook.service.PanierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/panier")
@PreAuthorize("hasAuthority('CLIENT')")
public class PanierController {
    private final PanierService panierService;

    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    @GetMapping
    public ResponseEntity<Object> getPanier() {
        List<PanierDTO> response = panierService.getPanier();
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Erreur lors de la recuperation du panier", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/add/{detailsLivreId}/{quantite}")
    public ResponseEntity<Object> addToPanier(@PathVariable int detailsLivreId, @PathVariable int quantite) {
        String response = panierService.addToPanier(detailsLivreId, quantite);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> ClearPanier() {
        String response = panierService.clearPanier();
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/{detailsLivreId}")
    public ResponseEntity<String> removeFromPanier(@PathVariable int detailsLivreId) {
        String response = panierService.removeFromPanier(detailsLivreId);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
