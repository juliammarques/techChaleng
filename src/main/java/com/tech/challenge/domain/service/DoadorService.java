package com.tech.challenge.domain.service;

import com.tech.challenge.domain.controller.exception.ControllerBadRequestException;
import com.tech.challenge.domain.controller.exception.ControllerNotFoundException;
import com.tech.challenge.domain.dto.DoadorDTO;
import com.tech.challenge.domain.entities.Doador;
import com.tech.challenge.domain.formatter.TelefoneFormatter;
import com.tech.challenge.domain.validators.EmailValidator;
import com.tech.challenge.domain.validators.TelefoneValidator;
import com.tech.challenge.infraestructure.repository.DoadorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tech.challenge.domain.validators.CpfcnpjValidator;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoadorService {
    private final DoadorRepository doadorRepo;

    public Collection<DoadorDTO> ListarDoadores() {
        return doadorRepo.findAll().
                stream().
                map(this::toDoadorDTO).
                collect(Collectors.toList());
    }

    public DoadorDTO ObterDoadorPeloID(int id) {
        var doador = doadorRepo.findById(id).orElseThrow(() -> new ControllerNotFoundException("Doador não Encontrado"));
        return toDoadorDTO(doador);
    }

    public DoadorDTO InserirDoador(DoadorDTO doadorDTO) {
        validaDoador(doadorDTO,true);
        Doador doadorJPA = toDoador(doadorDTO);
        return toDoadorDTO(doadorRepo.save(doadorJPA));
    }

    public DoadorDTO AtualizaDoadorPeloId(int id, DoadorDTO doadorDTO) {
        validaDoador(doadorDTO,false);
        try {
            Doador doadorRetornado = doadorRepo.getReferenceById(id);
            //Não será atualizado o CPF/CNPJ pois trata-se de dado único e não deve ser alterado.
            doadorRetornado.setNome(doadorDTO.nome());
            doadorRetornado.setEndereco(doadorDTO.endereco());
            doadorRetornado.setTelefone(doadorDTO.telefone());
            doadorRetornado.setEmail(doadorDTO.email());
            return toDoadorDTO(doadorRepo.save(doadorRetornado));
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Doador não encontrado");
        }
    }

    public boolean  RemoveDoador(int id) {
        if (doadorRepo.existsById(id)) {
            doadorRepo.deleteById(id);
            return true; // Exclusão bem-sucedida
        }
        return false; // Registro não encontrado
    }

    private DoadorDTO toDoadorDTO(Doador doador) {
        return new DoadorDTO(
                doador.getId(),
                doador.getCpfcnpj(),
                doador.getNome(),
                doador.getEndereco(),
                doador.getTelefone(),
                doador.getEmail()
        );
    }

    private Doador toDoador(DoadorDTO doadorDTO) {
        Doador doadorNovo = new Doador();
        doadorNovo.setId(doadorDTO.id());
        doadorNovo.setCpfcnpj(doadorDTO.cpfcnpj());
        doadorNovo.setNome(doadorDTO.nome());
        doadorNovo.setEndereco(doadorDTO.endereco());
        doadorNovo.setTelefone(TelefoneFormatter.extractNumbers(doadorDTO.telefone()));
        doadorNovo.setEmail(doadorDTO.email());
        return doadorNovo;
    }

    private void validaDoador(DoadorDTO doadorDTO,boolean validaCPFCNPJ){
        if(doadorDTO.cpfcnpj().length() != 11 && doadorDTO.cpfcnpj().length() != 14)
            throw new ControllerBadRequestException("O CPF ou CNPJ é Invalido");
        if (doadorDTO.cpfcnpj().length() == 11 && !CpfcnpjValidator.isValidCPF(doadorDTO.cpfcnpj()))
            throw new ControllerBadRequestException("O CPF é Invalido");
        if (doadorDTO.cpfcnpj().length() == 14 && !CpfcnpjValidator.isValidCNPJ(doadorDTO.cpfcnpj()))
            throw new ControllerBadRequestException("O CNPJ é Invalido");
        if(!EmailValidator.isValidEmail(doadorDTO.email()))
            throw new ControllerBadRequestException("O Email é Invalido");
        if(!TelefoneValidator.isValidTelefone(doadorDTO.telefone()))
            throw new ControllerBadRequestException("O Telefone é Invalido, por favor utilize somente números.");
        if(validaCPFCNPJ && doadorRepo.findBycpfcnpj(doadorDTO.cpfcnpj()).isPresent())
            throw new ControllerBadRequestException("Já existe um registro com o cpf ou cnpj informado.");
    }
}
