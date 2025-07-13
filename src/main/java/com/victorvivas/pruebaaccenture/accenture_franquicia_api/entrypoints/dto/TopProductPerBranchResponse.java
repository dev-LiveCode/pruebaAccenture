package com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto;

import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Product;

/**
 * DTO que representa la respuesta con el producto de mayor stock por sucursal.
 */
public class TopProductPerBranchResponse {

    private String branchName;
    private Product product;

    public TopProductPerBranchResponse(String branchName, Product product) {
        this.branchName = branchName;
        this.product = product;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}