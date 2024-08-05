package com.tech.challenge.domain.service;

import com.tech.challenge.domain.controller.exception.ControllerBadRequestException;
import com.tech.challenge.domain.controller.exception.ControllerNotFoundException;
import com.tech.challenge.domain.dto.EntidadeBeneficiariaDTO;
import com.tech.challenge.domain.entities.EntidadeBeneficiaria;
import com.tech.challenge.domain.formatter.TelefoneFormatter;
import com.tech.challenge.domain.validators.TelefoneValidator;
import com.tech.challenge.infraestructure.repository.EntidadeBeneficiariaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntidadeBeneficiariaService {
    private final EntidadeBeneficiariaRepository entidadeRepo;

    public Collection<EntidadeBeneficiariaDTO> ListarEntidades() {
        return entidadeRepo.findAll().
                stream().
                map(this::toEntidadeBeneficiariaDTO).
                collect(Collectors.toList());
    }

    public EntidadeBeneficiariaDTO ObterEntidadeBeneficiariaPeloID(int id) {
        var entidadeBeneficiaria = entidadeRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("EntidadeBeneficiaria não encontrada"));
        return toEntidadeBeneficiariaDTO(entidadeBeneficiaria);
    }

    public EntidadeBeneficiariaDTO InserirEntidadeBeneficiaria(EntidadeBeneficiariaDTO entidadeBeneficiariaDTO) {
        validaEntidadeBeneficiaria(entidadeBeneficiariaDTO,true);

        EntidadeBeneficiaria entidadeBeneficiariaJPA = toEntidadeBeneficiaria(entidadeBeneficiariaDTO);
        return toEntidadeBeneficiariaDTO(entidadeRepo.save(entidadeBeneficiariaJPA));
    }

    public EntidadeBeneficiariaDTO AtualizaEntidadeBeneficiariaPeloId(int id, EntidadeBeneficiariaDTO entidadeBeneficiariaDTO) {
        validaEntidadeBeneficiaria(entidadeBeneficiariaDTO,false);

        try {
            EntidadeBeneficiaria entidadeBeneficiariaRetornada = entidadeRepo.getReferenceById(id);
            //Não será atualizado o CPF/CNPJ pois trata-se de dado único e não deve ser alterado.
            entidadeBeneficiariaRetornada.setNome(entidadeBeneficiariaDTO.nome());
            entidadeBeneficiariaRetornada.setEndereco(entidadeBeneficiariaDTO.endereco());
            entidadeBeneficiariaRetornada.setTelefone(entidadeBeneficiariaDTO.telefone());
            entidadeBeneficiariaRetornada.setEmail(entidadeBeneficiariaDTO.email());
            return toEntidadeBeneficiariaDTO(entidadeRepo.save(entidadeBeneficiariaRetornada));
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("EntidadeBeneficiaria não encontrada");
        }
    }

    public boolean  RemoveEntidadeBeneficiaria(int id) {
        if (entidadeRepo.existsById(id)) {
            entidadeRepo.deleteById(id);
            return true; // Exclusão bem-sucedida
        }
        return false; // Registro não encontrado
    }

    private EntidadeBeneficiariaDTO toEntidadeBeneficiariaDTO(EntidadeBeneficiaria entidadeBeneficiaria) {
        return new EntidadeBeneficiariaDTO(
                entidadeBeneficiaria.getId(),
                entidadeBeneficiaria.getCnpj(),
                entidadeBeneficiaria.getNome(),
                entidadeBeneficiaria.getEndereco(),
                entidadeBeneficiaria.getTelefone(),
                entidadeBeneficiaria.getEmail()
        );
    }

    private EntidadeBeneficiaria toEntidadeBeneficiaria(EntidadeBeneficiariaDTO entidadeBeneficiariaDTO) {
        EntidadeBeneficiaria entidadeNova = new EntidadeBeneficiaria();
        entidadeNova.setId(entidadeBeneficiariaDTO.id());
        entidadeNova.setCnpj(entidadeBeneficiariaDTO.cnpj());
        entidadeNova.setNome(entidadeBeneficiariaDTO.nome());
        entidadeNova.setEndereco(entidadeBeneficiariaDTO.endereco());
        entidadeNova.setTelefone(TelefoneFormatter.extractNumbers(entidadeBeneficiariaDTO.telefone()));
        entidadeNova.setEmail(entidadeBeneficiariaDTO.email());
        return entidadeNova;
    }

    private void validaEntidadeBeneficiaria(EntidadeBeneficiariaDTO entidadeBeneficiariaDTO,boolean validaCNPJ){
        if(!TelefoneValidator.isValidTelefone(entidadeBeneficiariaDTO.telefone()))
            throw new ControllerBadRequestException("O Telefone é Invalido, por favor utilize somente números.");
        if(validaCNPJ && entidadeRepo.findBycnpj(entidadeBeneficiariaDTO.cnpj()).isPresent())
            throw new ControllerBadRequestException("Já existe um registro com o cnpj informado.");
    }
}
