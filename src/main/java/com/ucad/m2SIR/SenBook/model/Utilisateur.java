package com.ucad.m2SIR.SenBook.model;

import com.ucad.m2SIR.SenBook.customTypes.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "utilisateurs", uniqueConstraints = {
        @UniqueConstraint(name = "nom_utilisateur", columnNames = {"nom_utilisateur"}),
        @UniqueConstraint(name = "email", columnNames = {"email"})
})
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nom_utilisateur", nullable = false, length = 100)
    private String nomUtilisateur;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;



    @ColumnDefault("'CLIENT'")
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "cree_le")
    private Instant creeLe;

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

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Instant getCreeLe() {
        return creeLe;
    }

    public void setCreeLe(Instant creeLe) {
        this.creeLe = creeLe;
    }

}