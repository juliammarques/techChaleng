package com.tech.challenge.domain.service;

import com.tech.challenge.domain.controller.exception.ControllerBadRequestException;
import com.tech.challenge.domain.controller.exception.ControllerNotFoundException;
import com.tech.challenge.domain.dto.DoacoesDTO;
import com.tech.challenge.domain.entities.Doacoes;
import com.tech.challenge.domain.enums.StatusDoacoes;
import com.tech.challenge.domain.enums.StatusItemDoacao;
import com.tech.challenge.infraestructure.repository.DoacoesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//Não foi criado o método RemoverDoacao pois no fluxo do projeto, isso não ocorre.

@Service
@RequiredArgsConstructor
public class DoacoesService {
    private final DoacoesRepository doacoesRepo;
    private final DoadorService doadorService;
    private final ItensDisponiveisDoacaoService itensDisponiveisDoacaoService;

    public Page<DoacoesDTO> ListarDoacoes(Pageable pageable) {
        Page<Doacoes> Doacoes = doacoesRepo.findAll(pageable);
        return Doacoes.map(this::toDoacaoDTO);
    }

    public DoacoesDTO ObterDoacoesPeloID(int id) {
        var Doacao = doacoesRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Doação não encontrada"));
        return toDoacaoDTO(Doacao);
    }

    public DoacoesDTO InserirDoacao(DoacoesDTO doacoesDTO) {
        validaDoacao(doacoesDTO);

        Doacoes DoacaoJPA = toDoacao(doacoesDTO);

        // Ao inserir, o status inicial sempre será solicitado.

        var doacaoRealizada = doacoesRepo.save(DoacaoJPA);

        if((doacoesDTO.modalidade() == 2 || doacoesDTO.modalidade() == 3) && doacoesDTO.id_itemDisponivelDoacao() != 0){
            itensDisponiveisDoacaoService.AtualizaStatusItensDisponiveisDoacaoPeloId(
                    doacoesDTO.id_itemDisponivelDoacao(),
                    StatusItemDoacao.INATIVO.getDescricao());
            DoacaoJPA.setStatus(StatusDoacoes.SOLICITADO.getDescricao());
            // NESSE CASO DEVERIA ENCAMINHAR PARA O SISTEMA EXTERNO ADICIONANDO O MELHOR DIA,E IR ATUALIZANDO O STATUS.
        }
        else
            DoacaoJPA.setStatus(StatusDoacoes.FINALIZADO.getDescricao());

        return toDoacaoDTO(doacaoRealizada);
    }

    //O único valor alterado será o status,tendo em vista de que não faz sentido alterar os outros valores.
    public DoacoesDTO AtualizaStatusDoacaoPeloId(int id, String novoStatus) {
        validaStatus(novoStatus);

        try {
            Doacoes doacaoRetornada = doacoesRepo.getReferenceById(id);
            doacaoRetornada.setStatus(novoStatus);
            return toDoacaoDTO(doacoesRepo.save(doacaoRetornada));
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Doação não encontrada");
        }
    }

    private DoacoesDTO toDoacaoDTO(Doacoes doacoes) {
        return new DoacoesDTO(
                doacoes.getId(),
                doacoes.getId_doador(),
                doacoes.getId_itemDisponivelDoacao(),
                doacoes.getModalidade(),
                doacoes.getStatus(),
                doacoes.getValorDoadoReais()
        );
    }

    private Doacoes toDoacao(DoacoesDTO doacoesDTO) {
        Doacoes doacao = new Doacoes();
        doacao.setId(doacoesDTO.id());
        doacao.setId_doador(doacoesDTO.id_doador());
        doacao.setId_itemDisponivelDoacao(doacoesDTO.id_itemDisponivelDoacao());
        doacao.setModalidade(doacoesDTO.modalidade());
        doacao.setStatus(doacoesDTO.status());
        doacao.setValorDoadoReais(doacoesDTO.valorDoadoReais());
        return doacao;
    }


    private void validaDoacao(DoacoesDTO doacoesDTO){
        if(doacoesDTO.modalidade() != 1 && doacoesDTO.modalidade() != 2 && doacoesDTO.modalidade() != 3)
            throw new ControllerBadRequestException("Por favor informar a modalidade da doação.1-Somente Dinheiro,2-Somente Produtos,3 - Dinheiro e Produtos");

        doadorService.ObterDoadorPeloID(doacoesDTO.id_doador());
        if(doacoesDTO.modalidade() == 1 && doacoesDTO.valorDoadoReais() == 0){
            throw new ControllerBadRequestException("Doação somente em dinheiro e 0 reais informados, por favor informe um valor válido.");
        }
        if(doacoesDTO.modalidade() == 2){
            itensDisponiveisDoacaoService.ObterItensDisponiveisDoacaoPeloID(doacoesDTO.id_itemDisponivelDoacao());
        }
        if(doacoesDTO.modalidade() == 3){
            if(doacoesDTO.valorDoadoReais() == 0)
                throw new ControllerBadRequestException("Doação somente em dinheiro e 0 reais informados, por favor informe um valor válido.");

            itensDisponiveisDoacaoService.ObterItensDisponiveisDoacaoPeloID(doacoesDTO.id_itemDisponivelDoacao());
        }
    }
    private void validaStatus(String novoStatus) {
        try{
            StatusDoacoes statusdoacao = StatusDoacoes.valueOf(novoStatus);
        }
        catch(IllegalArgumentException e)
        {
            throw new ControllerBadRequestException(
                    """
                    O Status informado não é valido, por favor utilize um dos status disponíveis:\
                    SOLICITADO;A_CAMINHO;ENTREGUE;REAGENDAR;
                    """);
        }
    }
}
