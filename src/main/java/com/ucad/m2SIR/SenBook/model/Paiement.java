package com.ucad.m2SIR.SenBook.model;

import com.ucad.m2SIR.SenBook.customTypes.PaymentMethods;
import com.ucad.m2SIR.SenBook.customTypes.PaymentStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "paiement", indexes = {
        @Index(name = "id_commande", columnList = "id_commande")
})
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_commande", nullable = false)
    private Commande commande;

    @Column(name = "montant", nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    @Column(name = "methode_paiement", nullable = false)
    private PaymentMethods methodePaiement;

    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_paiement")
    private Instant datePaiement;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
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

    public PaymentStatus getStatut() {
        return status;
    }

    public void setStatut(PaymentStatus status) {
        this.status = status;
    }

    public Instant getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
    }

}