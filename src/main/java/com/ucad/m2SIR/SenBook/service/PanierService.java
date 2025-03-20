package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.model.DetailsLivre;
import com.ucad.m2SIR.SenBook.model.Panier;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import com.ucad.m2SIR.SenBook.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class PanierService {

    private final DetailsLivreRepository detailsLivreRepository;
    private final PanierRepository panierRepository;
    private final UtilisateurRepository utilisateurRepository;

    public PanierService(DetailsLivreRepository detailsLivreRepository, PanierRepository panierRepository, UtilisateurRepository utilisateurRepository) {
        this.detailsLivreRepository = detailsLivreRepository;
        this.panierRepository = panierRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<DetailsLivre> getPanier(int idUser){
        List<DetailsLivre> detailsLivres = new ArrayList<>();
        for(Panier panier : panierRepository.findAllByUtilisateurId(idUser))
        {
            detailsLivres.add(panier.getDetailLivre());
        }
        return detailsLivres;
    }

    public ResponseEntity<String> addToPanier(int livreId, int userId){
        Utilisateur user = utilisateurRepository.findById(userId).get();
        DetailsLivre livre = detailsLivreRepository.findByLivreId(livreId).get();
        Panier panier = new Panier();
        panier.setDetailLivre(livre);
        panier.setUtilisateur(user);
        panier.setDateAjout(Instant.now());
        panier.setQuantite(1);
        Panier response;
        response = panierRepository.save(panier);
        if(response.getId() != null){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public ResponseEntity<String> clearPanier(int userId){
        Utilisateur user = utilisateurRepository.findById(userId).get();
        panierRepository.deleteAllByUtilisateur(user);
        if(panierRepository.existsByUtilisateur(user)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> removeFromPanier(int livreId, int userId){
        Utilisateur user = utilisateurRepository.findById(userId).get();
        DetailsLivre livre = detailsLivreRepository.findByLivreId(livreId).get();
        panierRepository.deleteAllByUtilisateurAndDetailLivre(user,livre);
        if(panierRepository.existsByUtilisateurAndDetailLivre(user,livre)){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
