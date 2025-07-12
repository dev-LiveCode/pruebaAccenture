package com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Entidad para persistir en MongoDB.
 */
@Data
@NoArgsConstructor
@Document(collection = "franchises")
public class FranchiseEntity {

    @Id
    private String id;

    private String name;

    private List<BranchEntity> branches;
}