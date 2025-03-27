package com.ucad.m2SIR.SenBook.dto;

import com.ucad.m2SIR.SenBook.model.Auteur;
import com.ucad.m2SIR.SenBook.model.Livre;

import java.time.LocalDate;
import java.util.List;

public class LivreDTO{
    private String titre;
    private String isbn;
    private LocalDate datePublication;
    private String editeur;
    private String genre;
    private String image;
    private Integer nombrePages;
    private String description;
    private List<Auteur> auteurs;

    public LivreDTO() {
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

    public List<Auteur> getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(List<Auteur> auteurs) {
        this.auteurs = auteurs;
    }


    public Livre getLivre(){

        Livre livre = new Livre();
        livre.setTitre(this.titre);
        livre.setEditeur(this.editeur);
        livre.setIsbn(this.isbn);
        livre.setDatePublication(this.datePublication);
        livre.setGenre(this.genre);
        livre.setImage(this.image);
        livre.setNombrePages(this.nombrePages);
        livre.setDescription(this.description);

        return livre;
    }
}
