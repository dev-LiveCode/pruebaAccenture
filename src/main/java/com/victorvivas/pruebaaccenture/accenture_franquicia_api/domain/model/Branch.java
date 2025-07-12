package com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Representa una sucursal dentro de una franquicia.
 * Contiene un nombre y un listado de productos.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Branch {
    private String name;
    private List<Product> products;
}