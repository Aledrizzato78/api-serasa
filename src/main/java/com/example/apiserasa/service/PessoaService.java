package com.example.apiserasa.service;

import com.example.apiserasa.model.Pessoa;
import com.example.apiserasa.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Page<Pessoa> listarPessoas(String nome, Integer idade, String cep, Pageable pageable) {
        if (nome != null && !nome.isEmpty()) {
            return pessoaRepository.findByNomeContainingIgnoreCase(nome, pageable);
        } else if (idade != null) {
            return pessoaRepository.findByIdade(idade, pageable);
        } else if (cep != null && !cep.isEmpty()) {
            return pessoaRepository.findByCep(cep, pageable);
        }
        return pessoaRepository.findAll(pageable);
    }

    public Optional<Pessoa> buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    @Transactional
    public Pessoa atualizarPessoa(Long id, Pessoa pessoaAtualizada) {
        return pessoaRepository.findById(id).map(pessoa -> {
            pessoa.setNome(pessoaAtualizada.getNome());
            pessoa.setIdade(pessoaAtualizada.getIdade());
            pessoa.setCep(pessoaAtualizada.getCep());
            pessoa.setEstado(pessoaAtualizada.getEstado());
            pessoa.setCidade(pessoaAtualizada.getCidade());
            pessoa.setBairro(pessoaAtualizada.getBairro());
            pessoa.setLogradouro(pessoaAtualizada.getLogradouro());
            pessoa.setTelefone(pessoaAtualizada.getTelefone());
            pessoa.setScore(pessoaAtualizada.getScore());
            return pessoaRepository.save(pessoa);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));
    }

    @Transactional
    public void excluirPessoa(Long id) {
        if (pessoaRepository.existsById(id)) {
            pessoaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pessoa não encontrada com ID: " + id);
        }
    }

    public Page<Pessoa> buscarPessoasPorNome(String nome, Pageable pageable) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

    // Método para obter a descrição do score com base na tabela fornecida
    public String obterDescricaoScore(Integer score) {
        if (score == null) {
            throw new IllegalArgumentException("O score não pode ser nulo.");
        }

        if (score >= 0 && score <= 200) {
            return "Insuficiente";
        } else if (score >= 201 && score <= 500) {
            return "Inaceitável";
        } else if (score >= 501 && score <= 700) {
            return "Aceitável";
        } else if (score >= 701 && score <= 1000) {
            return "Recomendável";
        } else {
            throw new IllegalArgumentException("Score fora do intervalo permitido (0 - 1000)");
        }
    }
}
