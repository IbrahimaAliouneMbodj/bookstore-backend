package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.model.DetailsCommande;

public class DetailsCommandeDTO {
    private DetailsLivreDTO detailsLivre;
    private Integer quantite;

    public DetailsCommandeDTO(DetailsCommande detailsCommande) {
        this.detailsLivre =new DetailsLivreDTO(detailsCommande.getDetailLivre());
        this.quantite = detailsCommande.getQuantite();
    }

    public DetailsCommandeDTO() {
    }

    public DetailsLivreDTO getDetailsLivre() {
        return detailsLivre;
    }

    public void setDetailsLivre(DetailsLivreDTO detailsLivre) {
        this.detailsLivre = detailsLivre;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
