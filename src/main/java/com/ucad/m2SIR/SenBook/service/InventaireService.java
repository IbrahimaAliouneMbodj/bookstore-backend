package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.dto.InventaireDTO;
import com.ucad.m2SIR.SenBook.model.DetailsLivre;
import com.ucad.m2SIR.SenBook.model.Inventaire;
import com.ucad.m2SIR.SenBook.repository.DetailsLivreRepository;
import com.ucad.m2SIR.SenBook.repository.InventaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InventaireService {

    private static final Logger log = LoggerFactory.getLogger(InventaireService.class);
    private final InventaireRepository inventaireRepository;
    private final DetailsLivreRepository detailsLivreRepository;

    public InventaireService(InventaireRepository inventaireRepository, DetailsLivreRepository detailsLivreRepository) {
        this.inventaireRepository = inventaireRepository;
        this.detailsLivreRepository = detailsLivreRepository;
    }

    //Ajouter un nouvel article en stock
    public ResponseEntity<Object> ajouterStock(InventaireDTO inventaireDTO) {
        DetailsLivre detailsLivre = detailsLivreRepository.findById(inventaireDTO.getDetailLivreId())
                .orElse(null);
        if(detailsLivre == null)
            return new ResponseEntity<>("Détail du livre introuvable",HttpStatus.BAD_REQUEST);
        if(!inventaireRepository.existsByDetailLivre(detailsLivre)) {
            Inventaire inventaire = new Inventaire();
            inventaire.setDetailLivre(detailsLivre);
            inventaire.setQuantite(inventaireDTO.getQuantite());
            inventaireRepository.save(inventaire);
            return new ResponseEntity<>("Stock ajouté avec succés",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Erreur, Le stock existe deja essayer de le modifier peut etre",HttpStatus.BAD_REQUEST);
    }

    // Modifier le stock d’un livre (augmenter ou diminuer)
    public ResponseEntity<Object> modifierStock(InventaireDTO inventaireDTO) {
        Inventaire inventaire = inventaireRepository.findByDetailLivreId(inventaireDTO.getDetailLivreId()).orElse(null);
        if(inventaire == null)
            return new ResponseEntity<>("Stock introuvable",HttpStatus.NOT_FOUND);

        inventaire.setQuantite(inventaire.getQuantite() + inventaireDTO.getQuantite());
        inventaireRepository.save(inventaire);
        return new ResponseEntity<>("Stock modifié avec succés",HttpStatus.OK);
    }

    //Supprimer un stock
    public ResponseEntity<Object> supprimerStock(Integer detailsLivreId) {
        Inventaire inventaire = inventaireRepository.findByDetailLivreId(detailsLivreId).orElse(null);
        if(inventaire == null)
            return new ResponseEntity<>("Stock introuvable",HttpStatus.NOT_FOUND);

        inventaireRepository.delete(inventaire);
        return new ResponseEntity<>("Stock supprimé avec succés",HttpStatus.OK);
    }

    //Récupérer tout l’inventaire
    public ResponseEntity<Object> getTousInventaires() {
        return new ResponseEntity<>(inventaireRepository
                .findAll()
                    .stream()
                    .map(InventaireDTO::new)
                    .toList()
                ,HttpStatus.OK);
    }

    //Récupérer l’inventaire d’un livre spécifique
    public ResponseEntity<Object> getInventaireParLivre(Integer detailsLivreId) {
         Inventaire inventaire = inventaireRepository.findByDetailLivreId(detailsLivreId).orElse(null);
         if(inventaire==null)
         {
             return new ResponseEntity<>("Stock introuvable",HttpStatus.NOT_FOUND);
         }
         return new ResponseEntity<>(new InventaireDTO(inventaire),HttpStatus.OK);
    }

    //Récupérer les articles avec un stock faible
    public ResponseEntity<Object> getFaibleStock(int seuil) {
        return new ResponseEntity<>(inventaireRepository
                .findByQuantiteLessThan(seuil)
                    .stream()
                    .map(InventaireDTO::new)
                    .toList()
                ,HttpStatus.OK);
    }
}
