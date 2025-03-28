package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.CommandStatus;
import com.ucad.m2SIR.SenBook.dto.*;
import com.ucad.m2SIR.SenBook.model.*;
import com.ucad.m2SIR.SenBook.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService {
    private final LivreRepository livreRepository;
    private final DetailLivreService detailLivreService;
    private final AuteurService auteurService;
    private final LivresAuteurRepository livresAuteurRepository;
    private final UtilisateurService utilisateurService;
    private final CommandeRepository commandeRepository;
    private final DetailsCommandeRepository detailsCommandeRepository;
    private final PaiementRepository paiementRepository;

    public AdminService(LivreRepository livreRepository,
                        DetailLivreService detailLivreService,
                        AuteurService auteurService,
                        LivresAuteurRepository livresAuteurRepository,
                        UtilisateurService utilisateurService,
                        CommandeRepository commandeRepository,
                        DetailsCommandeRepository detailsCommandeRepository,
                        PaiementRepository paiementRepository) {
        this.livreRepository = livreRepository;
        this.detailLivreService = detailLivreService;
        this.auteurService = auteurService;
        this.livresAuteurRepository = livresAuteurRepository;
        this.utilisateurService = utilisateurService;
        this.commandeRepository = commandeRepository;
        this.detailsCommandeRepository = detailsCommandeRepository;
        this.paiementRepository = paiementRepository;
    }


    public String createAuteur(Auteur auteur) {
        return auteurService.createAuteur(auteur);
    }

    public String updateAuteur(Auteur auteur) {
        return auteurService.updateAuteur(auteur);
    }

    public String removeAuteur(int auteurId) {
        return auteurService.removeAuteur(auteurId);
    }

    public List<Auteur> getAuteurs() {
        return auteurService.getAuteurs();
    }

    public Auteur getAuteurById(int auteurId) {
        return auteurService.getAuteurById(auteurId);
    }

    public List<Auteur> getAuteurByNom(String nom) {
        return auteurService.getAuteurByNom(nom);
    }

    public String createLivre(LivreDTO livreDTO) {
        Livre livre = livreRepository.save(livreDTO.getLivre());
        LivresAuteur livresAuteur = new LivresAuteur();
        LivresAuteurId livresAuteurId = new LivresAuteurId();
        if (livre.getId() != null) {
            livresAuteurId.setIdLivre(livre.getId());
            for (Integer auteurId : livreDTO.getAuteurIds()) {
                Auteur auteur = auteurService.getAuteurById(auteurId);
                livresAuteurId.setIdAuteur(auteur.getId());
                livresAuteur.setId(livresAuteurId);
                livresAuteur.setAuteur(auteur);
                livresAuteur.setLivre(livre);
                livresAuteurRepository.save(livresAuteur);
            }
            return "Success : Livre créé avec succés";
        }

        return "Failed : Une erreur est survenue lors de la creation du livre";
    }

    @Transactional
    public String removeLivre(int livreId) {
        if (livreRepository.existsById(livreId)) {
            livreRepository.deleteById(livreId);
            livresAuteurRepository.deleteAllByLivreId(livreId);
            return "Success : Livre supprimé avec succés";
        }
        return "Failed : Une erreur est survenue lors de la suppression";
    }

    public String createDetailLivre(DetailsLivreDTO detailLivre) {
        return detailLivreService.createDetailLivre(detailLivre);
    }

    public String removeDetailsLivre(int detailLivreId) {
        return detailLivreService.removeDetailsLivre(detailLivreId);
    }

    public List<UtilisateurDTO> getUtilisateurs() {
        return utilisateurService.getUtilisateurs();
    }

    public String deleteUtilisateurs(int id) {
        return utilisateurService.deleteUtilisateur(id);
    }

    // Change le status d'une commande
    @Transactional
    public String changerStatusCommande(Integer commandeId, CommandStatus nouveauStatus) {
        Commande commande = commandeRepository.findById(commandeId).orElse(null);

        if (commande == null)
            return "Failed : Commande introuvable";
        System.out.println(commande.getStatus());
        commande.setStatus(nouveauStatus);
        Commande response = commandeRepository.save(commande);
        System.out.println(response.getStatus());
        if (response.getId() != null)
            return "Success : Le status a été correctement changé";
        return "Failed : Une erreur est survenue lors du changement de status";
    }

    // Récupère toutes les commandes d'un utilisateur
    public List<CommandeDTO> getCommandesParUtilisateur(int idUser) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(idUser);
        return commandeRepository.findByUtilisateur(utilisateur)
                .stream()
                .map(CommandeDTO::new)
                .toList();
    }

    public List<CommandeDTO> getCommandes() {
        return commandeRepository.findAll()
                .stream()
                .map(CommandeDTO::new)
                .toList();
    }

    public List<DetailsCommandeDTO> getDetailsByCommandId(int commandId) {
        return detailsCommandeRepository.findAllByCommandeId(commandId)
                .stream()
                .map(DetailsCommandeDTO::new)
                .toList();
    }

    public CommandeDTO getCommandeById(int commandId) {
        Commande commande = commandeRepository.findById(commandId).orElse(null);
        return commande != null ? new CommandeDTO(commande) : null;
    }

    public List<PaiementDTO> getPaiements() {
        return paiementRepository
                .findAll()
                .stream()
                .map(PaiementDTO::new)
                .toList();
    }

}
