package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.CommandStatus;
import com.ucad.m2SIR.SenBook.dto.*;
import com.ucad.m2SIR.SenBook.model.*;
import com.ucad.m2SIR.SenBook.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    @Transactional
    public String createLivre(LivreDTO livreDTO) {
        if(livreDTO.getIsbn()==null || livreRepository.existsByIsbn(livreDTO.getIsbn()))
            return "Failed : L'ISBN spécifié est vide ou bien existe deja";
        if(livreDTO.getTitre().trim().isEmpty())
            return "Failed : Le titre ne doit pas etre vide";
        if(livreDTO.getEditeur().trim().isEmpty())
            return "Failed : Veuillez spécifier l'editeur du livre";
        if(livreDTO.getAuteurs().isEmpty())
            return "Failled : Veuillez specifier au moins un auteur";
        for(AuteurDTO auteurDTO : livreDTO.getAuteurs())
        {
            if(!auteurService.doesAuteurExiste(auteurDTO.getId())) {
                return "Failed : L'auteur " + auteurDTO.getId() + " n'existe pas veuillez l'enregistrer d'abord";
            }
        }
        if(livreDTO.getNombrePages() <=0){
            return "failed : Le nombre de page ne doit etre superieur a 0";
        }

        Livre livre = livreRepository.save(this.getLivre(livreDTO));
        if (livre.getId() != null) {
            LivresAuteur livresAuteur = new LivresAuteur();
            LivresAuteurId livresAuteurId = new LivresAuteurId();
            livresAuteurId.setIdLivre(livre.getId());
            for (AuteurDTO auteurDTO : livreDTO.getAuteurs()) {
                Auteur auteur = auteurService.getAuteurById(auteurDTO.getId());
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
    public String updateLivre(LivreDTO livreDTO) {
        Livre livre = livreRepository.findById(livreDTO.getId()).orElse(null);
        if (livre == null)
            return "Failed : Le livre que vous essayer de mettre a jour n'existe pas";

        if(livreDTO.getAuteurs().isEmpty())
            return "Failled : Veuillez specifier au moins un auteur";
        livresAuteurRepository.deleteAllByLivreId(livre.getId());
        LivresAuteur livresAuteur = new LivresAuteur();
        LivresAuteurId livresAuteurId = new LivresAuteurId();

        livresAuteurId.setIdLivre(livre.getId());
        for (AuteurDTO auteurDTO : livreDTO.getAuteurs()) {
            Auteur auteur = auteurService.getAuteurById(auteurDTO.getId());
            livresAuteurId.setIdAuteur(auteur.getId());
            livresAuteur.setId(livresAuteurId);
            livresAuteur.setAuteur(auteur);
            livresAuteur.setLivre(livre);
            livresAuteurRepository.save(livresAuteur);
        }
        if(!livreDTO.getTitre().trim().isEmpty())
            livre.setTitre(livreDTO.getTitre());

        if(!livreDTO.getEditeur().trim().isEmpty())
            livre.setEditeur(livreDTO.getEditeur());
        if(!livreDTO.getIsbn().equals(livre.getIsbn()) && !livreRepository.existsByIsbn(livreDTO.getIsbn()))
            livre.setIsbn(livreDTO.getIsbn());
        livre.setDatePublication(livreDTO.getDatePublication());
        livre.setGenre(livreDTO.getGenre());
        livre.setImage(livreDTO.getImage());
        if( livreDTO.getNombrePages() != null && livreDTO.getNombrePages() > 0 && livreDTO.getNombrePages() != livre.getNombrePages())
            livre.setNombrePages(livreDTO.getNombrePages());
        livre.setDescription(livreDTO.getDescription());
        livre.setCreeLe(Instant.now());
        return livreRepository.save(livre).getId() != null
                ? "Success : Le livre a été mis à jour"
                : "Failed : Une erreur s'est produite lors de la nodification des infos du livre";
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

    public String updateDetailLivre(DetailsLivreDTO detailLivre) {
        return detailLivreService.updateDetailsLivre(detailLivre);
    }

    public String removeDetailsLivre(int detailLivreId) {
        return detailLivreService.removeDetailsLivre(detailLivreId);
    }

    public List<UtilisateurDTO> getUtilisateurs() {
        return utilisateurService.getUtilisateurs();
    }

    public List<UtilisateurDTO> rechercherUtilisateur(String text) {
        return utilisateurService.getUtilisateurByText(text);
    }

    public String updateUser(Utilisateur user) {
        return utilisateurService.updateUtilisateur(user);
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
        commande.setStatus(nouveauStatus);
        Commande response = commandeRepository.save(commande);
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

    public Integer getUserCount() {
        return utilisateurService.getUserCount();
    }

    public Integer getOrderCount() {
        return commandeRepository.getOrderCount();
    }

    public List<CommandeDTO> getRecentOrders(){
        return commandeRepository.findTop5ByOrderByDateCommande()
                .stream()
                .map(CommandeDTO::new)
                .toList();
    }

    public List<UtilisateurDTO> getRecentUsers(){
        return commandeRepository.findMostRecentUsers()
                .stream()
                .map(UtilisateurDTO::new)
                .toList();
    }

    private Livre getLivre(LivreDTO livreDTO) {
        Livre livre = new Livre();
        livre.setTitre(livreDTO.getTitre());
        livre.setEditeur(livreDTO.getEditeur());
        livre.setIsbn(livreDTO.getIsbn());
        livre.setDatePublication(livreDTO.getDatePublication());
        livre.setGenre(livreDTO.getGenre());
        livre.setImage(livreDTO.getImage());
        livre.setNombrePages(livreDTO.getNombrePages());
        livre.setDescription(livreDTO.getDescription());
        livre.setCreeLe(Instant.now());
        return livre;
    }
}
