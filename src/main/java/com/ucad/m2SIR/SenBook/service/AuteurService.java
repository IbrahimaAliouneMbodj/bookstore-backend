package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.dto.AuteurDTO;
import com.ucad.m2SIR.SenBook.model.Auteur;
import com.ucad.m2SIR.SenBook.model.LivresAuteur;
import com.ucad.m2SIR.SenBook.repository.AuteurRepository;
import com.ucad.m2SIR.SenBook.repository.LivresAuteurRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuteurService {

    private final AuteurRepository auteurRepository;
    private final LivresAuteurRepository livresAuteurRepository;


    public AuteurService(AuteurRepository auteurRepository, LivresAuteurRepository livresAuteurRepository) {
        this.auteurRepository = auteurRepository;
        this.livresAuteurRepository = livresAuteurRepository;
    }

    public ResponseEntity<Object> createAuteur(AuteurDTO auteurDTO){
        Auteur auteur = auteurRepository.save(auteurDTO.getAuteur());
        if(auteur.getId() != null)
            return new ResponseEntity<>(HttpStatus.CREATED);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> removeAuteur(int auteurId){
        auteurRepository.deleteById(auteurId);
        return new ResponseEntity<>(auteurRepository.existsById(auteurId) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

    public ResponseEntity<Object> updateAuteur(AuteurDTO auteurDTO){
        return new ResponseEntity<>(auteurRepository.save(auteurDTO.getAuteur()),HttpStatus.OK);
    }

    public ResponseEntity<Object> getAuteurs(){
        return new ResponseEntity<>(auteurRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<Object> getAuteurById(int id){
        return new ResponseEntity<>(auteurRepository.findById(id).orElse(null),HttpStatus.OK);
    }

    public ResponseEntity<Object> getAuteurByNom(String nom){
       return new ResponseEntity<>(auteurRepository.findByNomContaining(nom), HttpStatus.OK);
    }

    public ResponseEntity<Object> getAuteursByPays(String pays){
        return new ResponseEntity<>(auteurRepository.findAllByPays(pays), HttpStatus.OK);
    }

    public List<Auteur> getAuteursByBookId(int bookId) {
        List<LivresAuteur> livresAuteurs = livresAuteurRepository.findAllByLivre_Id(bookId);
        List <Auteur> auteurs = new ArrayList<>();
        for (LivresAuteur la : livresAuteurs) {
            auteurs.add(la.getAuteur());
        }
        return auteurs;
    }
}
