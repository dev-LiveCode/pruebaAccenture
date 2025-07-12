package com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence;

import com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity.FranchiseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de MongoDB con soporte reactivo.
 */
@Repository
public interface ReactiveMongoFranchiseRepository extends ReactiveMongoRepository<FranchiseEntity, String> {
}