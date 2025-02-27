package com.ucad.m2SIR.SenBook.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "livres_auteurs", indexes = {
        @Index(name = "id_auteur", columnList = "id_auteur")
})
public class LivresAuteur {
    @EmbeddedId
    private LivresAuteurId id;

    @MapsId("idLivre")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_livre", nullable = false)
    private Livre livre;

    @MapsId("idAuteur")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_auteur", nullable = false)
    private Auteur auteur;

    public LivresAuteurId getId() {
        return id;
    }

    public void setId(LivresAuteurId id) {
        this.id = id;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Auteur getAuteur() {
        return auteur;
    }

    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

}