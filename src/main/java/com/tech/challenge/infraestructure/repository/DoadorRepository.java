package com.tech.challenge.infraestructure.repository;

import com.tech.challenge.domain.entities.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoadorRepository extends JpaRepository<Doador,Integer> {
    Optional<Doador> findBycpfcnpj(String cpfCnpj);
}
