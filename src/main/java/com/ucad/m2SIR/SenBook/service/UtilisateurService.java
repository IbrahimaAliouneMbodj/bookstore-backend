package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.UserRole;
import com.ucad.m2SIR.SenBook.dto.UtilisateurDTO;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import com.ucad.m2SIR.SenBook.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<UtilisateurDTO> getUtilisateurs() {
        List<Utilisateur> allUsers = utilisateurRepository.findAllByRole(UserRole.CLIENT);
        List<UtilisateurDTO> users = new ArrayList<>();
        for (Utilisateur user : allUsers) {
            users.add(new UtilisateurDTO(user));
        }
        return users;
    }

    public Utilisateur getUtilisateurById(int id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur getUtilisateurByNom(String nom) {
        return utilisateurRepository.findByNomUtilisateur(nom).orElse(null);
    }

    @Transactional
    public String deleteUtilisateur(int id) {
        utilisateurRepository.deleteById(id);
        return !utilisateurRepository.existsById(id)
                ? "Success : Utilisateur supprimé avec succés"
                : "Failed : Erreur lors de la suppression de l'utilisateur";
    }
}
