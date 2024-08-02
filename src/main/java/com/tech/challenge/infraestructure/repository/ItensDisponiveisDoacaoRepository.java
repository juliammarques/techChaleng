package com.tech.challenge.infraestructure.repository;

import com.tech.challenge.domain.entities.ItensDisponiveisDoacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensDisponiveisDoacaoRepository extends JpaRepository<ItensDisponiveisDoacoes,Integer> {
}
