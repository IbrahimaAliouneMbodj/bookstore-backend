package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.model.DetailsCommande;

public class DetailsCommandeDTO {
    private Integer detailsLivreId;
    private Integer quantite;

    public DetailsCommandeDTO(DetailsCommande detailsCommande) {
        this.detailsLivreId = detailsCommande.getDetailLivre().getId();
        this.quantite = detailsCommande.getQuantite();
    }

    public Integer getDetailsLivreId() {
        return detailsLivreId;
    }

    public void setDetailsLivreId(Integer detailsLivreId) {
        this.detailsLivreId = detailsLivreId;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
}
