package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.BookFormat;
import com.ucad.m2SIR.SenBook.dto.DetailsLivreDTO;
import com.ucad.m2SIR.SenBook.model.DetailsLivre;
import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.repository.DetailsLivreRepository;
import com.ucad.m2SIR.SenBook.repository.LivreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class DetailLivreService {
    private final DetailsLivreRepository detailsLivreRepository;
    private final LivreRepository livreRepository;

    public DetailLivreService(DetailsLivreRepository detailsLivreRepository,
                              LivreRepository livreRepository) {
        this.detailsLivreRepository = detailsLivreRepository;
        this.livreRepository = livreRepository;
    }

    public String createDetailLivre(DetailsLivreDTO detailLivre) {
        Optional<Livre> livre = livreRepository.findById(detailLivre.getIdLivre());
        if (livre.isPresent()) {
            DetailsLivre details = new DetailsLivre();
            details.setLivre(livre.get());
            if (detailLivre.getLangue().trim().isEmpty())
                return "Failed : Veuillez spécifier une langue pour le livre";
            details.setLangue(detailLivre.getLangue());
            if (detailLivre.getFormat() != BookFormat.PDF && detailLivre.getFormat() != BookFormat.PHYSICAL)
                return "Failed : Le format spécifié est n'existe pas";
            details.setFormat(detailLivre.getFormat());
            if (detailLivre.getPrixUnitaire().doubleValue() <= 0.0d)
                return "Failed : Le prix doit etre superieur a 0";
            details.setPrixUnitaire(detailLivre.getPrixUnitaire());
            if (detailsLivreRepository.save(details).getId() != null) {
                return "Success : Details du livre ajouté avec succés";
            }
        }
        return "Failed : Une erreur est survenue lors de l'ajout des details du livre";
    }

    public String updateDetailsLivre(DetailsLivreDTO detailLivre) {
        Optional<DetailsLivre> details = detailsLivreRepository.findById(detailLivre.getId());
        if (details.isPresent()) {
            if (detailLivre.getLangue().trim().isEmpty())
                return "Failed : Veuillez spécifier une langue pour le livre";
            details.get().setLangue(detailLivre.getLangue());
            if (detailLivre.getFormat() != BookFormat.PDF && detailLivre.getFormat() != BookFormat.PHYSICAL)
                return "Failed : Le format spécifié est n'existe pas";
            details.get().setFormat(detailLivre.getFormat());
            if (detailLivre.getPrixUnitaire().doubleValue() <= 0.0d)
                return "Failed : Le prix doit etre superieur a 0";
            details.get().setPrixUnitaire(detailLivre.getPrixUnitaire());
            if (detailsLivreRepository.save(details.get()).getId() != null) {
                return "Success : Details du livre modifié avec succés";
            }
        }
        return "Failed : Une erreur est survenue lors de la modification des details du livre";
    }

    public String removeDetailsLivre(int livreId) {
        if (detailsLivreRepository.existsById(livreId)) {
            detailsLivreRepository.deleteById(livreId);
            return "Success : Les details du livre sont correctement supprimé";
        }
        return "Failed : Une erreur est survenue lors de la suppression des details";
    }
}
