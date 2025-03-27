package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.CommandStatus;
import com.ucad.m2SIR.SenBook.customTypes.PaymentStatus;
import com.ucad.m2SIR.SenBook.dto.DetailsCommandeDTO;
import com.ucad.m2SIR.SenBook.dto.PaiementDTO;
import com.ucad.m2SIR.SenBook.model.*;
import com.ucad.m2SIR.SenBook.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final DetailsCommandeRepository detailsCommandeRepository;
    private final InventaireRepository inventaireRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final DetailsLivreRepository detailsLivreRepository;
    private final PaiementRepository paiementRepository;

    public CommandeService(CommandeRepository commandeRepository,
                           DetailsCommandeRepository detailsCommandeRepository,
                           InventaireRepository inventaireRepository,
                           UtilisateurRepository utilisateurRepository,
                           DetailsLivreRepository detailsLivreRepository,
                           PaiementRepository paiementRepository) {
        this.commandeRepository = commandeRepository;
        this.detailsCommandeRepository = detailsCommandeRepository;
        this.inventaireRepository = inventaireRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.detailsLivreRepository = detailsLivreRepository;
        this.paiementRepository = paiementRepository;
    }


    // Crée une nouvelle commande pour un utilisateur.
    public ResponseEntity<Object> creerCommande(Integer utilisateurId, List<DetailsCommandeDTO> details) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
        if(utilisateur==null)
            return new ResponseEntity<>("Utilisateur introuvable",HttpStatus.NOT_FOUND);

        Commande commande = new Commande();
        commande.setUtilisateur(utilisateur);
        commande.setDateCommande(Instant.now());
        commande.setStatut(CommandStatus.PENDING);
        commande.setPrixTotal(BigDecimal.ZERO);

        commande = commandeRepository.save(commande);

        BigDecimal prixTotal = BigDecimal.ZERO;

        for (DetailsCommandeDTO detailDTO : details) {
            DetailsLivre detailsLivre = detailsLivreRepository.findById(detailDTO.getDetailsLivreId()).orElse(null);
            if(detailsLivre==null)
                return new ResponseEntity<>("Détail du livre introuvable",HttpStatus.NOT_FOUND);

            Inventaire inventaire = inventaireRepository.findByDetailLivre(detailsLivre).orElse(null);
            if (inventaire==null)
                return new ResponseEntity<>("Stock indisponible",HttpStatus.NOT_FOUND);

            if (inventaire.getQuantite() < detailDTO.getQuantite()) {
                return new ResponseEntity<>("Stock insuffisant pour le livre: " + detailsLivre.getLivre().getTitre(),HttpStatus.INSUFFICIENT_STORAGE);
            }

            inventaire.setQuantite(inventaire.getQuantite() - detailDTO.getQuantite());
            inventaireRepository.save(inventaire);

            DetailsCommande detailsCommande = new DetailsCommande();
            detailsCommande.setCommande(commande);
            detailsCommande.setDetailLivre(detailsLivre);
            detailsCommande.setQuantite(detailDTO.getQuantite());
            detailsCommandeRepository.save(detailsCommande);

            prixTotal = prixTotal.add(detailsLivre.getPrixUnitaire().multiply(BigDecimal.valueOf(detailDTO.getQuantite())));
        }

        commande.setPrixTotal(prixTotal);
        commandeRepository.save(commande);
        return new ResponseEntity<>("Commande créé avec succés",HttpStatus.CREATED);
    }

    // Annule une commande et restaure les stocks.
    public ResponseEntity<Object> annulerCommande(Integer commandeId) {
        Commande commande = commandeRepository.findById(commandeId).orElse(null);

        if(commande == null)
            return new ResponseEntity<>("Commande introuvable",HttpStatus.NOT_FOUND);

        if (commande.getStatut() == CommandStatus.DELIVERED) {
            return new ResponseEntity<>("Impossible d'annuler une commande complétée",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<DetailsCommande> details = detailsCommandeRepository.findByCommande(commande);
        for (DetailsCommande detail : details) {
            Inventaire inventaire = inventaireRepository.findByDetailLivre(detail.getDetailLivre()).orElse(null);
            if(inventaire == null)
                return new ResponseEntity<>("Stock introuvable",HttpStatus.NOT_FOUND);
            inventaire.setQuantite(inventaire.getQuantite() + detail.getQuantite());
            inventaireRepository.save(inventaire);
        }

        commande.setStatut(CommandStatus.CANCELLED);
        commandeRepository.save(commande);
        return new ResponseEntity<>("Annulé avec succés",HttpStatus.OK);
    }

    // Récupère toutes les commandes d'un utilisateur
    public ResponseEntity<Object> getCommandes(Integer utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);

        if(utilisateur==null)
            return new ResponseEntity<>("Utilisateur introuvable",HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(commandeRepository.findByUtilisateur(utilisateur),HttpStatus.OK);
    }

    // Associe un paiement à une commande.
    public ResponseEntity<Object> effectuerPaiement(PaiementDTO paiementDTO) {
        Commande commande = commandeRepository.findById(paiementDTO.getCommandeId()).orElse(null);
        if(commande == null)
            return new ResponseEntity<>("Commande introuvable",HttpStatus.NOT_FOUND);

        if (commande.getStatut() == CommandStatus.DELIVERED) {
            return new ResponseEntity<>("La commande a déjà été validé",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Paiement paiement = new Paiement();
        paiement.setCommande(commande);
        paiement.setMontant(paiementDTO.getMontant());
        paiement.setMethodePaiement(paiementDTO.getMethodePaiement());
        paiement.setStatut(PaymentStatus.PENDING);
        paiement.setDatePaiement(Instant.now());

        paiement = paiementRepository.save(paiement);

        // Vérifier si le paiement couvre le prix total
        if (paiementDTO.getMontant().compareTo(commande.getPrixTotal()) >= 0) {
            paiement.setStatut(PaymentStatus.CONFIRMED);
            commande.setStatut(CommandStatus.DELIVERED);
        } else {
            paiement.setStatut(PaymentStatus.REJECTED);
        }

        paiementRepository.save(paiement);
        commandeRepository.save(commande);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}

