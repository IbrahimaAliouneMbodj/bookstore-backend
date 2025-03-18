package com.ucad.m2SIR.SenBook.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "livres", uniqueConstraints = {
        @UniqueConstraint(name = "isbn", columnNames = {"isbn"})
})
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    @Column(name = "date_publication")
    private LocalDate datePublication;

    @Column(name = "editeur")
    private String editeur;

    @Column(name = "genre")
    private String genre;

    @Column(name = "image")
    private String image;

    @Column(name = "nombre_pages")
    private Integer nombrePages;


    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "cree_le")
    private Instant creeLe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getNombrePages() {
        return nombrePages;
    }

    public void setNombrePages(Integer nombrePages) {
        this.nombrePages = nombrePages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreeLe() {
        return creeLe;
    }

    public void setCreeLe(Instant creeLe) {
        this.creeLe = creeLe;
    }

}