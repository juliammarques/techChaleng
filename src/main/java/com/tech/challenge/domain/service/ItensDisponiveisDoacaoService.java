package com.tech.challenge.domain.service;

import com.tech.challenge.domain.controller.exception.ControllerBadRequestException;
import com.tech.challenge.domain.controller.exception.ControllerNotFoundException;
import com.tech.challenge.domain.dto.ItensDisponiveisDoacoesDTO;
import com.tech.challenge.domain.entities.ItensDisponiveisDoacoes;
import com.tech.challenge.domain.enums.Medidas;
import com.tech.challenge.domain.enums.StatusItemDoacao;
import com.tech.challenge.infraestructure.repository.ItensDisponiveisDoacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItensDisponiveisDoacaoService {
    private final ItensDisponiveisDoacaoRepository itensDisponiveisRepo;
    private final EntidadeBeneficiariaService entidadeBeneficiariaService;

    public Page<ItensDisponiveisDoacoesDTO> ListarItensDisponiveisDoacao(Pageable pageable) {
        Page<ItensDisponiveisDoacoes> itensDisponiveis = itensDisponiveisRepo.findAll(pageable);
        return itensDisponiveis.map(this::toItensDisponiveisDoacaoDTO);
    }

    public ItensDisponiveisDoacoesDTO ObterItensDisponiveisDoacaoPeloID(int id) {
        var ItensDisponiveisDoacao = itensDisponiveisRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Itens não encontrados"));
        return toItensDisponiveisDoacaoDTO(ItensDisponiveisDoacao);
    }

    public ItensDisponiveisDoacoesDTO InserirItensDisponiveisDoacao(ItensDisponiveisDoacoesDTO ItensDisponiveisDoacoesDTO) {
        validaitensDisponiveisDoacao(ItensDisponiveisDoacoesDTO);
        ItensDisponiveisDoacoes ItensDisponiveisDoacaoJPA = toItensDisponiveisDoacao(ItensDisponiveisDoacoesDTO);
        ItensDisponiveisDoacaoJPA.setStatus(StatusItemDoacao.ATIVO.getDescricao());
        return toItensDisponiveisDoacaoDTO(itensDisponiveisRepo.save(ItensDisponiveisDoacaoJPA));
    }

    public ItensDisponiveisDoacoesDTO AtualizaItensDisponiveisDoacaoPeloId(int id, ItensDisponiveisDoacoesDTO ItensDisponiveisDoacoesDTO) {
        validaitensDisponiveisDoacao(ItensDisponiveisDoacoesDTO);

        try {
            ItensDisponiveisDoacoes itensDisponiveisDoacaoRetornada = itensDisponiveisRepo.getReferenceById(id);
            //A Atualização do Status será disponibilizada em outro método.
            itensDisponiveisDoacaoRetornada.setDescricao(ItensDisponiveisDoacoesDTO.descricao());
            itensDisponiveisDoacaoRetornada.setQuantidade(ItensDisponiveisDoacoesDTO.quantidade());
            itensDisponiveisDoacaoRetornada.setMedida(ItensDisponiveisDoacoesDTO.medida());
            return toItensDisponiveisDoacaoDTO(itensDisponiveisRepo.save(itensDisponiveisDoacaoRetornada));
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Itens Disponivel não encontrado");
        }
    }
    public void AtualizaStatusItensDisponiveisDoacaoPeloId(int id, String novoStatus) {
        try {
            ItensDisponiveisDoacoes itensDisponiveisDoacaoRetornada = itensDisponiveisRepo.getReferenceById(id);
            itensDisponiveisDoacaoRetornada.setStatus(novoStatus);
            itensDisponiveisRepo.save(itensDisponiveisDoacaoRetornada);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Itens Disponivel não encontrado");
        }
    }

    public void RemoveItensDisponiveisDoacao(int id) {
        itensDisponiveisRepo.deleteById(id);
    }

    private ItensDisponiveisDoacoesDTO toItensDisponiveisDoacaoDTO(ItensDisponiveisDoacoes itensDisponiveisDoacoes) {
        return new ItensDisponiveisDoacoesDTO(
                itensDisponiveisDoacoes.getId(),
                itensDisponiveisDoacoes.getId_entidade(),
                itensDisponiveisDoacoes.getDescricao(),
                itensDisponiveisDoacoes.getQuantidade(),
                itensDisponiveisDoacoes.getStatus(),
                itensDisponiveisDoacoes.getMedida()
        );
    }

    private ItensDisponiveisDoacoes toItensDisponiveisDoacao(ItensDisponiveisDoacoesDTO itensDisponiveisDoacaoDTO) {
        ItensDisponiveisDoacoes ItensDisponiveisNovo = new ItensDisponiveisDoacoes();
        ItensDisponiveisNovo.setId(itensDisponiveisDoacaoDTO.id());
        ItensDisponiveisNovo.setId_entidade(itensDisponiveisDoacaoDTO.id_entidade());
        ItensDisponiveisNovo.setDescricao(itensDisponiveisDoacaoDTO.descricao());
        ItensDisponiveisNovo.setQuantidade(itensDisponiveisDoacaoDTO.quantidade());
        ItensDisponiveisNovo.setStatus(itensDisponiveisDoacaoDTO.status());
        ItensDisponiveisNovo.setMedida(itensDisponiveisDoacaoDTO.medida());
        return ItensDisponiveisNovo;
    }


    private void validaitensDisponiveisDoacao(ItensDisponiveisDoacoesDTO itensDisponiveisDoacoesDTO){
        entidadeBeneficiariaService.ObterEntidadeBeneficiariaPeloID(itensDisponiveisDoacoesDTO.id_entidade());

        try{
            Medidas medida = Medidas.valueOf(itensDisponiveisDoacoesDTO.medida());
        }
        catch(IllegalArgumentException e)
        {
            throw new ControllerBadRequestException(
                    """
                    A medida informada não é valida, por favor utilize uma das medidas disponíveis:\
                    KG;LITRO;SACO;PACOTE;CAIXA;ENGRADADO;UNIDADE
                    """);
        }
    }

}
