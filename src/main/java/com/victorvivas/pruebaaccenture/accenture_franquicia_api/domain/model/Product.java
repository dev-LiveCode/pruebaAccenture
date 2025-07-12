package com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un producto ofertado por una sucursal.
 * Contiene el nombre del producto y su stock actual.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private int stock;
}