package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.CommandStatus;
import com.ucad.m2SIR.SenBook.customTypes.PaymentStatus;
import com.ucad.m2SIR.SenBook.dto.CommandeDTO;
import com.ucad.m2SIR.SenBook.dto.DetailsCommandeDTO;
import com.ucad.m2SIR.SenBook.dto.PaiementDTO;
import com.ucad.m2SIR.SenBook.model.*;
import com.ucad.m2SIR.SenBook.repository.*;
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
    public String creerCommande(Integer utilisateurId, List<DetailsCommandeDTO> details) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);
        if (utilisateur == null)
            return "Failed : Utilisateur introuvable";

        Commande commande = new Commande();
        commande.setUtilisateur(utilisateur);
        commande.setDateCommande(Instant.now());
        commande.setStatus(CommandStatus.PENDING);
        commande.setPrixTotal(BigDecimal.ZERO);

        commande = commandeRepository.save(commande);

        BigDecimal prixTotal = BigDecimal.ZERO;

        for (DetailsCommandeDTO detailDTO : details) {
            DetailsLivre detailsLivre = detailsLivreRepository.findById(detailDTO.getDetailsLivre().getIdLivre()).orElse(null);
            if (detailsLivre == null)
                return "Failed : Détail du livre introuvable";

            Inventaire inventaire = inventaireRepository.findByDetailLivre(detailsLivre).orElse(null);
            if (inventaire == null)
                return "Failed : Stock indisponible";

            if (inventaire.getQuantite() < detailDTO.getQuantite()) {
                return "Failed : Stock insuffisant pour le livre: " + detailsLivre.getLivre().getTitre();
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
        return "Success : Commande créé avec succés";
    }

    // Annule une commande et restaure les stocks.
    public String annulerCommande(Integer commandeId) {
        Commande commande = commandeRepository.findById(commandeId).orElse(null);

        if (commande == null)
            return "Failed : Commande introuvable";

        if (commande.getStatus() == CommandStatus.DELIVERED) {
            return "Failed : Impossible d'annuler une commande complétée";
        }

        List<DetailsCommande> details = detailsCommandeRepository.findByCommande(commande);
        for (DetailsCommande detail : details) {
            Inventaire inventaire = inventaireRepository.findByDetailLivre(detail.getDetailLivre()).orElse(null);
            if (inventaire == null)
                return "Failed : Stock introuvable";
            inventaire.setQuantite(inventaire.getQuantite() + detail.getQuantite());
            inventaireRepository.save(inventaire);
        }

        commande.setStatus(CommandStatus.CANCELLED);
        commandeRepository.save(commande);
        return "Success : La commande a été annulé";
    }

    // Récupère toutes les commandes d'un utilisateur
    public List<CommandeDTO> getCommandes(Integer utilisateurId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElse(null);

        if (utilisateur == null)
            return null;

        return commandeRepository.findByUtilisateur(utilisateur)
                .stream()
                .map(CommandeDTO::new)
                .toList();
    }

    // Associe un paiement à une commande.
    public String effectuerPaiement(PaiementDTO paiementDTO) {
        Commande commande = commandeRepository.findById(paiementDTO.getCommandeId()).orElse(null);
        if (commande == null)
            return "Failed : Commande introuvable";

        if (commande.getStatus() == CommandStatus.DELIVERED) {
            return "Failed : La commande a déjà été validé";
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
            commande.setStatus(CommandStatus.DELIVERED);
        } else {
            paiement.setStatut(PaymentStatus.REJECTED);
        }

        paiementRepository.save(paiement);
        commandeRepository.save(commande);

        return "Success : Paiement effectuer avec succés";
    }
}

