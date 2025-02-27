package com.ucad.m2SIR.SenBook.model;

import com.ucad.m2SIR.SenBook.customTypes.CommandStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "commandes", indexes = {
        @Index(name = "id_utilisateur", columnList = "id_utilisateur")
})
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_utilisateur")
    private com.ucad.m2SIR.SenBook.model.Utilisateur utilisateur;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_commande")
    private Instant dateCommande;

    @Column(name = "prix_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixTotal;

    @ColumnDefault("'PENDING'")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CommandStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public com.ucad.m2SIR.SenBook.model.Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(com.ucad.m2SIR.SenBook.model.Utilisateur utilisateur) {
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

    public CommandStatus getStatut() {
        return status;
    }

    public void setStatut(CommandStatus status) {
        this.status = status;
    }

}