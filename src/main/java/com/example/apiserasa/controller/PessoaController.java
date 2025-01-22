package com.example.apiserasa.controller;

import com.example.apiserasa.model.Pessoa;
import com.example.apiserasa.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // Endpoint para criar uma nova pessoa
    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@RequestBody Pessoa pessoa) {
        Pessoa novaPessoa = pessoaService.criarPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    // Endpoint para listar todas as pessoas com filtros e paginação
    @GetMapping
    public ResponseEntity<Page<Pessoa>> listarPessoas(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer idade,
            @RequestParam(required = false) String cep,
            Pageable pageable) {
        Page<Pessoa> pessoas = pessoaService.listarPessoas(nome, idade, cep, pageable);
        return ResponseEntity.ok(pessoas);
    }

    // Endpoint para buscar uma pessoa pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> buscarPessoaPorId(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaService.buscarPessoaPorId(id);
        return pessoa.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para atualizar uma pessoa existente
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoaAtualizada) {
        try {
            Pessoa pessoa = pessoaService.atualizarPessoa(id, pessoaAtualizada);
            return ResponseEntity.ok(pessoa);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint para excluir uma pessoa pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
        try {
            pessoaService.excluirPessoa(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint para buscar pessoas por nome com paginação
    @GetMapping("/buscar")
    public ResponseEntity<Page<Pessoa>> buscarPessoasPorNome(
            @RequestParam String nome,
            Pageable pageable) {
        Page<Pessoa> pessoas = pessoaService.buscarPessoasPorNome(nome, pageable);
        return ResponseEntity.ok(pessoas);
    }

    // Novo endpoint para obter a descrição do score
    @GetMapping("/score/{score}")
    public ResponseEntity<String> obterDescricaoScore(@PathVariable Integer score) {
        try {
            String descricao = pessoaService.obterDescricaoScore(score);
            return ResponseEntity.ok(descricao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
