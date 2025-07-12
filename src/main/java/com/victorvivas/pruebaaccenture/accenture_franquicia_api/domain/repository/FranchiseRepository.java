package com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.repository;

import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Contrato para persistencia y consulta de franquicias.
 * Este repositorio define las operaciones necesarias sobre el dominio.
 */
public interface FranchiseRepository {

    Mono<Franchise> save(Franchise franchise);

    Mono<Franchise> findById(String id);

    Flux<Franchise> findAll();

    Mono<Void> deleteById(String id);
}