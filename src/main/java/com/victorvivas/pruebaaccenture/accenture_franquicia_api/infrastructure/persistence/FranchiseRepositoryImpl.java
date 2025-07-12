package com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence;

import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Franchise;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.repository.FranchiseRepository;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.mapper.FranchiseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del repositorio del dominio utilizando MongoDB reactivo.
 */
@Component
public class FranchiseRepositoryImpl implements FranchiseRepository {

    private final ReactiveMongoFranchiseRepository reactiveRepo;
    private final FranchiseMapper mapper;

    @Autowired
    public FranchiseRepositoryImpl(ReactiveMongoFranchiseRepository reactiveRepo, FranchiseMapper mapper) {
        this.reactiveRepo = reactiveRepo;
        this.mapper = mapper;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return reactiveRepo
                .save(mapper.toEntity(franchise))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return reactiveRepo
                .findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return reactiveRepo
                .findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return reactiveRepo.deleteById(id);
    }
}
