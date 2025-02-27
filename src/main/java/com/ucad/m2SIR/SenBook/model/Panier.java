package com.ucad.m2SIR.SenBook.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "panier", indexes = {
        @Index(name = "id_detail_livre", columnList = "id_detail_livre")
}, uniqueConstraints = {
        @UniqueConstraint(name = "id_utilisateur", columnNames = {"id_utilisateur", "id_detail_livre"})
})
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_utilisateur")
    private com.ucad.m2SIR.SenBook.model.Utilisateur utilisateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_detail_livre")
    private DetailsLivre detailLivre;

    @ColumnDefault("1")
    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_ajout")
    private Instant dateAjout;

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

    public DetailsLivre getDetailLivre() {
        return detailLivre;
    }

    public void setDetailLivre(DetailsLivre detailLivre) {
        this.detailLivre = detailLivre;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Instant getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Instant dateAjout) {
        this.dateAjout = dateAjout;
    }

}