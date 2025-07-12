package com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.controller;

import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Franchise;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.repository.FranchiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * Controlador para exponer operaciones relacionadas con franquicias.
 */
@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final FranchiseRepository franchiseRepository;

    @Autowired
    public FranchiseController(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    /**
     * Crea una nueva franquicia.
     * @param franchise Objeto recibido desde el cliente
     * @return La franquicia guardada, con su ID generado
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> createFranchise(@RequestBody Franchise franchise) {
        return franchiseRepository.save(franchise);
    }
}