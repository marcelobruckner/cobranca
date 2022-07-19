package com.bruckner.cobranca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bruckner.cobranca.model.Titulo;
import com.bruckner.cobranca.repository.TitulosRepository;

@Service
public class CadastroTituloService {

  @Autowired
  private TitulosRepository titulosRepository;

  public List<Titulo> buscarTodos() {
    return titulosRepository.findAll();
  }

  public Optional<Titulo> buscarPorId(Long id) {
    return titulosRepository.findById(id);
  }

  public Titulo salvar(Titulo titulo) {
    try {
      return titulosRepository.save(titulo);
    } catch (DataIntegrityViolationException e) {
      throw new IllegalArgumentException("Formato de data inválido");
    }
  }

  public void apagarTitulo(Titulo titulo) {
    titulosRepository.delete(titulo);
  }
}
