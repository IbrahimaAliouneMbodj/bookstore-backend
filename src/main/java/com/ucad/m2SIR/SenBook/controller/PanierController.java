package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.model.DetailsLivre;
import com.ucad.m2SIR.SenBook.service.PanierService;
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
    public List<DetailsLivre> getPanier(@RequestParam int userId){
        return this.panierService.getPanier(userId);
    }

    @GetMapping("/add")
    public ResponseEntity<String> addToPanier(@RequestParam int livreId, @RequestParam int userId){
        return this.panierService.addToPanier(livreId,userId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> ClearPanier(@RequestParam int userId){
        return this.panierService.clearPanier(userId);
    }

    @DeleteMapping("/delete/{livreId}")
    public ResponseEntity<String> removeFromPanier(@PathVariable int livreId, @RequestParam int userId){
        return this.panierService.removeFromPanier(livreId,userId);
    }
}
