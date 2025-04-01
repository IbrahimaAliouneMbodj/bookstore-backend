package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.UserRole;
import com.ucad.m2SIR.SenBook.dto.UserRegisterDTO;
import com.ucad.m2SIR.SenBook.configuration.PasswordConfig;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import com.ucad.m2SIR.SenBook.repository.TokenRepository;
import com.ucad.m2SIR.SenBook.repository.UtilisateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserAuthService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordConfig passwordConfig;
    private final TokenRepository tokenRepository;

    public UserAuthService(UtilisateurRepository utilisateurRepository,
                           PasswordConfig passwordConfig,
                           TokenRepository tokenRepository) {

        this.utilisateurRepository = utilisateurRepository;
        this.passwordConfig = passwordConfig;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> user = utilisateurRepository.findByNomUtilisateur(username);
        if (user.isPresent()) {
            ArrayList<GrantedAuthority> roles = new ArrayList<>();
            SimpleGrantedAuthority role1 = new SimpleGrantedAuthority(user.get().getRole().toString());
            roles.add(role1);
            return new User(user.get().getNomUtilisateur(), user.get().getMotDePasse(), roles);
        }
        throw new UsernameNotFoundException("Username Not Found!");
    }

    public String saveUtilisateur(UserRegisterDTO userDto) {
        String response = "";
        if (userDto.getPassword().trim().length() < 8)
            response = "Failed : Le mot de passe doit avoir au minimum 8 caractères";
        if (userDto.getUsername().trim().length() < 4)
            response = "Failed : Le nom d'utilisateur doit avoir au minimum 4 caractères";
        if (response.trim().isEmpty()) {
            response = "Failed : Le nom d'utilisateur ou l'email specifé existe déja";
            if (!utilisateurRepository.existsByNomUtilisateur(userDto.getUsername())
                    && !utilisateurRepository.existsByEmail(userDto.getEmail())) {
                userDto.setPassword(passwordConfig.getEncoder().encode(userDto.getPassword()));
                Utilisateur user = utilisateurRepository.save(convertUserRegisterDtoToEntity(userDto));
                response = user.getId() != null
                        ? "Success : Inscription reussie"
                        : "Failed : Une erreur s'est produite lors de l'enregistrement des informations";
            }
        }
        return response;
    }

    private Utilisateur convertUserRegisterDtoToEntity(UserRegisterDTO userDto) {
        Utilisateur user = new Utilisateur();
        user.setNomUtilisateur(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setMotDePasse(userDto.getPassword());
        user.setRole(UserRole.CLIENT);
        user.setCreeLe(Instant.now());
        return user;
    }

    public void revokeAllUserTokens(Utilisateur user) {
        var validUserTokens = tokenRepository.findValidTokensByUtilisateurId(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
