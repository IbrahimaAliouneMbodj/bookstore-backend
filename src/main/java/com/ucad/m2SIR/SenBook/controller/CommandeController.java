package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.dto.DetailsCommandeDTO;
import com.ucad.m2SIR.SenBook.dto.PaiementDTO;
import com.ucad.m2SIR.SenBook.model.Commande;
import com.ucad.m2SIR.SenBook.model.Paiement;
import com.ucad.m2SIR.SenBook.service.CommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    //Crée une nouvelle commande
    @PostMapping("/creer")
    public ResponseEntity<Object> creerCommande(@RequestParam Integer utilisateurId,
                                                  @RequestBody List<DetailsCommandeDTO> details) {
        return commandeService.creerCommande(utilisateurId, details);
    }

    //Effectue un paiement pour une commande
    @PostMapping("/paiement")
    public ResponseEntity<Object> effectuerPaiement(@RequestBody PaiementDTO paiementDTO) {
        return commandeService.effectuerPaiement(paiementDTO);
    }

    //Annule une commande
    @PostMapping("/annuler")
    public ResponseEntity<Object> annulerCommande(@RequestParam Integer commandeId) {
        commandeService.annulerCommande(commandeId);
        return ResponseEntity.ok().build();
    }


    //Récupère les commandes de l'utilisateur
    @GetMapping
    public ResponseEntity<Object> getCommandes(@RequestParam Integer utilisateurId) {
        return commandeService.getCommandes(utilisateurId);
    }
}
