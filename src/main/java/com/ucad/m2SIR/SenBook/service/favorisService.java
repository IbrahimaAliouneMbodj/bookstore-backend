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


    public favorisService(FavorisRepository favorisRepository,
                          UtilisateurRepository utilisateurRepository,
                          LivreRepository livreRepository) {
        this.favorisRepository = favorisRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.livreRepository = livreRepository;
    }

    public List<Favoris> getUserFavoris(int idUser) {
        return favorisRepository.findByUtilisateurId(idUser);

    }

    public String addLivreFavori(int id_utilisateur, int idlivre) {
        Utilisateur user = utilisateurRepository.findById(id_utilisateur).orElse(null);

        Livre livre = livreRepository.findById(idlivre).orElse(null);

        if (livre == null)
            return "Failed : Livre introuvable";

        if (favorisRepository.existsByUtilisateurIdAndLivreId(id_utilisateur, idlivre)) {
            return "Failed : Le livre est déjà dans vos favoris";
        }


        FavorisId favorisId = new FavorisId();
        favorisId.setIdLivre(idlivre);
        favorisId.setIdUtilisateur(id_utilisateur);

        Favoris favoris = new Favoris();
        favoris.setId(favorisId);
        favoris.setLivre(livre);
        favoris.setUtilisateur(user);
        favoris.setDateAjout(Instant.now());

        if (favorisRepository.save(favoris).getId() != null)
            return "Success : Livre ajouté dans favoris";

        return "Failed : Une erreur s'est produite lors de l'ajout du livre";
    }

    @Transactional
    public String removeFromFavorite(int id_utilisateur, int idlivre) {
        favorisRepository.deleteByUtilisateurIdAndLivreId(id_utilisateur, idlivre);
        if (!favorisRepository.existsByUtilisateurIdAndLivreId(id_utilisateur, idlivre))
            return "Success : Livre retiré des favoris";
        return "Failed : Une erreur s'est produite lors de l'enlevement du livre de vos favoris";
    }
}