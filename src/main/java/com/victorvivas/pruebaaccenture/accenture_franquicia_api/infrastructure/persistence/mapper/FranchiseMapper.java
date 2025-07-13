package com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.mapper;

import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Branch;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Franchise;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Product;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity.BranchEntity;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity.FranchiseEntity;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapea entre el modelo de dominio y las entidades de persistencia.
 */
@Component
public class FranchiseMapper {

    public FranchiseEntity toEntity(Franchise franchise) {
        FranchiseEntity entity = new FranchiseEntity();
        entity.setId(franchise.getId());
        entity.setName(franchise.getName());

        if (franchise.getBranches() != null) {
            List<BranchEntity> branchEntities = franchise.getBranches().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList());
            entity.setBranches(branchEntities);
        } else {
            entity.setBranches(new ArrayList<>());
        }

        return entity;
    }

    public Franchise toDomain(FranchiseEntity entity) {
        Franchise franchise = new Franchise();
        franchise.setId(entity.getId());
        franchise.setName(entity.getName());

        if (entity.getBranches() != null) {
            List<Branch> branches = entity.getBranches().stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList());
            franchise.setBranches(branches);
        } else {
            franchise.setBranches(new ArrayList<>());
        }

        return franchise;
    }

    private BranchEntity toEntity(Branch branch) {
        BranchEntity entity = new BranchEntity();
        entity.setName(branch.getName());

        if (branch.getProducts() != null) {
            List<ProductEntity> products = branch.getProducts().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList());
            entity.setProducts(products);
        } else {
            entity.setProducts(new ArrayList<>());
        }

        return entity;
    }

    private Branch toDomain(BranchEntity entity) {
        Branch branch = new Branch();
        branch.setName(entity.getName());

        if (entity.getProducts() != null) {
            List<Product> products = entity.getProducts().stream()
                    .map(this::toDomain)
                    .collect(Collectors.toList());
            branch.setProducts(products);
        } else {
            branch.setProducts(new ArrayList<>());
        }

        return branch;
    }

    private ProductEntity toEntity(Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getStock());
    }

    private Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getStock());
    }

}
