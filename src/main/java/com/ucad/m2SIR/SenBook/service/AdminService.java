package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.customTypes.CommandStatus;
import com.ucad.m2SIR.SenBook.dto.*;
import com.ucad.m2SIR.SenBook.model.*;
import com.ucad.m2SIR.SenBook.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


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


    public ResponseEntity<Object> createAuteur(AuteurDTO auteurDTO){
        return auteurService.createAuteur(auteurDTO);
    }

    public ResponseEntity<Object> removeAuteur(int auteurId){
        return auteurService.removeAuteur(auteurId);
    }

    public ResponseEntity<Object> getAuteurs(){
        return auteurService.getAuteurs();
    }

    public ResponseEntity<Object> getAuteurById(int auteurId){
        return auteurService.getAuteurById(auteurId);
    }

    public ResponseEntity<Object> getAuteurByNom(String nom){
        return auteurService.getAuteurByNom(nom);
    }

    public ResponseEntity<Object> createLivre(LivreDTO livreDTO) {
        Livre livre = livreRepository.save(livreDTO.getLivre());
        LivresAuteur livresAuteur = new LivresAuteur();
        LivresAuteurId livresAuteurId = new LivresAuteurId();
        if (livre.getId() != null) {
            livresAuteurId.setIdLivre(livre.getId());
            for(Auteur auteur : livreDTO.getAuteurs()){
                livresAuteurId.setIdAuteur(auteur.getId());
                livresAuteur.setId(livresAuteurId);
                livresAuteur.setAuteur(auteur);
                livresAuteur.setLivre(livre);
                livresAuteurRepository.save(livresAuteur);
            }
            return new ResponseEntity<>("Le livre a été créé avec succés", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Le livre n'a pas pu être enregistré",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public ResponseEntity<Object> removeLivre(int livreId){
        if(livreRepository.existsById(livreId)) {
            livreRepository.deleteById(livreId);
            livresAuteurRepository.deleteAllByLivreId(livreId);
            return new ResponseEntity<>("Le livre a été supprimé avec succés",HttpStatus.OK);
        }
        return new ResponseEntity<>("Erreur lors de la suppression",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> createDetailLivre(DetailLivreDTO detailLivre){
        return detailLivreService.createDetailLivre(detailLivre);
    }

    public ResponseEntity<Object> removeDetailsLivre(int detailLivreId){
        return detailLivreService.removeDetailsLivre(detailLivreId);
    }

    public ResponseEntity<Object> getUtilisateurs(){
        return new ResponseEntity<>(utilisateurService.getUtilisateurs(),HttpStatus.OK);
    }

    public ResponseEntity<Object> deleteUtilisateurs(int id){
        return  new ResponseEntity<>(utilisateurService.deleteUtilisateur(id),HttpStatus.OK);
    }

    // Change le statut d'une commande
    @Transactional
    public ResponseEntity<Object> changerStatutCommande(Integer commandeId, CommandStatus nouveauStatut) {
        Commande commande = commandeRepository.findById(commandeId).orElse(null);
        if(commande == null)
            return new ResponseEntity<>("Commande introuvable",HttpStatus.NOT_FOUND);

        commande.setStatut(nouveauStatut);
        if(commandeRepository.save(commande).getId() !=null)
            return new ResponseEntity<>("Success",HttpStatus.OK);
        return new ResponseEntity<>("Erreur lors du changement de Status",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Récupère toutes les commandes d'un utilisateur
    public ResponseEntity<Object> getCommandesParUtilisateur(int idUser) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(idUser);
        return new ResponseEntity<>(commandeRepository.findByUtilisateur(utilisateur),HttpStatus.OK);
    }

    public ResponseEntity<Object> getAllCommandes(){
        return new ResponseEntity<>(detailsCommandeRepository.findAll()
                .stream()
                .map(DetailsCommandeDTO::new)
                .toList(),HttpStatus.OK);
    }

    public ResponseEntity<Object> getCommandeById(int commandId){
        Commande commande = commandeRepository.findById(commandId).orElse(null);
        return new ResponseEntity<>(detailsCommandeRepository.findAllByCommande(commande)
                .stream()
                .map(DetailsCommandeDTO::new)
                .toList(),HttpStatus.OK);
    }

    public ResponseEntity<Object> getPaiements(){
        return new ResponseEntity<>(paiementRepository
                .findAll()
                    .stream()
                    .map(PaiementDTO::new)
                    .toList()
                ,HttpStatus.OK);
    }

}
