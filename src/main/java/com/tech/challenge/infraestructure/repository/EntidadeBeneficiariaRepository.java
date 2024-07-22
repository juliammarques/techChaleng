package com.tech.challenge.infraestructure.repository;

import com.tech.challenge.domain.entities.EntidadeBeneficiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadeBeneficiariaRepository extends JpaRepository<EntidadeBeneficiaria,Integer> {
}
