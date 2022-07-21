package com.bruckner.cobranca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruckner.cobranca.model.Titulo;

public interface TitulosRepository extends JpaRepository<Titulo, Long> {

  List<Titulo> findByDescricaoContaining(String descricao);

}
