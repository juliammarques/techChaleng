package com.tech.challenge.domain.controller;

import com.tech.challenge.domain.dto.EntidadeBeneficiariaDTO;
import com.tech.challenge.domain.service.EntidadeBeneficiariaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/entidadebeneficiaria")
@RequiredArgsConstructor
@Tag(name = "EntidadeBeneficiaria EndPoint", description = "Endpoints referente aos métodos da EntidadeBeneficiaria.")
public class EntidadeBeneficiariaController {

    private final EntidadeBeneficiariaService entidadeBeneficiariaService;

    @GetMapping("/listarEntidade")
    @Operation(
            summary = "Lista todas as entidade beneficiárias",
            description = "Esse endpoint retorna uma lista de todas as entidades beneficiárias."
    )
    public ResponseEntity<Collection<EntidadeBeneficiariaDTO>> ListarEntidades(){
        return ResponseEntity.ok(entidadeBeneficiariaService.ListarEntidades());
    }

    @GetMapping("/obterEntidadeBeneficiaria/{id}")
    @Operation(
            summary = "Lista a entidade beneficiária pelo ID",
            description = "Esse endpoint retorna a entidade beneficiária pelo ID."
    )
    public ResponseEntity<EntidadeBeneficiariaDTO> ObterEntidadeBeneficiariaPeloID(@PathVariable int id){
        return ResponseEntity.ok(entidadeBeneficiariaService.ObterEntidadeBeneficiariaPeloID(id));
    }

    @PostMapping("/inserirEntidadeBeneficiaria")
    @Operation(
            summary = "Insere uma nova entidade beneficiária",
            description = "Esse endpoint insere uma nova entidade beneficiária"
    )
    public ResponseEntity<EntidadeBeneficiariaDTO> InserirEntidadeBeneficiaria(@Valid  @RequestBody EntidadeBeneficiariaDTO doador){
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(entidadeBeneficiariaService.InserirEntidadeBeneficiaria(doador));
    }
    @PutMapping("/atualizaEntidadeBeneficiariaPeloId/{id}")
    @Operation(
            summary = "Atualiza a entidade beneficiária através do ID",
            description = "Esse endpoint atualiza uma entidade beneficiária através do ID."
    )
    public ResponseEntity<EntidadeBeneficiariaDTO> AtualizaEntidadeBeneficiariaPeloId(
            @PathVariable int id,
            @Valid @RequestBody EntidadeBeneficiariaDTO NovoDoador){
        return ResponseEntity.ok(entidadeBeneficiariaService.AtualizaEntidadeBeneficiariaPeloId(id,NovoDoador));
    }

    @DeleteMapping("/removeEntidadeBeneficiaria/{id}")
    @Operation(
            summary = "Remove uma entidade beneficiária através do ID",
            description = "Esse endpoint remove uma entidade beneficiária através do ID."
    )
    public ResponseEntity<Void> RemoveEntidadeBeneficiaria(@PathVariable int id){
        entidadeBeneficiariaService.RemoveEntidadeBeneficiaria(id);
        return ResponseEntity.noContent().build();
    }
}
