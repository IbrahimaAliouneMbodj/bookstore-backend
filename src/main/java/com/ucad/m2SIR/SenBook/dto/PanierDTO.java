package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.model.Panier;

import java.time.Instant;

public class PanierDTO {
    private DetailsLivreDTO detailLivre;
    private Integer quantite;
    private Instant dateAjout;

    public PanierDTO() {
    }
    public PanierDTO(Panier panier) {
        this.dateAjout = panier.getDateAjout();
        this.quantite = panier.getQuantite();
        this.detailLivre = new DetailsLivreDTO(panier.getDetailLivre());
    }

    public PanierDTO(DetailsLivreDTO detailLivre, Integer quantite, Instant dateAjout) {
        this.detailLivre = detailLivre;
        this.quantite = quantite;
        this.dateAjout = dateAjout;
    }

    public DetailsLivreDTO getDetailLivre() {
        return detailLivre;
    }

    public void setDetailLivre(DetailsLivreDTO detailLivre) {
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
