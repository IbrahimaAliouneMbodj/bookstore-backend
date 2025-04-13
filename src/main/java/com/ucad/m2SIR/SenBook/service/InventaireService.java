package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.dto.InventaireDTO;
import com.ucad.m2SIR.SenBook.model.DetailsLivre;
import com.ucad.m2SIR.SenBook.model.Inventaire;
import com.ucad.m2SIR.SenBook.repository.DetailsLivreRepository;
import com.ucad.m2SIR.SenBook.repository.InventaireRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class InventaireService {
    private final InventaireRepository inventaireRepository;
    private final DetailsLivreRepository detailsLivreRepository;

    public InventaireService(InventaireRepository inventaireRepository,
                             DetailsLivreRepository detailsLivreRepository) {
        this.inventaireRepository = inventaireRepository;
        this.detailsLivreRepository = detailsLivreRepository;
    }

    //Ajouter un nouvel article en stock
    public String ajouterStock(InventaireDTO inventaireDTO) {
        DetailsLivre detailsLivre = detailsLivreRepository.findById(inventaireDTO.getDetailLivre().getIdLivre())
                .orElse(null);
        if (detailsLivre == null)
            return "Failed : Détails du livre introuvable";
        if (!inventaireRepository.existsByDetailLivre(detailsLivre)) {
            Inventaire inventaire = new Inventaire();
            inventaire.setDetailLivre(detailsLivre);
            inventaire.setQuantite(inventaireDTO.getQuantite());
            inventaireRepository.save(inventaire);
            return "Success : Stock ajouté avec succés";
        }
        return "Failed : Erreur, Le stock existe deja essayer de le modifier peut etre";
    }

    // Modifier le stock d’un livre (augmenter ou diminuer)
    public String modifierStock(InventaireDTO inventaireDTO) {
        Inventaire inventaire = inventaireRepository.findByDetailLivreId(inventaireDTO.getDetailLivre().getIdLivre()).orElse(null);
        if (inventaire == null)
            return "Failed : Le stock demandé pour le produit est introuvable";

        inventaire.setQuantite(inventaire.getQuantite() + inventaireDTO.getQuantite());
        inventaireRepository.save(inventaire);
        return "Success : Stock modifié avec succés";
    }

    //Supprimer un stock
    public String supprimerStock(Integer detailsLivreId) {
        Inventaire inventaire = inventaireRepository.findByDetailLivreId(detailsLivreId).orElse(null);
        if (inventaire == null)
            return "Failed : Stock introuvable";

        inventaireRepository.delete(inventaire);
        return "Success : L'inventaire pour le produit spécifié a été supprimé avec succés";
    }

    //Récupérer tout l’inventaire
    public List<InventaireDTO> getTousInventaires() {
        return inventaireRepository
                .findAll()
                .stream()
                .map(InventaireDTO::new)
                .toList();
    }

    //Récupérer l’inventaire d’un livre spécifique
    public InventaireDTO getInventaireParLivre(Integer detailsLivreId) {
        Inventaire inventaire = inventaireRepository.findByDetailLivreId(detailsLivreId).orElse(null);
        if (inventaire != null)
            return new InventaireDTO(inventaire);
        return null;
    }

    //Récupérer les articles avec un stock faible
    public List<InventaireDTO> getFaibleStock(int seuil) {
        return inventaireRepository
                .findByQuantiteLessThan(seuil)
                .stream()
                .map(InventaireDTO::new)
                .toList();
    }

    public Integer getStockCount() {
        return inventaireRepository.getStockCount();
    }
}
