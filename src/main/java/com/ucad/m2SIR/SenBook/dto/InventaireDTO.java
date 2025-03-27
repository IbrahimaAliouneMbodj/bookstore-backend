package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.model.Inventaire;

public class InventaireDTO {
    private int detailLivreId;
    private int quantite;

    public InventaireDTO() {
    }

    public InventaireDTO(int detailLivreId, int quantite) {
        this.detailLivreId = detailLivreId;
        this.quantite = quantite;
    }

    public InventaireDTO(Inventaire inventaire) {
        this.detailLivreId = inventaire.getDetailLivre().getId();
        this.quantite = inventaire.getQuantite();
    }

    public int getDetailLivreId() {
        return detailLivreId;
    }

    public void setDetailLivreId(int detailLivreId) {
        this.detailLivreId = detailLivreId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
