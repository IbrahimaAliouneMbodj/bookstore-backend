package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.dto.DetailLivreDTO;
import com.ucad.m2SIR.SenBook.model.DetailsLivre;
import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.repository.DetailsLivreRepository;
import com.ucad.m2SIR.SenBook.repository.LivreRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class DetailLivreService {
    private final DetailsLivreRepository detailsLivreRepository;
    private final LivreRepository livreRepository;

    public DetailLivreService(DetailsLivreRepository detailsLivreRepository, LivreRepository livreRepository) {
        this.detailsLivreRepository = detailsLivreRepository;
        this.livreRepository = livreRepository;
    }

    public ResponseEntity<Object> createDetailLivre(DetailLivreDTO detailLivre){
        Optional<Livre> livre = livreRepository.findById(detailLivre.getIdLivre());
        if(livre.isPresent()){
            DetailsLivre details = new DetailsLivre();
            details.setLivre(livre.get());
            details.setLangue(detailLivre.getLangue());
            details.setFormat(detailLivre.getFormat());
            details.setPrixUnitaire(detailLivre.getPrixUnitaire());
            if(detailsLivreRepository.save(details).getId() != null) {
                return new ResponseEntity<>("Detail cree avec succés",HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>("Erreur lors de la creation",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> removeDetailsLivre(int livreId){
        if(detailsLivreRepository.existsById(livreId)) {
            detailsLivreRepository.deleteById(livreId);
            return new ResponseEntity<>("Supprimé avec succés",HttpStatus.OK);
        }
        return new ResponseEntity<>("Erreur lors de la suppression",HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
