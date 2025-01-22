package com.example.apiserasa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 18, message = "A idade mínima permitida é 18 anos")
    @Max(value = 120, message = "A idade máxima permitida é 120 anos")
    private Integer idade;

    @NotNull(message = "O score é obrigatório")
    @Min(value = 0, message = "O score mínimo permitido é 0")
    @Max(value = 1000, message = "O score máximo permitido é 1000")
    private Integer score;

    // Relacionamento com Endereco (tabela separada)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    // Construtor padrão
    public Pessoa() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
