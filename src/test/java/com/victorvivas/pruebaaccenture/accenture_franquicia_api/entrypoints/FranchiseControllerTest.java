package com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Branch;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Franchise;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.domain.model.Product;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto.BranchDTO;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto.FranchiseDTO;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto.NameUpdateDTO;
import com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto.ProductDTO;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
class FranchiseControllerTest {

     @Autowired
    private WebTestClient webTestClient;

    private static String franchiseId;
    private static String branchId;
    private static String productId;

    @Test
    @Order(1)
    @DisplayName("Crear franquicia")
    public void testCreateFranchise() {
        FranchiseDTO franchiseDTO = new FranchiseDTO();
        franchiseDTO.setName("Nueva franquicia");

        webTestClient.post()
            .uri("/api/franchises")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(franchiseDTO), FranchiseDTO.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Franchise.class)
            .value(franchise -> franchiseId = franchise.getId());
    }

    @Test
    @Order(2)
    @DisplayName("agregar sucursal a franquicia")
    public void testAddBranchToFranchise() {
        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setName("Nuena sucursal");

        webTestClient.post()
            .uri("/api/franchises/{franchiseId}/branches", franchiseId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(branchDTO), BranchDTO.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Branch.class)
            .value(branch -> branchId = branch.getId());
    }

    @Test
    @Order(3)
    @DisplayName("agregar producto a la sucursal")
    public void testAddProductToBranch() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Nuevo producto");
        productDTO.setStock(10);

        webTestClient.post()
            .uri("/api/franchises/{franchiseId}/branches/{branchId}/products", franchiseId, branchId)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(List.of(productDTO))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Branch.class)
            .value(branch -> productId = branch.getProducts().get(0).getId());
    }

    @Test
    @Order(4)
    @DisplayName("Traer productos de una sucursal")
    public void testGetProductsByBranch() {
        webTestClient.get()
            .uri("/api/franchises/{franchiseId}/branches/{branchId}/products", franchiseId, branchId)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Product.class)
            .hasSize(1);
    }

    @Test
    @Order(5)
    @DisplayName("Actualizar nombre de un producto")
    public void testUpdateProductName() {
        NameUpdateDTO updateDTO = new NameUpdateDTO();
        updateDTO.setName("Prodcuto actualizado");

        webTestClient.patch()
            .uri("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}",
                franchiseId, branchId, productId)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(updateDTO), NameUpdateDTO.class)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Product.class)
            .value(product -> Assertions.assertEquals("Prodcuto actualizado", product.getName()));
    }

    @Test
    @Order(6)
    @DisplayName("Eliminar producto de la sucursal")
    public void testDeleteProductFromBranch() {
        webTestClient.delete()
            .uri("/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}",
                franchiseId, branchId, productId)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @Order(7)
    @DisplayName("Actualizar nombre de una franquicia")
    void testUpdateFranchiseName() {
        NameUpdateDTO updateDTO = new NameUpdateDTO();
        updateDTO.setName("Franquicia renombrada");
        webTestClient.patch()
                .uri("/api/franchises/" + franchiseId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateDTO), NameUpdateDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Franquicia renombrada");
    }

    @Test
    @Order(8)
    @DisplayName("Actualizar nombre de una sucursal")
    void testUpdateBranchName() {
        NameUpdateDTO updateDTO = new NameUpdateDTO();
        updateDTO.setName("Sucursal renombrada");

        webTestClient.patch()
                .uri("/api/franchises/" + franchiseId + "/branches/" + branchId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateDTO), NameUpdateDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Sucursal renombrada");
    }

    @Test
    @Order(9)
    @DisplayName("Consultar producto con más stock por sucursal")
    void testGetTopProductPerBranch() {
        webTestClient.get()
                .uri("/api/franchises/" + franchiseId + "/branches/top-products")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray();
    }
    

    @Test
    @Order(10)
    @DisplayName("Limpiar base de datos")
    public void testDeleteFranchise() {
        webTestClient.delete()
            .uri("/api/franchises/delete-all")
            .exchange()
            .expectStatus().isNoContent();
    }
}