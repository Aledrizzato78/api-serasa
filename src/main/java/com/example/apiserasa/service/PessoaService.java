package com.example.apiserasa.service;

import com.example.apiserasa.model.Endereco;
import com.example.apiserasa.model.Pessoa;
import com.example.apiserasa.repository.EnderecoRepository;
import com.example.apiserasa.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final CepService cepService;


    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, CepService cepService) {
        this.pessoaRepository = pessoaRepository;
        this.cepService = cepService;
    }

    @PostMapping("/criar")
    @PreAuthorize("hasRole('ADMIN')")  // Somente ADMIN pode criar
    public ResponseEntity<Pessoa> criarPessoa(@RequestBody Pessoa pessoaRequest) {
        // Endereço informado no body
        Endereco enderecoRequest = pessoaRequest.getEndereco();

        // Tentamos consultar a API externa
        Endereco enderecoCompleto;
        try {
            enderecoCompleto = cepService.buscarEnderecoPorCep(enderecoRequest.getCep());
        } catch (Exception e) {
            // Se não conseguir consultar (CEP inválido ou API offline), usa o que veio no body
            enderecoCompleto = enderecoRequest;
        }

        // Atribui o endereço (da API ou do body, dependendo do resultado)
        pessoaRequest.setEndereco(enderecoCompleto);

        // Salva normalmente (ex.: usando o seu service de Pessoas)
        Pessoa novaPessoa = pessoaRepository.save(pessoaRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    public Page<Pessoa> listarPessoas(String nome, Integer idade, String cep, Pageable pageable) {
        if (nome != null && !nome.isEmpty()) {
            return pessoaRepository.findByExcluidoFalseAndNomeContainingIgnoreCase(nome, pageable);
        } else if (idade != null) {
            return pessoaRepository.findByExcluidoFalseAndIdade(idade, pageable);
        } else if (cep != null && !cep.isEmpty()) {
            return pessoaRepository.findByExcluidoFalseAndEnderecoCep(cep, pageable);
        }
        return pessoaRepository.findByExcluidoFalse(pageable);
    }

    public Optional<Pessoa> buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    @Transactional
    public Pessoa atualizarPessoa(Long id, Pessoa pessoaAtualizada) {
        return pessoaRepository.findById(id)
                .map(pessoa -> {
                    // Atualiza campos diretos de Pessoa
                    pessoa.setNome(pessoaAtualizada.getNome());
                    pessoa.setIdade(pessoaAtualizada.getIdade());
                    pessoa.setScore(pessoaAtualizada.getScore());

                    // Atualiza o Endereço dentro de Pessoa
                    if (pessoaAtualizada.getEndereco() != null) {
                        // Se o endereço atual da pessoa for nulo, cria um novo para preencher
                        if (pessoa.getEndereco() == null) {
                            pessoa.setEndereco(new Endereco());
                        }
                        pessoa.getEndereco().setCep(pessoaAtualizada.getEndereco().getCep());
                        pessoa.getEndereco().setLogradouro(pessoaAtualizada.getEndereco().getLogradouro());
                        pessoa.getEndereco().setComplemento(pessoaAtualizada.getEndereco().getComplemento());
                        pessoa.getEndereco().setBairro(pessoaAtualizada.getEndereco().getBairro());
                        pessoa.getEndereco().setLocalidade(pessoaAtualizada.getEndereco().getLocalidade());
                        pessoa.getEndereco().setUf(pessoaAtualizada.getEndereco().getUf());
                    } else {
                        // Caso queira permitir que o endereço seja removido,
                        // você pode definir aqui um comportamento para setar null
                        // Exemplo:
                        // pessoa.setEndereco(null);
                    }

                    return pessoaRepository.save(pessoa);
                })
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));
    }


    public void excluirPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));
        pessoa.setExcluido(true);
        pessoaRepository.save(pessoa);  // Atualiza o registro com excluido = true
    }

    public Page<Pessoa> buscarPessoasPorNome(String nome, Pageable pageable) {
        return pessoaRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }

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
