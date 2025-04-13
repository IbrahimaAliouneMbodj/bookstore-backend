package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.dto.CommandeDTO;
import com.ucad.m2SIR.SenBook.dto.DetailsCommandeDTO;
import com.ucad.m2SIR.SenBook.dto.PaiementDTO;
import com.ucad.m2SIR.SenBook.service.CommandeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@PreAuthorize("hasAuthority('CLIENT')")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    //Crée une nouvelle commande
    @PostMapping("/creer")
    public ResponseEntity<Object> creerCommande(@RequestBody List<DetailsCommandeDTO> details) {
        String response = commandeService.creerCommande(details);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    //Effectue un paiement pour une commande
    @PostMapping("/paiement")
    public ResponseEntity<Object> effectuerPaiement(@RequestBody PaiementDTO paiementDTO) {
        String response = commandeService.effectuerPaiement(paiementDTO);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    //Annule une commande
    @PostMapping("/annuler/{commandeId}")
    public ResponseEntity<Object> annulerCommande(@PathVariable Integer commandeId) {
        String response = commandeService.annulerCommande(commandeId);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.OK
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    //Récupère les commandes de l'utilisateur
    @GetMapping
    public ResponseEntity<Object> getCommandes() {
        List<CommandeDTO> response = commandeService.getCommandes();
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(
                "Failed : Une erreur s'est produite lors de la recuperation des commandes",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
