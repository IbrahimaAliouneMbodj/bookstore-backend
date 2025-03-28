package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.customTypes.CommandStatus;
import com.ucad.m2SIR.SenBook.model.Commande;

import java.math.BigDecimal;
import java.time.Instant;

public class CommandeDTO {
    private Integer id;
    private UtilisateurDTO utilisateur;
    private Instant dateCommande;
    private BigDecimal prixTotal;
    private CommandStatus status;

    public CommandeDTO() {
    }

    public CommandeDTO(Commande commande) {
        this.id = commande.getId();
        this.utilisateur = new UtilisateurDTO(commande.getUtilisateur());
        this.dateCommande = commande.getDateCommande();
        this.prixTotal = commande.getPrixTotal();
        this.status = commande.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UtilisateurDTO getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UtilisateurDTO utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public CommandStatus getStatus() {
        return status;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }
}
