package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.customTypes.UserRole;


public class UserRegisterDTO {
    private String nomUtilisateur;
    private String email;
    private String motDePasse;


    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String nomUtilisateur, String email, String motDePasse) {
        this.nomUtilisateur = nomUtilisateur;
        this.email = email;
        this.motDePasse = motDePasse;

    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

}
