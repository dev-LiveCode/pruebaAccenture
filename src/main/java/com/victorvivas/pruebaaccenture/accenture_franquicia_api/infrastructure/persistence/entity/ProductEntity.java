package com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un producto como documento embebido dentro de una sucursal.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    private String name;
    private int stock;
}