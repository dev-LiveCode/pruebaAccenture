package com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.controller;

import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Franchise;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Product;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Branch;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.repository.FranchiseRepository;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto.BranchDTO;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto.FranchiseDTO;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto.NameUpdateDTO;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto.ProductDTO;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto.TopProductPerBranchResponse;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.infrastructure.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador para exponer operaciones relacionadas con franquicias.
 */
@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    private final FranchiseRepository franchiseRepository;

    @Autowired
    public FranchiseController(FranchiseRepository franchiseRepository) {
        this.franchiseRepository = franchiseRepository;
    }

    /**
     * Crea una nueva franquicia con su información básica, incluyendo opcionalmente
     * las sucursales y productos asociados.
     *
     * @param franchiseDTO Objeto recibido en el cuerpo de la petición que contiene
     *                     el nombre de la franquicia y su estructura opcional.
     * @return La franquicia creada con su identificador generado y sus relaciones
     *         correspondientes.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> createFranchise(@RequestBody FranchiseDTO franchiseDTO) {
        Franchise franchise = new Franchise();
        franchise.setId(UUID.randomUUID().toString());
        franchise.setName(franchiseDTO.getName());

        if (franchiseDTO.getBranches() != null) {
            List<Branch> branches = franchiseDTO.getBranches().stream().map(branchDTO -> {
                Branch branch = new Branch();
                branch.setId(UUID.randomUUID().toString());
                branch.setName(branchDTO.getName());

                if (branchDTO.getProducts() != null) {
                    List<Product> products = branchDTO.getProducts().stream().map(productDTO -> {
                        Product product = new Product();
                        product.setId(UUID.randomUUID().toString());
                        product.setName(productDTO.getName());
                        product.setStock(productDTO.getStock());
                        return product;
                    }).toList();
                    branch.setProducts(products);
                } else {
                    branch.setProducts(new ArrayList<>());
                }

                return branch;
            }).toList();

            franchise.setBranches(branches);
        } else {
            franchise.setBranches(new ArrayList<>());
        }

        return franchiseRepository.save(franchise);
    }

    /**
     * Agrega una nueva sucursal a una franquicia existente.
     *
     * @param franchiseId Identificador único de la franquicia a la que se añadirá
     *                    la sucursal.
     * @param branchDTO   Objeto que representa la información de la sucursal a
     *                    crear, incluyendo opcionalmente sus productos.
     * @return La sucursal recién añadida con su identificador generado.
     * @throws ResourceNotFoundException Si la franquicia especificada no existe.
     */
    @PostMapping("/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Branch> addBranchToFranchise(
            @PathVariable String franchiseId,
            @RequestBody BranchDTO branchDTO) {

        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + franchiseId)))
                .flatMap(franchise -> {
                    Branch newBranch = new Branch();
                    newBranch.setId(UUID.randomUUID().toString());
                    newBranch.setName(branchDTO.getName());

                    if (branchDTO.getProducts() != null) {
                        List<Product> products = branchDTO.getProducts().stream().map(productDTO -> {
                            Product product = new Product();
                            product.setId(UUID.randomUUID().toString());
                            product.setName(productDTO.getName());
                            product.setStock(productDTO.getStock());
                            return product;
                        }).toList();
                        newBranch.setProducts(products);
                    } else {
                        newBranch.setProducts(new ArrayList<>());
                    }

                    franchise.getBranches().add(newBranch);
                    return franchiseRepository.save(franchise)
                            .thenReturn(newBranch);
                });
    }

    /**
     * Agrega uno o varios productos a una sucursal específica dentro de una
     * franquicia.
     *
     * @param franchiseId Identificador único de la franquicia.
     * @param branchId    Identificador único de la sucursal donde se agregarán los
     *                    productos.
     * @param productDTOs Lista de objetos que representan los productos a añadir.
     * @return La sucursal actualizada con la lista completa de productos.
     * @throws ResourceNotFoundException Si la franquicia o sucursal no existen.
     */
    @PostMapping("/{franchiseId}/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Branch> addProductsToBranch(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @RequestBody List<ProductDTO> productDTOs) {

        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + franchiseId)))
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

                    List<Product> newProducts = productDTOs.stream().map(productDTO -> {
                        Product product = new Product();
                        product.setId(UUID.randomUUID().toString());
                        product.setName(productDTO.getName());
                        product.setStock(productDTO.getStock());
                        return product;
                    }).toList();

                    branch.getProducts().addAll(newProducts);
                    return franchiseRepository.save(franchise)
                            .thenReturn(branch);
                });
    }

    /**
     * Elimina un producto específico de una sucursal determinada dentro de una
     * franquicia.
     *
     * @param franchiseId Identificador único de la franquicia.
     * @param branchId    Identificador único de la sucursal.
     * @param productId   Identificador único del producto que se desea eliminar.
     * @return Respuesta vacía con estado 204 si la eliminación fue exitosa.
     * @throws ResourceNotFoundException Si la franquicia, sucursal o producto no
     *                                   existen.
     */
    @DeleteMapping("/{franchiseId}/branches/{branchId}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProductFromBranch(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId) {

        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + franchiseId)))
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

                    boolean removed = branch.getProducts().removeIf(p -> p.getId().equals(productId));

                    if (!removed) {
                        return Mono.error(new ResourceNotFoundException("Product not found with id: " + productId));
                    }

                    return franchiseRepository.save(franchise).then();
                });
    }

    /**
     * Obtiene todas las franquicias registradas en el sistema junto con su
     * estructura completa: sucursales y productos.
     *
     * @return Un flujo reactivo (Flux) con todas las franquicias y sus detalles
     *         completos.
     */
    @GetMapping
    public Flux<Franchise> getAllFranchises() {
        return franchiseRepository.findAll();
    }

    /**
     * Obtiene todas las sucursales de una franquicia específica.
     *
     * @param franchiseId Identificador único de la franquicia.
     * @return Lista de sucursales que pertenecen a la franquicia indicada.
     * @throws ResourceNotFoundException Si la franquicia especificada no existe.
     */
    @GetMapping("/{franchiseId}/branches")
    public Mono<List<Branch>> getBranchesByFranchise(@PathVariable String franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(Franchise::getBranches);
    }

    /**
     * Obtiene todos los productos de una sucursal específica dentro de una
     * franquicia.
     *
     * @param franchiseId Identificador único de la franquicia.
     * @param branchId    Identificador único de la sucursal.
     * @return Lista de productos que pertenecen a la sucursal especificada.
     * @throws ResourceNotFoundException Si la franquicia o sucursal no existen.
     */
    @GetMapping("/{franchiseId}/branches/{branchId}/products")
    public Mono<List<Product>> getProductsByBranch(
            @PathVariable String franchiseId,
            @PathVariable String branchId) {

        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(franchise -> franchise.getBranches().stream()
                        .filter(b -> b.getId().equals(branchId))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId))
                        .getProducts());
    }

    /**
     * Obtiene el producto con mayor stock de cada sucursal perteneciente a una
     * franquicia específica.
     * <p>
     * Este endpoint permite consultar, para una franquicia en particular, el
     * producto que tiene mayor
     * cantidad de stock en cada una de sus sucursales. La respuesta incluye el
     * nombre de la sucursal y
     * la información del producto con mayor stock en dicha sucursal.
     *
     * @param franchiseId Identificador único de la franquicia.
     * @return Una lista que contiene el nombre de la sucursal y el producto con
     *         mayor stock en cada una.
     * @throws ResourceNotFoundException Si la franquicia especificada no existe.
     */
    @GetMapping("/{franchiseId}/branches/top-products")
    public Mono<List<TopProductPerBranchResponse>> getTopProductPerBranch(
            @PathVariable String franchiseId) {

        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + franchiseId)))
                .map(franchise -> franchise.getBranches().stream()
                        .map(branch -> {
                            Product topProduct = branch.getProducts().stream()
                                    .max(Comparator.comparingInt(Product::getStock))
                                    .orElse(null);

                            return new TopProductPerBranchResponse(branch.getName(), topProduct);
                        })
                        .filter(response -> response.getProduct() != null)
                        .toList());
    }

    /**
     * Actualiza el nombre de una franquicia específica.
     *
     * @param franchiseId   Identificador único de la franquicia.
     * @param nameUpdateDTO Objeto que contiene el nuevo nombre.
     * @return La franquicia actualizada con el nuevo nombre.
     * @throws ResourceNotFoundException Si la franquicia no existe.
     */
    @PatchMapping("/{franchiseId}")
    public Mono<Franchise> updateFranchiseName(
            @PathVariable String franchiseId,
            @RequestBody NameUpdateDTO nameUpdateDTO) {

        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + franchiseId)))
                .flatMap(franchise -> {
                    franchise.setName(nameUpdateDTO.getName());
                    return franchiseRepository.save(franchise);
                });
    }

    /**
     * Actualiza el nombre de una sucursal específica dentro de una franquicia.
     *
     * @param franchiseId   Identificador único de la franquicia.
     * @param branchId      Identificador único de la sucursal.
     * @param nameUpdateDTO Objeto que contiene el nuevo nombre.
     * @return La sucursal actualizada con el nuevo nombre.
     * @throws ResourceNotFoundException Si la franquicia o la sucursal no existen.
     */
    @PatchMapping("/{franchiseId}/branches/{branchId}")
    public Mono<Branch> updateBranchName(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @RequestBody NameUpdateDTO nameUpdateDTO) {

        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + franchiseId)))
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

                    branch.setName(nameUpdateDTO.getName());
                    return franchiseRepository.save(franchise).thenReturn(branch);
                });
    }

    /**
     * Actualiza el nombre de un producto específico dentro de una sucursal y
     * franquicia dadas.
     *
     * @param franchiseId   Identificador único de la franquicia.
     * @param branchId      Identificador único de la sucursal.
     * @param productId     Identificador único del producto.
     * @param nameUpdateDTO Objeto que contiene el nuevo nombre.
     * @return El producto actualizado con el nuevo nombre.
     * @throws ResourceNotFoundException Si la franquicia, sucursal o producto no
     *                                   existen.
     */
    @PatchMapping("/{franchiseId}/branches/{branchId}/products/{productId}")
    public Mono<Product> updateProductName(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId,
            @RequestBody NameUpdateDTO nameUpdateDTO) {

        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Franchise not found with id: " + franchiseId)))
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches().stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));

                    Product product = branch.getProducts().stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElseThrow(
                                    () -> new ResourceNotFoundException("Product not found with id: " + productId));

                    product.setName(nameUpdateDTO.getName());
                    return franchiseRepository.save(franchise).thenReturn(product);
                });
    }

    /**
     * Carga masiva de franquicias con su estructura completa (sucursales y
     * productos).
     * Este endpoint permite registrar múltiples franquicias en una sola petición,
     * incluyendo opcionalmente sus sucursales y los productos de cada sucursal.
     *
     * @param franchiseList Lista de franquicias con su estructura jerárquica.
     * @return Lista de franquicias creadas con sus identificadores generados.
     */
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Franchise> bulkCreateFranchises(@RequestBody List<FranchiseDTO> franchiseList) {
        List<Franchise> franchisesToSave = franchiseList.stream().map(dto -> {
            Franchise franchise = new Franchise();
            franchise.setId(UUID.randomUUID().toString());
            franchise.setName(dto.getName());

            List<Branch> branches = new ArrayList<>();
            if (dto.getBranches() != null) {
                branches = dto.getBranches().stream().map(branchDTO -> {
                    Branch branch = new Branch();
                    branch.setId(UUID.randomUUID().toString());
                    branch.setName(branchDTO.getName());

                    List<Product> products = new ArrayList<>();
                    if (branchDTO.getProducts() != null) {
                        products = branchDTO.getProducts().stream().map(productDTO -> {
                            Product product = new Product();
                            product.setId(UUID.randomUUID().toString());
                            product.setName(productDTO.getName());
                            product.setStock(productDTO.getStock());
                            return product;
                        }).toList();
                    }

                    branch.setProducts(products);
                    return branch;
                }).toList();
            }

            franchise.setBranches(branches);
            return franchise;
        }).toList();

        return franchiseRepository.saveAll(Flux.fromIterable(franchisesToSave));
    }

}