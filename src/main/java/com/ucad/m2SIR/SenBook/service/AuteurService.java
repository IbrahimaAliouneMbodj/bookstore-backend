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
        String response = "";
        if (auteur.getNom().trim().length() < 4) {
            response = "Failed : Le nom doit depasser 4 charactères";
        }
        if (auteur.getPays() == null || auteur.getPays().trim().isEmpty()) {
            response = "Failed : Le nom du pays ne doit pas etre vide";
        }
        if(auteurRepository.existsByNom(auteur.getNom())) {
            response = "Failed : Le nom existe deja";
        }
        if (response.isEmpty()) {
            auteur.setCreeLe(Instant.now());
            auteur.setId(null);
            if (auteurRepository.save(auteur).getId() != null) {
                response = "Success : L'auteur a été créé avec succés";
            } else {
                response = "Failed : Une erreur est survenue lors de la création de l'auteur";
            }
        }
        return response;
    }

    public String removeAuteur(int auteurId) {
        auteurRepository.deleteById(auteurId);
        return !auteurRepository.existsById(auteurId)
                ? "Success : L'auteur a été supprimé correctement"
                : "Failed : Une erreur est survenue lors de la suppression de l'auteur";
    }

    public String updateAuteur(Auteur aut) {
        Auteur auteur = auteurRepository.findById(aut.getId()).orElse(null);

        if (auteur != null) {
            String response = "";
            if (aut.getNom().trim().length() < 4) {
                response = "Failed : Le nom doit depasser 4 charactères";
            }
            if (aut.getPays() == null || aut.getPays().trim().isEmpty()) {
                response = "Failed : Le nom du pays ne doit pas etre vide";
            }
            if(!aut.getNom().equals(auteur.getNom()) && auteurRepository.existsByNom(aut.getNom())) {
                response = "Failed : Le nom spécifié existe deja";
            }
            if (response.isEmpty()) {
                auteur.setPays(aut.getPays());
                auteur.setNom(aut.getNom());
                auteur.setBiographie(aut.getBiographie());
                auteur.setDateNaissance(aut.getDateNaissance());
                if (auteurRepository.save(auteur).getId() != null) {
                    response = "Success : Auteur a été mis a jour correctement";
                } else {
                    response = "Failed : Une erreur est survenue lors de la mise a jour de l'auteur";
                }
            }
            return response;
        }
        return "Failed : L'auteur que vous essayer de modifié n'existe pas";
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

    public boolean doesAuteurExiste(int id) {
        return auteurRepository.existsById(id);
    }
}
