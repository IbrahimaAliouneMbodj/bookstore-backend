package com.ucad.m2SIR.SenBook.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "details_commande", indexes = {
        @Index(name = "id_commande", columnList = "id_commande"),
        @Index(name = "id_detail_livre", columnList = "id_detail_livre")
})
public class DetailsCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_commande")
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_detail_livre")
    private com.ucad.m2SIR.SenBook.model.DetailsLivre detailLivre;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

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

    public com.ucad.m2SIR.SenBook.model.DetailsLivre getDetailLivre() {
        return detailLivre;
    }

    public void setDetailLivre(com.ucad.m2SIR.SenBook.model.DetailsLivre detailLivre) {
        this.detailLivre = detailLivre;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

}