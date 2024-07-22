package com.tech.challenge.domain.controller;


import com.tech.challenge.domain.dto.DoadorDTO;
import com.tech.challenge.domain.service.DoadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/doador")
@RequiredArgsConstructor
@Tag(name = "Doador EndPoint", description = "Endpoints referente aos métodos do Doador")
public class DoadorController {

    private final DoadorService doadorService;

    @GetMapping
    @Operation(
            summary = "Lista todos os doadores",
            description = "Esse endpoint retorna uma lista de todos os doadores."
    )
    public ResponseEntity<Collection<DoadorDTO>> ListarDoadores(){
        return ResponseEntity.ok(doadorService.ListarDoadores());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Lista o doador pelo ID",
            description = "Esse endpoint retorna o Doador pelo ID."
    )
    public ResponseEntity<DoadorDTO> ObterDoadorPeloID(@PathVariable int id){
        return ResponseEntity.ok(doadorService.ObterDoadorPeloID(id));
    }

    @PostMapping
    @Operation(
            summary = "Insere um novo Doador",
            description = "Esse endpoint insere um novo Doador"
    )
    public ResponseEntity<DoadorDTO> InserirDoador(@RequestBody DoadorDTO doador){
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(doadorService.InserirDoador(doador));
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualiza o Doador através do ID",
            description = "Esse endpoint atualiza um doador através do ID."
    )
    public ResponseEntity<DoadorDTO> AtualizaDoadorPeloId(
            @PathVariable int id,
            @RequestBody DoadorDTO NovoDoador){
        return ResponseEntity.ok(doadorService.AtualizaDoadorPeloId(id,NovoDoador));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remove um Doador através do ID",
            description = "Esse endpoint remove um doador através do ID."
    )
    public ResponseEntity<Void> RemoveDoador(@PathVariable int id){
        doadorService.RemoveDoador(id);
        return ResponseEntity.noContent().build();
    }
}
