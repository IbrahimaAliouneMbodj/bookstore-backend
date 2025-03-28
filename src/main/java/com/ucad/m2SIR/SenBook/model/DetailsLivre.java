package com.ucad.m2SIR.SenBook.model;

import com.ucad.m2SIR.SenBook.customTypes.BookFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "details_livre", indexes = {
        @Index(name = "id_livre", columnList = "id_livre")
})
public class DetailsLivre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_livre", nullable = false)
    private com.ucad.m2SIR.SenBook.model.Livre livre;

    @Enumerated(EnumType.STRING)
    @Column(name = "format")
    private BookFormat format;

    @Column(name = "prix_unitaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;

    @Column(name = "langue", length = 50)
    private String langue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public com.ucad.m2SIR.SenBook.model.Livre getLivre() {
        return livre;
    }

    public void setLivre(com.ucad.m2SIR.SenBook.model.Livre livre) {
        this.livre = livre;
    }

    public BookFormat getFormat() {
        return format;
    }

    public void setFormat(BookFormat format) {
        this.format = format;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

}