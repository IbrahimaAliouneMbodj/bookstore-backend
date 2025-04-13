package com.ucad.m2SIR.SenBook.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "favoris", indexes = {
        @Index(name = "id_livre", columnList = "id_livre")
})
public class Favoris {
    @EmbeddedId
    private FavorisId id;

    @MapsId("idUtilisateur")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private com.ucad.m2SIR.SenBook.model.Utilisateur utilisateur;

    @MapsId("idLivre")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_livre", nullable = false)
    private com.ucad.m2SIR.SenBook.model.Livre livre;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_ajout")
    private Instant dateAjout;

    public FavorisId getId() {
        return id;
    }

    public void setId(FavorisId id) {
        this.id = id;
    }

    public com.ucad.m2SIR.SenBook.model.Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(com.ucad.m2SIR.SenBook.model.Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public com.ucad.m2SIR.SenBook.model.Livre getLivre() {
        return livre;
    }

    public void setLivre(com.ucad.m2SIR.SenBook.model.Livre livre) {
        this.livre = livre;
    }

    public Instant getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Instant dateAjout) {
        this.dateAjout = dateAjout;
    }

}