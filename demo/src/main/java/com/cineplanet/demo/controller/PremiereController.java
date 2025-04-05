package com.cineplanet.demo.controller;

import com.cineplanet.demo.dto.PremiereSummaryDto;
import com.cineplanet.demo.entity.Premiere;
import com.cineplanet.demo.service.interfaces.PremiereService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/premieres")
public class PremiereController {

    private final PremiereService premiereService;

    @Autowired
    public PremiereController(PremiereService premiereService) {
        this.premiereService = premiereService;
    }

    @GetMapping
    @ApiOperation(value = "Obtener todos los Premieres", notes = "Este endpoint devuelve una lista de todos los Premieres registrados.")
    public ResponseEntity<List<PremiereSummaryDto>> getAllPremieres() {
        List<PremiereSummaryDto> premieres = premiereService.getAllPremieres();
        return new ResponseEntity<>(premieres, HttpStatus.OK);
    }
}
