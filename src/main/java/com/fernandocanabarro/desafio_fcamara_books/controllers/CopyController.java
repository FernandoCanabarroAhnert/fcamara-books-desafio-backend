package com.fernandocanabarro.desafio_fcamara_books.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyMinResponseDTO;
import com.fernandocanabarro.desafio_fcamara_books.dtos.CopyRequestDTO;
import com.fernandocanabarro.desafio_fcamara_books.openapi.CopyControllerOpenAPI;
import com.fernandocanabarro.desafio_fcamara_books.services.CopyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/copies")
@RequiredArgsConstructor
public class CopyController implements CopyControllerOpenAPI{

    private final CopyService copyService;

    @GetMapping
    public ResponseEntity<Page<CopyMinResponseDTO>> findAllAvailableCopies(Pageable pageable){
        Page<CopyMinResponseDTO> page = copyService.findAllCopiesAvailable(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CopyMinResponseDTO> insert(@RequestBody @Valid CopyRequestDTO dto){
        CopyMinResponseDTO obj = copyService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }
}
