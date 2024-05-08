package com.refugietransaction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refugietransaction.model.Ventes;

public interface VentesRepository extends JpaRepository<Ventes, Long> {

}
