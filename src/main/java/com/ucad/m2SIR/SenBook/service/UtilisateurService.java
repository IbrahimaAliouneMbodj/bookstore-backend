package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.configuration.PasswordConfig;
import com.ucad.m2SIR.SenBook.dto.UtilisateurDTO;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import com.ucad.m2SIR.SenBook.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordConfig passwordConfig;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, PasswordConfig passwordConfig) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordConfig = passwordConfig;
    }

    public List<UtilisateurDTO> getUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream()
                .map(UtilisateurDTO::new)
                .toList();
    }

    public Utilisateur getUtilisateurById(int id) {
        return utilisateurRepository.findById(id).orElse(null);
    }

    public List<UtilisateurDTO> getUtilisateurByText(String text) {
        return text.trim().isEmpty()
                ? getUtilisateurs()
                : utilisateurRepository.findAllByText(text)
                    .stream()
                    .map(UtilisateurDTO::new)
                    .toList();
    }

    @Transactional
    public String updateUtilisateur(Utilisateur user) {
        Utilisateur utilisateur = utilisateurRepository.findById(user.getId()).orElse(null);
        if (utilisateur == null) {
            return "Failed : Utilisateur introuvable";
        }
        if (!user.getNomUtilisateur().equals(utilisateur.getNomUtilisateur())) {
            if (user.getNomUtilisateur().trim().length() < 4) {
                return "Failed : Le nom d'utilisateur doit avoir au minimum 4 caractères";
            }
            if (utilisateurRepository.existsByNomUtilisateur(user.getNomUtilisateur())) {
                return "Failed : Le nom d'utilisateur existe deja";
            }
            utilisateur.setNomUtilisateur(user.getNomUtilisateur());
        }
        if (!user.getEmail().equals(utilisateur.getEmail())) {
            if (utilisateurRepository.existsByEmail(user.getEmail())) {
                return "Failed : L'email est deja utilisé";
            }
            utilisateur.setEmail(user.getEmail());
        }
        if (user.getRole() != utilisateur.getRole()) {
            utilisateur.setRole(user.getRole());
        }
        if (!user.getMotDePasse().trim().isEmpty()) {
            if (user.getMotDePasse().trim().length() < 8) {
                return "Failed : Le mot de passe doit avoir au minimum 8 caractères";
            }
            utilisateur.setMotDePasse(passwordConfig.getEncoder().encode(user.getMotDePasse()));
        }

        return utilisateurRepository.save(utilisateur).getId() != null
                ? "Success : Modifications éffectués avec succés"
                : "Failed : Une erreur s'est produite lors de l'enregistrement des informations";
    }

    @Transactional
    public String deleteUtilisateur(int id) {
        utilisateurRepository.deleteById(id);
        return !utilisateurRepository.existsById(id)
                ? "Success : Utilisateur supprimé avec succés"
                : "Failed : Erreur lors de la suppression de l'utilisateur";
    }

    public Integer getUserCount() {
        return utilisateurRepository.getUserCount();
    }
}
