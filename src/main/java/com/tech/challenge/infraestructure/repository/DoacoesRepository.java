package com.tech.challenge.infraestructure.repository;

import com.tech.challenge.domain.entities.Doacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoacoesRepository extends JpaRepository<Doacoes,Integer> {
}
