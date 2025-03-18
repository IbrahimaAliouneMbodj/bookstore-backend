package com.ucad.m2SIR.SenBook.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "inventaire", indexes = {
        @Index(name = "id_detail_livre", columnList = "id_detail_livre")
})
public class Inventaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_detail_livre")
    private DetailsLivre detailLivre;

    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}