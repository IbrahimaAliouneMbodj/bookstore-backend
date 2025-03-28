package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.model.Favoris;
import com.ucad.m2SIR.SenBook.model.Livre;

import java.time.Instant;

public class FavorisDTO {
    private Livre livre;
    private Instant dateAjout;

    public FavorisDTO() {
    }

    public FavorisDTO(Favoris favoris) {
        this.livre = favoris.getLivre();
        this.dateAjout = favoris.getDateAjout();
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Instant getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Instant dateAjout) {
        this.dateAjout = dateAjout;
    }
}
