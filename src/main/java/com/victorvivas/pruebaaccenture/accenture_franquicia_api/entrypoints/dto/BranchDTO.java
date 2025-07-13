package com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto;

import java.util.List;

public class BranchDTO {
    private String name;
    private List<ProductDTO> products;

    public BranchDTO() {}

    public BranchDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
