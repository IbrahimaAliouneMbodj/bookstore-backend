package com.ucad.m2SIR.SenBook.controller;

import com.ucad.m2SIR.SenBook.model.Utilisateur;
import com.ucad.m2SIR.SenBook.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAuthority('CLIENT')")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<Object> getSelfInfos() {
        Utilisateur user = clientService.getCurrentUser();
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>("Failed : Une erreur s'est produite lors de la recuperation", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/{password}")
    public ResponseEntity<Object> changePassword(@PathVariable String password) {
        String response = clientService.changerMotDePasse(password);
        return new ResponseEntity<>(
                response,
                response.startsWith("Success")
                        ? HttpStatus.CREATED
                        : HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
