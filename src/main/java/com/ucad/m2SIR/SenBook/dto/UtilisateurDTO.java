package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.customTypes.UserRole;
import com.ucad.m2SIR.SenBook.model.Utilisateur;


public class UtilisateurDTO {
    private Integer id;
    private String nomUtilisateur;
    private String email;
    private UserRole role;

    public UtilisateurDTO() {
    }

    public UtilisateurDTO(Utilisateur utilisateur) {
        this.id = utilisateur.getId();
        this.nomUtilisateur = utilisateur.getNomUtilisateur();
        this.email = utilisateur.getEmail();
        this.role = utilisateur.getRole();
    }

    public UtilisateurDTO(Integer id, String nomUtilisateur, String email, UserRole role) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.email = email;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
