package com.tech.challenge.domain.controller;


import com.tech.challenge.domain.dto.ItensDisponiveisDoacoesDTO;
import com.tech.challenge.domain.service.ItensDisponiveisDoacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itensDisponiveisDoacao")
@RequiredArgsConstructor
@Tag(name = "itensDisponiveisDoacao EndPoint", description = "Endpoints referente aos métodos dos ItensDisponiveisDoacao.")
public class ItensDisponiveisDoacaoController {

    private final ItensDisponiveisDoacaoService itensDisponiveisDoacaoService;

    @GetMapping("/listagemItensDisponiveisDoacao")
    @Operation(
            summary = "Lista todos os itens disponiveis para doação",
            description = "Esse endpoint retorna uma lista de todos os itens disponiveis para doação."
    )
    public ResponseEntity<Page<ItensDisponiveisDoacoesDTO>> ListarItensDisponiveisDoacao(@PageableDefault(size=50,page=0,sort="id") Pageable pageable){
        System.out.println(pageable);
        Page<ItensDisponiveisDoacoesDTO> pageItensDisponiveis = itensDisponiveisDoacaoService.ListarItensDisponiveisDoacao(pageable);
        return ResponseEntity.ok(pageItensDisponiveis);
    }

    @GetMapping("/obterItensDisponiveisDoacaoPeloId/{id}")
    @Operation(
            summary = "Lista o item disponiveis para doação pelo ID",
            description = "Esse endpoint retorna o item disponiveis para doação pelo ID."
    )
    public ResponseEntity<ItensDisponiveisDoacoesDTO> ObterItensDisponiveisDoacaoPeloID(@PathVariable int id){
        return ResponseEntity.ok(itensDisponiveisDoacaoService.ObterItensDisponiveisDoacaoPeloID(id));
    }

    @PostMapping("/inserirItensDisponiveisDoacao")
    @Operation(
            summary = "Insere um novo item disponivel para doação",
            description = "Esse endpoint insere um novo item disponivel para doação"
    )
    public ResponseEntity<ItensDisponiveisDoacoesDTO> InserirItensDisponiveisDoacao(@Valid  @RequestBody ItensDisponiveisDoacoesDTO itemDisponivelDoacao){
        return ResponseEntity.status(HttpStatusCode.valueOf(HttpStatus.CREATED.value())).body(itensDisponiveisDoacaoService.InserirItensDisponiveisDoacao(itemDisponivelDoacao));
    }
    @PutMapping("/atualizaItensDisponiveisDoacaoPeloId/{id}")
    @Operation(
            summary = "Atualiza o item disponível para doação através do ID",
            description = "Esse endpoint atualiza o item disponível para doação através do ID."
    )
    public ResponseEntity<ItensDisponiveisDoacoesDTO> AtualizaItensDisponiveisDoacaoPeloId(
            @PathVariable int id,
            @Valid @RequestBody ItensDisponiveisDoacoesDTO NovoItemDisponivelDoacao){
        return ResponseEntity.ok(itensDisponiveisDoacaoService.AtualizaItensDisponiveisDoacaoPeloId(id,NovoItemDisponivelDoacao));
    }

    @DeleteMapping("/removeItensDisponiveisDoacao/{id}")
    @Operation(
            summary = "Remove um item disponível para doação através do ID",
            description = "Esse endpoint remove um item disponível para doação através do ID."
    )
    public ResponseEntity<Void> RemoveItensDisponiveisDoacao(@PathVariable int id){
        itensDisponiveisDoacaoService.RemoveItensDisponiveisDoacao(id);
        return ResponseEntity.noContent().build();
    }
}
