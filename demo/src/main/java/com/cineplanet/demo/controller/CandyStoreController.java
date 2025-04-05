package com.cineplanet.demo.controller;

import com.cineplanet.demo.dto.CandyStoreSummaryDto;
import com.cineplanet.demo.entity.CandyStore;
import com.cineplanet.demo.service.interfaces.CandyStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/candy-store-products")
public class CandyStoreController {

    private final CandyStoreService candyStoreService;

    @Autowired
    public CandyStoreController(CandyStoreService candyStoreService) {
        this.candyStoreService = candyStoreService;
    }

    @Operation(summary = "Obtener el listado de productos de la dulcería",
            description = "Este endpoint retorna un listado de todos los productos disponibles en la dulcería.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos de dulcería obtenido correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<CandyStoreSummaryDto>> getAllCandyStores() {
        List<CandyStoreSummaryDto> candyStores = candyStoreService.getAllCandyStores();
        return new ResponseEntity<>(candyStores, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un producto de dulcería por ID",
            description = "Este endpoint obtiene un producto de dulcería específico usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto de dulcería encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto de dulcería no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CandyStore> getCandyStoreById(@PathVariable Long id) {
        CandyStore candyStore = candyStoreService.getCandyStoreById(id);
        return candyStore != null ? new ResponseEntity<>(candyStore, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
