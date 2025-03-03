package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.UserRole;
import com.ucad.m2SIR.SenBook.dto.UserRegisterDTO;
import com.ucad.m2SIR.SenBook.configuration.PasswordConfig;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
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

    public UserAuthService(UtilisateurRepository utilisateurRepository, PasswordConfig passwordConfig) {

        this.utilisateurRepository = utilisateurRepository;
        this.passwordConfig = passwordConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> user = utilisateurRepository.findByNomUtilisateur(username);
        if(user.isPresent()) {
            ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
            SimpleGrantedAuthority role1 = new SimpleGrantedAuthority(user.get().getRole().toString());
            roles.add(role1);
            return new User(user.get().getNomUtilisateur(), user.get().getMotDePasse(), roles);
        }
        throw new UsernameNotFoundException("Username Not Found!");
    }

    public boolean saveUtilisateur(UserRegisterDTO userDto){
        userDto.setMotDePasse(passwordConfig.getEncoder().encode(userDto.getMotDePasse()));
        Utilisateur user = utilisateurRepository.save(convertUserRegisterDtoToEntity(userDto));
        return user.getId() != null;
    }

    private Utilisateur convertUserRegisterDtoToEntity(UserRegisterDTO userDto) {
        Utilisateur user = new Utilisateur();
        user.setNomUtilisateur(userDto.getNomUtilisateur());
        user.setEmail(userDto.getEmail());
        user.setMotDePasse(userDto.getMotDePasse());
        user.setRole(UserRole.CLIENT);
        user.setCreeLe(Instant.now());
        return user;
    }
}
