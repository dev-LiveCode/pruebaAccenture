package com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.mapper;

import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Branch;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Franchise;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Product;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity.BranchEntity;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity.FranchiseEntity;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

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

        List<BranchEntity> branchEntities = franchise.getBranches().stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        entity.setBranches(branchEntities);
        return entity;
    }

    public Franchise toDomain(FranchiseEntity entity) {
        Franchise franchise = new Franchise();
        franchise.setId(entity.getId());
        franchise.setName(entity.getName());

        List<Branch> branches = entity.getBranches().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());

        franchise.setBranches(branches);
        return franchise;
    }

    private BranchEntity toEntity(Branch branch) {
        BranchEntity entity = new BranchEntity();
        entity.setName(branch.getName());

        List<ProductEntity> products = branch.getProducts().stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        entity.setProducts(products);
        return entity;
    }

    private Branch toDomain(BranchEntity entity) {
        Branch branch = new Branch();
        branch.setName(entity.getName());

        List<Product> products = entity.getProducts().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());

        branch.setProducts(products);
        return branch;
    }

    private ProductEntity toEntity(Product product) {
        return new ProductEntity(product.getName(), product.getStock());
    }

    private Product toDomain(ProductEntity entity) {
        return new Product(entity.getName(), entity.getStock());
    }
}
