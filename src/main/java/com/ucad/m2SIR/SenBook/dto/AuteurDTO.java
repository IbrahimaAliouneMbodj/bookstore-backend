package com.ucad.m2SIR.SenBook.dto;


import com.ucad.m2SIR.SenBook.model.Auteur;

import java.time.LocalDate;

public class AuteurDTO {
    private Integer id;
    private String nom;
    private String biographie;
    private LocalDate dateNaissance;
    private String pays;

    public AuteurDTO() {
    }

    public AuteurDTO(Auteur auteur) {
        this.id = auteur.getId();
        this.nom = auteur.getNom();
        this.biographie = auteur.getBiographie();
        this.dateNaissance = auteur.getDateNaissance();
        this.pays = auteur.getPays();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
