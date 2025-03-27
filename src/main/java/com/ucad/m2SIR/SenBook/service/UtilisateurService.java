package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.UserRole;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import com.ucad.m2SIR.SenBook.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurRepository.findAllByRole(UserRole.CLIENT);
    }

    public Utilisateur getUtilisateurById(int id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur getUtilisateurByNom(String nom) {
       return utilisateurRepository.findByNomUtilisateur(nom).orElse(null);
    }

    @Transactional
    public ResponseEntity<String> deleteUtilisateur(int id) {
        utilisateurRepository.deleteById(id);
        if(utilisateurRepository.existsById(id)){
            return new ResponseEntity<>("Erreur lors de la suppression",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
