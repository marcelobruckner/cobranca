package com.bruckner.cobranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruckner.cobranca.model.Titulo;

public interface TitulosRepository extends JpaRepository<Titulo, Long> {

}
