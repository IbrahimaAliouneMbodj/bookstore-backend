package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.dto.FavorisDTO;
import com.ucad.m2SIR.SenBook.model.Favoris;
import com.ucad.m2SIR.SenBook.model.FavorisId;
import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.model.Utilisateur;
import com.ucad.m2SIR.SenBook.repository.FavorisRepository;
import com.ucad.m2SIR.SenBook.repository.LivreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
@Service
public class favorisService {
    private final FavorisRepository favorisRepository;
    private final ClientService clientService;
    private final LivreRepository livreRepository;


    public favorisService(FavorisRepository favorisRepository,
                          ClientService clientService,
                          LivreRepository livreRepository) {
        this.favorisRepository = favorisRepository;
        this.clientService = clientService;
        this.livreRepository = livreRepository;
    }

    public List<FavorisDTO> getUserFavoris() {
        return favorisRepository.findByUtilisateurId(clientService.getCurrentUser().getId())
                .stream()
                .map(FavorisDTO::new)
                .toList();
    }

    public String addLivreFavori(int idlivre) {
        Utilisateur user = clientService.getCurrentUser();

        Livre livre = livreRepository.findById(idlivre).orElse(null);

        if (livre == null)
            return "Failed : Livre introuvable";

        if (favorisRepository.existsByUtilisateurIdAndLivreId(user.getId(), idlivre)) {
            return "Failed : Le livre est déjà dans vos favoris";
        }

        FavorisId favorisId = new FavorisId();
        favorisId.setIdLivre(idlivre);
        favorisId.setIdUtilisateur(user.getId());

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
    public String removeFromFavorite(int idlivre) {
        favorisRepository.deleteByUtilisateurIdAndLivreId(clientService.getCurrentUser().getId(), idlivre);
        if (!favorisRepository.existsByUtilisateurIdAndLivreId(clientService.getCurrentUser().getId(), idlivre))
            return "Success : Livre retiré des favoris";
        return "Failed : Une erreur s'est produite lors de l'enlevement du livre de vos favoris";
    }
}