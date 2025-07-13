package com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto;

public class ProductDTO {
    private String name;
    private int stock;

    public ProductDTO() {}

    public ProductDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
