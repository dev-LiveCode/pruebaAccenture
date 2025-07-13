package com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que encapsula el nuevo nombre para actualizaciones.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameUpdateDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
