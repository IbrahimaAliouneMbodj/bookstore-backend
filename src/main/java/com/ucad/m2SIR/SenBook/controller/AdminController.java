package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.customTypes.CommandStatus;
import com.ucad.m2SIR.SenBook.dto.*;
import com.ucad.m2SIR.SenBook.service.AdminService;
import com.ucad.m2SIR.SenBook.service.InventaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final InventaireService inventaireService;


    public AdminController(AdminService adminService, InventaireService inventaireService) {
        this.adminService = adminService;
        this.inventaireService = inventaireService;
    }

    @PostMapping("/auteurs")
    public ResponseEntity<Object> createAuteur(@RequestBody AuteurDTO auteurDTO) {
        return adminService.createAuteur(auteurDTO);
    }

    @DeleteMapping("/auteurs/{id}")
    public ResponseEntity<Object> removeAuteur(@PathVariable int id) {
        return adminService.removeAuteur(id);
    }

    @GetMapping("/auteurs")
    public ResponseEntity<Object> getAuteurs() {
        return adminService.getAuteurs();
    }

    @GetMapping("/auteurs/{id}")
    public ResponseEntity<Object> getAuteurById(@PathVariable int id) {
        return adminService.getAuteurById(id);
    }

    @GetMapping("/auteurs/nom")
    public ResponseEntity<Object> getAuteurByNom(@RequestParam String nom) {
        return adminService.getAuteurByNom(nom);
    }

    @PostMapping("/livre")
    public ResponseEntity<Object> createLivre(@RequestBody LivreDTO livreDTO) {
        return adminService.createLivre(livreDTO);
    }

    @DeleteMapping("/livre/{id}")
    public ResponseEntity<Object> removeLivre(@PathVariable int id) {
        return adminService.removeLivre(id);
    }

    @PostMapping("/detailLivre")
    public ResponseEntity<Object> createDetailLivre(@RequestBody DetailLivreDTO detailLivre) {
        return adminService.createDetailLivre(detailLivre);
    }

    @DeleteMapping("/detailLivre/{id}")
    public ResponseEntity<Object> removeDetailsLivre(@PathVariable int id) {
        return adminService.removeDetailsLivre(id);
    }

    @GetMapping("/utilisateurs")
    public ResponseEntity<Object> getUtilisateurs() {
        return adminService.getUtilisateurs();
    }

    @DeleteMapping("/utilisateurs/{id}")
    public ResponseEntity<Object> deleteUtilisateur(@PathVariable int id) {
        return adminService.deleteUtilisateurs(id);
    }

    //Change le statut d'une commande
    @PutMapping("/commandes/statut")
    public ResponseEntity<Object> changerStatutCommande(@RequestParam Integer commandeId,
                                                      @RequestParam CommandStatus nouveauStatut) {
        return adminService.changerStatutCommande(commandeId, nouveauStatut);
    }

    //Récupère les commandes d'un utilisateur
    @GetMapping("/commandes/utilisateur")
    public ResponseEntity<Object> getCommandesParUtilisateur(@RequestParam Integer utilisateurId) {
        return adminService.getCommandesParUtilisateur(utilisateurId);
    }

    @GetMapping("/commandes")
    public ResponseEntity<Object> getCommandes() {
        return adminService.getAllCommandes();
    }

    @GetMapping("/commandes/{id}")
    public ResponseEntity<Object> getCommandesById(@PathVariable int id) {
        return adminService.getCommandeById(id);
    }


    // Récupérer tout l’inventaire
    @GetMapping("/inventaire")
    public ResponseEntity<Object> getTousInventaires() {
        return inventaireService.getTousInventaires();
    }

    //Récupérer l’inventaire d’un livre spécifique
    @GetMapping("/inventaire/{detailsLivreId}")
    public ResponseEntity<Object> getInventaireParLivre(@PathVariable int detailsLivreId) {
        return inventaireService.getInventaireParLivre(detailsLivreId);
    }

    //Récupérer les articles avec un stock faible
    @GetMapping("/inventaire/faible-stock")
    public ResponseEntity<Object> getFaibleStock(@RequestParam int seuil) {
        return inventaireService.getFaibleStock(seuil);
    }

    //Ajouter un nouvel article en stock
    @PostMapping("/inventaire")
    public ResponseEntity<Object> ajouterStock(@RequestBody InventaireDTO inventaireDTO) {
        return inventaireService.ajouterStock(inventaireDTO);
    }

    //Modifier le stock d’un livre (augmenter ou diminuer)
    @PutMapping("/inventaire")
    public ResponseEntity<Object> modifierStock(@RequestBody InventaireDTO inventaireDTO) {
        return inventaireService.modifierStock(inventaireDTO);
    }

    //Supprimer un stock
    @DeleteMapping("/inventaire/{detailsLivreId}")
    public ResponseEntity<Object> supprimerStock(@PathVariable int detailsLivreId) {
        return inventaireService.supprimerStock(detailsLivreId);
    }


    @GetMapping("/paiement")
    public ResponseEntity<Object> getPaiements(){
        return adminService.getPaiements();
    }

}

