package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.customTypes.BookFormat;
import com.ucad.m2SIR.SenBook.model.DetailsLivre;

import java.math.BigDecimal;

public class DetailsLivreDTO {
    private int id;
    private int idLivre;
    private BookFormat format;
    private BigDecimal prixUnitaire;
    private String langue;

    public DetailsLivreDTO() {
    }

    public DetailsLivreDTO(DetailsLivre detailsLivre) {
        this.id = detailsLivre.getId();
        this.idLivre = detailsLivre.getLivre().getId();
        this.format = detailsLivre.getFormat();
        this.prixUnitaire = detailsLivre.getPrixUnitaire();
        this.langue = detailsLivre.getLangue();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookFormat getFormat() {
        return format;
    }

    public void setFormat(BookFormat format) {
        this.format = format;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }
}
