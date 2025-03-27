package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.model.Auteur;

import java.time.LocalDate;

public class AuteurDTO {
    private String nom;
    private String biographie;
    private LocalDate dateNaissance;
    private String pays;

    public AuteurDTO() {
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

    public Auteur getAuteur(){
        Auteur auteur = new Auteur();
        auteur.setBiographie(this.biographie);
        auteur.setPays(this.pays);
        auteur.setNom(this.nom);
        auteur.setDateNaissance(this.dateNaissance);
        return auteur;
    }
}
