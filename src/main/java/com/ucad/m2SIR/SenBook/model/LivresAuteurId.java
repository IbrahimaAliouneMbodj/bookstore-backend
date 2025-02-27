package com.ucad.m2SIR.SenBook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.util.Objects;

@Embeddable
public class LivresAuteurId implements java.io.Serializable {
    private static final long serialVersionUID = 6019989951908184817L;
    @Column(name = "id_livre", nullable = false)
    private Integer idLivre;

    @Column(name = "id_auteur", nullable = false)
    private Integer idAuteur;

    public Integer getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(Integer idLivre) {
        this.idLivre = idLivre;
    }

    public Integer getIdAuteur() {
        return idAuteur;
    }

    public void setIdAuteur(Integer idAuteur) {
        this.idAuteur = idAuteur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LivresAuteurId entity = (LivresAuteurId) o;
        return Objects.equals(this.idAuteur, entity.idAuteur) &&
                Objects.equals(this.idLivre, entity.idLivre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAuteur, idLivre);
    }

}