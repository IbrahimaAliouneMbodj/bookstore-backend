package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.model.Inventaire;

public class InventaireDTO {
    private DetailsLivreDTO detailLivre;
    private int quantite;

    public InventaireDTO() {
    }

    public InventaireDTO(DetailsLivreDTO detailLivre, int quantite) {
        this.detailLivre = detailLivre;
        this.quantite = quantite;
    }

    public InventaireDTO(Inventaire inventaire) {
        this.detailLivre = new DetailsLivreDTO(inventaire.getDetailLivre());
        this.quantite = inventaire.getQuantite();
    }

    public DetailsLivreDTO getDetailLivre() {
        return detailLivre;
    }

    public void setDetailLivre(DetailsLivreDTO detailLivre) {
        this.detailLivre = detailLivre;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
