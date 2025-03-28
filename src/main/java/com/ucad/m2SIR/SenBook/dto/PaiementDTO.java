package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.customTypes.PaymentMethods;
import com.ucad.m2SIR.SenBook.model.Paiement;


import java.math.BigDecimal;

public class PaiementDTO {

    private int commandeId;

    private BigDecimal montant;

    private PaymentMethods methodePaiement;

    public PaiementDTO() {
    }

    public PaiementDTO(Paiement paiement) {
        this.commandeId = paiement.getCommande().getId();
        this.methodePaiement = paiement.getMethodePaiement();
        this.montant = paiement.getMontant();
    }

    public PaiementDTO(int commandeId, BigDecimal montant, PaymentMethods methodePaiement) {
        this.commandeId = commandeId;
        this.montant = montant;
        this.methodePaiement = methodePaiement;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public PaymentMethods getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(PaymentMethods methodePaiement) {
        this.methodePaiement = methodePaiement;
    }
}
