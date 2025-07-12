package com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Representa una sucursal como documento embebido dentro de una franquicia.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchEntity {
    private String name;
    private List<ProductEntity> products;
}