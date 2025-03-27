package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.model.Favoris;
import com.ucad.m2SIR.SenBook.model.FavorisId;
import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import com.ucad.m2SIR.SenBook.repository.FavorisRepository;
import com.ucad.m2SIR.SenBook.repository.LivreRepository;
import com.ucad.m2SIR.SenBook.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
@Service
public class favorisService {
    private final FavorisRepository favorisRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final LivreRepository livreRepository;


    public favorisService(FavorisRepository favorisRepository, UtilisateurRepository utilisateurRepository, LivreRepository livreRepository) {
        this.favorisRepository = favorisRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.livreRepository = livreRepository;
    }

    public List<Favoris> getUserFavoris(int idUser){
        return  favorisRepository.findByUtilisateurId(idUser);

    }

    public Favoris addLivreFavori(int id_utilisateur, int idlivre ){
        Utilisateur user = utilisateurRepository.findById(id_utilisateur)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Livre livre = livreRepository.findById(idlivre)
                .orElseThrow(() -> new RuntimeException("Livre introuvable"));

        if (favorisRepository.existsByUtilisateurIdAndLivreId(id_utilisateur, idlivre)) {
            throw new RuntimeException("Le livre est déjà dans vos favoris");
        }


        FavorisId favorisId = new FavorisId();
        favorisId.setIdLivre(idlivre);
        favorisId.setIdUtilisateur(id_utilisateur);

        Favoris favoris = new Favoris();
        favoris.setId(favorisId);
        favoris.setLivre(livre);
        favoris.setUtilisateur(user);
        favoris.setDateAjout(Instant.now());

        return favorisRepository.save(favoris);

    }

    @Transactional
    public void RemoveFromFavorite(int id_utilisateur, int idlivre){

        favorisRepository.deleteByUtilisateurIdAndLivreId(id_utilisateur,idlivre);
    }
}
