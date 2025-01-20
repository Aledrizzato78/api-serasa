package com.example.apiserasa.service;

import com.example.apiserasa.model.Pessoa;
import com.example.apiserasa.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    // Criar uma nova pessoa
    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    // Listar todas as pessoas
    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    // Buscar uma pessoa por ID
    public Optional<Pessoa> buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    // Atualizar uma pessoa existente
    public Pessoa atualizarPessoa(Long id, Pessoa dadosAtualizados) {
        return pessoaRepository.findById(id).map(pessoa -> {
            pessoa.setNome(dadosAtualizados.getNome());
            pessoa.setIdade(dadosAtualizados.getIdade());
            pessoa.setCep(dadosAtualizados.getCep());
            pessoa.setEstado(dadosAtualizados.getEstado());
            pessoa.setCidade(dadosAtualizados.getCidade());
            pessoa.setBairro(dadosAtualizados.getBairro());
            pessoa.setLogradouro(dadosAtualizados.getLogradouro());
            pessoa.setTelefone(dadosAtualizados.getTelefone());
            pessoa.setScore(dadosAtualizados.getScore());
            return pessoaRepository.save(pessoa);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));
    }

    // Excluir uma pessoa pelo ID
    public void excluirPessoa(Long id) {
        if (pessoaRepository.existsById(id)) {
            pessoaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pessoa não encontrada com ID: " + id);
        }
    }

    // Buscar pessoas por nome (parcial)
    public List<Pessoa> buscarPessoasPorNome(String nome) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome);
    }
}
