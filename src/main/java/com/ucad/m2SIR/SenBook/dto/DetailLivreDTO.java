package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.customTypes.BookFormat;

import java.math.BigDecimal;

public class DetailLivreDTO {
    private int idLivre;
    private BookFormat format;
    private BigDecimal prixUnitaire;
    private String langue;

    public DetailLivreDTO() {
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
