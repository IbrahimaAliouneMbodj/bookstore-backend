package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.customTypes.CommandStatus;
import com.ucad.m2SIR.SenBook.dto.*;
import com.ucad.m2SIR.SenBook.model.Auteur;
import com.ucad.m2SIR.SenBook.service.AdminService;
import com.ucad.m2SIR.SenBook.service.InventaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final InventaireService inventaireService;


    public AdminController(AdminService adminService,
                           InventaireService inventaireService) {
        this.adminService = adminService;
        this.inventaireService = inventaireService;
    }

    @PostMapping("/auteurs")
    public ResponseEntity<Object> createAuteur(@RequestBody Auteur auteur) {
        String response = adminService.createAuteur(auteur);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PutMapping("/auteurs")
    public ResponseEntity<Object> updateAuteur(@RequestBody Auteur auteur) {
        String response = adminService.updateAuteur(auteur);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/auteurs/{id}")
    public ResponseEntity<Object> removeAuteur(@PathVariable int id) {
        String response = adminService.removeAuteur(id);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/auteurs")
    public ResponseEntity<Object> getAuteurs() {
        List<Auteur> response = adminService.getAuteurs();
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Erreur lors de la recuperation des auteurs", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/auteurs/{id}")
    public ResponseEntity<Object> getAuteurById(@PathVariable int id) {
        Auteur response = adminService.getAuteurById(id);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : L'auteur spécifié n'as pas été trouvé", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/auteurs/nom/{nom}")
    public ResponseEntity<Object> getAuteurByNom(@PathVariable String nom) {
        List<Auteur> response = adminService.getAuteurByNom(nom);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur est survenue lors de la recuperation", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/livres")
    public ResponseEntity<Object> createLivre(@RequestBody LivreDTO livreDTO) {
        String response = adminService.createLivre(livreDTO);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/livres/{id}")
    public ResponseEntity<Object> removeLivre(@PathVariable int id) {
        String response = adminService.removeLivre(id);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PostMapping("/detailsLivre")
    public ResponseEntity<Object> createDetailLivre(@RequestBody DetailsLivreDTO detailLivre) {
        String response = adminService.createDetailLivre(detailLivre);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @DeleteMapping("/detailsLivre/{id}")
    public ResponseEntity<Object> removeDetailsLivre(@PathVariable int id) {
        String response = adminService.removeDetailsLivre(id);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/utilisateurs")
    public ResponseEntity<Object> getUtilisateurs() {
        List<UtilisateurDTO> response = adminService.getUtilisateurs();
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/utilisateurs/{id}")
    public ResponseEntity<Object> deleteUtilisateur(@PathVariable int id) {
        String response = adminService.deleteUtilisateurs(id);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/commandes")
    public ResponseEntity<Object> getCommandes() {
        List<CommandeDTO> response = adminService.getCommandes();
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation des commandes", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/commandes/{id}")
    public ResponseEntity<Object> getCommandeById(@PathVariable int id) {
        CommandeDTO response = adminService.getCommandeById(id);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation des commandes", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/commandes/utilisateurs/{id}")
    public ResponseEntity<Object> getCommandesParUtilisateur(@PathVariable Integer id) {
        List<CommandeDTO> response = adminService.getCommandesParUtilisateur(id);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation des commandes", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/commandes/details/{commandId}")
    public ResponseEntity<Object> getDetailsCommandes(@PathVariable Integer commandId) {
        List<DetailsCommandeDTO> response = adminService.getDetailsByCommandId(commandId);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation des details", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Change le statut d'une commande
    @PutMapping("/commandes/status/{commandeId}/{status}")
    public ResponseEntity<Object> changerStatusCommande(@PathVariable Integer commandeId,
                                                        @PathVariable CommandStatus status) {
        String response = adminService.changerStatusCommande(commandeId, status);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // Récupérer tout l’inventaire
    @GetMapping("/inventaire")
    public ResponseEntity<Object> getTousInventaires() {
        List<InventaireDTO> response = inventaireService.getTousInventaires();
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation des stocks", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Récupérer l’inventaire d’un livre spécifique
    @GetMapping("/inventaire/{detailsLivreId}")
    public ResponseEntity<Object> getInventaireParLivre(@PathVariable int detailsLivreId) {
        InventaireDTO response = inventaireService.getInventaireParLivre(detailsLivreId);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation des commandes", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Récupérer les articles avec un stock faible
    @GetMapping("/inventaire/faible-stock")
    public ResponseEntity<Object> getFaibleStock(@RequestParam int seuil) {
        List<InventaireDTO> response = inventaireService.getFaibleStock(seuil);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation des commandes", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Ajouter un nouvel article en stock
    @PostMapping("/inventaire")
    public ResponseEntity<Object> ajouterStock(@RequestBody InventaireDTO inventaireDTO) {
        String response = inventaireService.ajouterStock(inventaireDTO);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    //Modifier le stock d’un livre (augmenter ou diminuer)
    @PutMapping("/inventaire")
    public ResponseEntity<Object> modifierStock(@RequestBody InventaireDTO inventaireDTO) {
        String response = inventaireService.modifierStock(inventaireDTO);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    //Supprimer un stock
    @DeleteMapping("/inventaire/{detailsLivreId}")
    public ResponseEntity<Object> supprimerStock(@PathVariable int detailsLivreId) {
        String response = inventaireService.supprimerStock(detailsLivreId);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    @GetMapping("/paiement")
    public ResponseEntity<Object> getPaiements() {
        List<PaiementDTO> reponse = adminService.getPaiements();
        return reponse != null
                ? new ResponseEntity<>(reponse, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation des paiements", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

