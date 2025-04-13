package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.configuration.PasswordConfig;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import com.ucad.m2SIR.SenBook.repository.UtilisateurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordConfig passwordConfig;

    public ClientService(UtilisateurRepository utilisateurRepository, PasswordConfig passwordConfig) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordConfig = passwordConfig;
    }

    public Utilisateur getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            return utilisateurRepository.findByNomUtilisateur(username).orElse(null);
        }
        return null;
    }

    public String changerMotDePasse(String password) {
        if (password.trim().length() > 8) {
            Utilisateur user = getCurrentUser();
            user.setMotDePasse(passwordConfig.getEncoder().encode(password));
            utilisateurRepository.save(user);
            return "Success : Mot de passe changé avec succés";
        }
        return "Failed : Le mot de passe ne depasse pas 8 caractères";
    }
}
