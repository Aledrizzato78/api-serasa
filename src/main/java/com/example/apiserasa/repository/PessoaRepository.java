package com.example.apiserasa.repository;

import com.example.apiserasa.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Page<Pessoa> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Pessoa> findByIdade(Integer idade, Pageable pageable);

    Page<Pessoa> findByEnderecoCep(String cep, Pageable pageable);

    Page<Pessoa> findByExcluidoFalseAndNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Pessoa> findByExcluidoFalseAndIdade(Integer idade, Pageable pageable);
    Page<Pessoa> findByExcluidoFalseAndEnderecoCep(String cep, Pageable pageable);

    Page<Pessoa> findByExcluidoFalse(Pageable pageable);
}
