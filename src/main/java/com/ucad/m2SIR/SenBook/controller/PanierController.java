package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.dto.PanierDTO;
import com.ucad.m2SIR.SenBook.service.PanierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/panier")
public class PanierController {
    private final PanierService panierService;

    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    @GetMapping
    public ResponseEntity<Object> getPanier(@RequestParam int userId) {
        List<PanierDTO> response = panierService.getPanier(userId);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Erreur lors de la recuperation du panier", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/add/{detailsLivreId}")
    public ResponseEntity<Object> addToPanier(@PathVariable int detailsLivreId, @RequestParam int quantite,@RequestParam int userId) {
        String response = panierService.addToPanier(detailsLivreId, userId, quantite);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> ClearPanier(@RequestParam int userId) {
        String response = panierService.clearPanier(userId);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/{livreId}")
    public ResponseEntity<String> removeFromPanier(@PathVariable int livreId, @RequestParam int userId) {
        String response = panierService.removeFromPanier(livreId, userId);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
