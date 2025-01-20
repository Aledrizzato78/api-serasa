package com.example.apiserasa.repository;

import com.example.apiserasa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    // Método para buscar pessoas pelo nome (case insensitive)
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);

    // Método para buscar pessoas por idade
    List<Pessoa> findByIdade(Integer idade);

    // Método para buscar pessoas por CEP
    List<Pessoa> findByCep(String cep);
}
