package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.model.Auteur;
import com.ucad.m2SIR.SenBook.repository.AuteurRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class AuteurService {
    private final AuteurRepository auteurRepository;


    public AuteurService(AuteurRepository auteurRepository) {
        this.auteurRepository = auteurRepository;
    }

    public String createAuteur(Auteur auteur) {
        auteur.setCreeLe(Instant.now());
        Auteur response = auteurRepository.save(auteur);
        return response.getId() != null
                ? "Success : L'auteur a été créé avec succés"
                : "Failed : Une erreur est survenue lors de la création de l'auteur";
    }

    public String removeAuteur(int auteurId) {
        auteurRepository.deleteById(auteurId);
        return !auteurRepository.existsById(auteurId)
                ? "Success : L'auteur a été supprimé correctement"
                : "Failed : Une erreur est survenue lors de la suppression de l'auteur";
    }

    public String updateAuteur(Auteur aut) {
        Auteur auteur = auteurRepository.findById(aut.getId()).orElse(null);
        Auteur response = null;
        if(auteur!= null) {
            auteur.setPays(aut.getPays());
            auteur.setNom(aut.getNom());
            auteur.setBiographie(aut.getBiographie());
            auteur.setDateNaissance(aut.getDateNaissance());
            response = auteurRepository.save(auteur);
        }
        return response != null
                ? "Success : Auteur a été mis a jour correctement"
                : "Failed : Une erreur est survenue lors de la mise a jour de l'auteur";

    }

    public List<Auteur> getAuteurs() {
        return auteurRepository.findAll();
    }

    public Auteur getAuteurById(int id) {
        return auteurRepository.findById(id).orElse(null);
    }

    public List<Auteur> getAuteurByNom(String nom) {
        return auteurRepository.findByNomContaining(nom);
    }

}
