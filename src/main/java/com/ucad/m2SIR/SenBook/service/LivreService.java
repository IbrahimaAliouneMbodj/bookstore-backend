package com.ucad.m2SIR.SenBook.service;

import com.ucad.m2SIR.SenBook.dto.AuteurDTO;
import com.ucad.m2SIR.SenBook.dto.DetailsLivreDTO;
import com.ucad.m2SIR.SenBook.dto.InventaireDTO;
import com.ucad.m2SIR.SenBook.dto.LivreDTO;
import com.ucad.m2SIR.SenBook.model.DetailsLivre;
import com.ucad.m2SIR.SenBook.model.Inventaire;
import com.ucad.m2SIR.SenBook.model.Livre;
import com.ucad.m2SIR.SenBook.repository.DetailsLivreRepository;
import com.ucad.m2SIR.SenBook.repository.InventaireRepository;
import com.ucad.m2SIR.SenBook.repository.LivreRepository;
import com.ucad.m2SIR.SenBook.repository.LivresAuteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivreService {

    private final LivreRepository livreRepository;
    private final DetailsLivreRepository detailsLivreRepository;
    private final LivresAuteurRepository livresAuteurRepository;
    private final InventaireRepository inventaireRepository;

    public LivreService(LivreRepository livreRepository, DetailsLivreRepository detailsLivreRepository, LivresAuteurRepository livresAuteurRepository, InventaireRepository inventaireRepository) {
        this.livreRepository = livreRepository;
        this.detailsLivreRepository = detailsLivreRepository;
        this.livresAuteurRepository = livresAuteurRepository;
        this.inventaireRepository = inventaireRepository;
    }

    public List<LivreDTO> getAllLivre() {
        return livreRepository.findAll()
                .stream()
                .map(this::getLivreDTOFromLivre)
                .toList();
    }
    public LivreDTO getLivre(int id) {
        Livre book = livreRepository.findById(id).orElse(null);
        if (book != null) {
            return getLivreDTOFromLivre(book);
        }
        return null;
    }
    public LivreDTO getLivreParISBN(String ISBN) {
        Livre book = livreRepository.findByIsbn(ISBN).orElse(null);
        if (book != null) {
            return getLivreDTOFromLivre(book);
        }
        return null;
    }
    public List<LivreDTO> getLivreByTitle(String title) {
        return livreRepository.findByTitreContaining(title)
                .stream()
                .map(this::getLivreDTOFromLivre)
                .toList();
    }
    public List<LivreDTO> getLivreByAuthorName(String authorName) {
        return livresAuteurRepository.findAllByAuteurNom(authorName)
                .stream()
                .map(this::getLivreDTOFromLivre)
                .toList();
    }
    public List<LivreDTO> getLivreByGenre(String genre) {
        return livreRepository.findByGenreContaining(genre)
                .stream()
                .map(this::getLivreDTOFromLivre)
                .toList();
    }

    private List<DetailsLivreDTO> getDetailsPerLivreId(int bookId)
    {
        return detailsLivreRepository.findAllByLivreId(bookId)
                .stream()
                .map(DetailsLivreDTO::new)
                .toList();
    }

    private List<AuteurDTO> getAuteursByLivreId(int bookId) {

        return livresAuteurRepository.findAllAuteurByLivreId(bookId)
                .stream()
                .map(AuteurDTO::new)
                .toList();
    }

    private LivreDTO getLivreDTOFromLivre(Livre book){
        List<AuteurDTO> auteurs = getAuteursByLivreId(book.getId());
        List<DetailsLivreDTO> details = getDetailsPerLivreId(book.getId());
        return new LivreDTO(book, auteurs, details);
    }

    public List<LivreDTO> getSimilarBooks(String text){
        return livreRepository.findTop4ByTitreContainingOrDescriptionContaining(text,text)
                .stream()
                .map(this::getLivreDTOFromLivre)
                .toList();
    }

    public List<LivreDTO> searchBooks(String text){
        return livreRepository.findAllByTitreContainingOrDescriptionContaining(text,text)
                .stream()
                .map(this::getLivreDTOFromLivre)
                .toList();
    }

    public InventaireDTO getStockByDetailsLivreId(int detailsLivreId) {
        DetailsLivre detailsLivre = detailsLivreRepository.findById(detailsLivreId).orElse(null);
        Inventaire inventaire = this.inventaireRepository.findByDetailLivreId(detailsLivreId).orElse(null);
        if (inventaire != null)
            return new InventaireDTO(inventaire);
        if(detailsLivre!=null)
            return new InventaireDTO(new DetailsLivreDTO(detailsLivre),0);
        return null;
    }
}
