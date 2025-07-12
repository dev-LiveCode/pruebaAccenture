package com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Representa una franquicia que agrupa múltiples sucursales.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Franchise {
    private String id;
    private String name;
    private List<Branch> branches;
}