package com.tech.challenge.domain.controller;


import com.google.gson.Gson;
import com.tech.challenge.domain.dto.DoacoesDTO;
import com.tech.challenge.domain.dto.DoadorDTO;
import com.tech.challenge.domain.dto.EntidadeBeneficiariaDTO;
import com.tech.challenge.domain.dto.ItensDisponiveisDoacoesDTO;
import com.tech.challenge.domain.service.DoacoesService;
import com.tech.challenge.domain.service.DoadorService;
import com.tech.challenge.domain.service.EntidadeBeneficiariaService;
import com.tech.challenge.domain.service.ItensDisponiveisDoacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/FluxoExemplo")
@RequiredArgsConstructor
@Tag(name = "EndPoint Fluxo Exemplo", description = "Endpoints referente ao exemplo de fluxo do projeto")
public class FluxoExemploController {

    private final DoadorService doadorService;
    private final EntidadeBeneficiariaService entidadeService;
    private final ItensDisponiveisDoacaoService itensDisponiveisService;
    private final DoacoesService DoacoesService;

    private static final HttpClient CLIENT = HttpClient.newHttpClient();

    @GetMapping
    @Operation(
            summary = "Fluxo exemplo para o TechChallenge",
            description = "Esse endpoint retorna uma lista de mensagens exemplificando o fluxo do projeto."
    )
    public ResponseEntity<String> fluxoExemplo() throws Exception {
        StringBuilder mensagens = new StringBuilder();

        String UrlInserirDoador = "http://localhost:8080/doador/inserirDoador";
        String UrlInserirEntidadeBeneficiaria = "http://localhost:8080/entidadebeneficiaria/inserirEntidadeBeneficiaria";
        String UrlInserirItemDisponivelDoacao = "http://localhost:8080/itensDisponiveisDoacao/inserirItensDisponiveisDoacao";
        String UrlRealizarDoacao = "http://localhost:8080/doador/realizarDoacao";
        Gson gson = new Gson();

        mensagens.append("Inserindo Doador\n");
        String requestBody = "{\n" +
                "\t\"cpfcnpj\": \"30486873005\",\n" +
                "\t\"nome\": \"Usuário Exemplo\",\n" +
                "\t\"endereco\": \"Rua Exemplo\",\n" +
                "\t\"telefone\": \"12345678\",\n" +
                "\t\"email\": \"exemplo@gmail.com\"\n" +
                "}";
        mensagens.append(requestBody + "\n");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(UrlInserirDoador))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        int idDoador = gson.fromJson(response.body(),DoadorDTO.class).id();
        mensagens.append("Id doador Inserido = " + idDoador);
        mensagens.append("\nInserindo EntidadeBeneficiaria\n");
        requestBody = "{\n" +
                "\t\"cnpj\": \"71114020000131\",\n" +
                "\t\"nome\": \"Instituição Exemplo\",\n" +
                "\t\"endereco\": \"Endereço Exemplo\",\n" +
                "\t\"telefone\": \"12345678\",\n" +
                "\t\"email\": \"instituicao@gmail.com\"\n" +
                "}";
        mensagens.append(requestBody + "\n");
        request = HttpRequest.newBuilder()
                .uri(URI.create(UrlInserirEntidadeBeneficiaria))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        int idEntidadeBeneficiaria = gson.fromJson(response.body(), EntidadeBeneficiariaDTO.class).id();
        mensagens.append("\nId Entidade Beneficiária inserida = " + idEntidadeBeneficiaria);

        mensagens.append("\nInserindo Item Disponivel Doação\n");
        requestBody = "{\n" +
                "\t\"id_entidade\": \""+idEntidadeBeneficiaria+"\",\n" +
                "\t\"descricao\": \"Pacote de Arroz 1 KG\",\n" +
                "\t\"quantidade\": \"1\",\n" +
                "\t\"medida\": \"KG\"\n" +
                "}";
        mensagens.append(requestBody + "\n");
        request = HttpRequest.newBuilder()
                .uri(URI.create(UrlInserirItemDisponivelDoacao))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        int idItemDisponivelDoacao = gson.fromJson(response.body(), ItensDisponiveisDoacoesDTO.class).id();
        mensagens.append("\nId Item Disponivel Doação inserida = " + idItemDisponivelDoacao);

        mensagens.append("\nRealizando Doação\n");
        requestBody = "{\n" +
                "\t\"id_doador\": \""+idDoador+"\",\n" +
                "\t\"id_itemDisponivelDoacao\": \""+idItemDisponivelDoacao+"\",\n" +
                "\t\"modalidade\": \"3\",\n" +
                "\t\"valorDoadoReais\": \"100\"\n" +
                "}";
        mensagens.append(requestBody + "\n");
        request = HttpRequest.newBuilder()
                .uri(URI.create(UrlRealizarDoacao))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        var DoacaoFinal = gson.fromJson(response.body(), DoacoesDTO.class);
        mensagens.append("\nDoação realizada!\n\n");
        mensagens.append(DoacaoFinal);

        return ResponseEntity.ok(mensagens.toString());
    }
}
