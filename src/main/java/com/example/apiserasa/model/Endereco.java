package com.example.apiserasa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "enderecos")
// Ignora qualquer propriedade desconhecida que venha no JSON
@JsonIgnoreProperties(ignoreUnknown = true)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("cep")
    private String cep;

    @JsonProperty("logradouro")
    private String logradouro;

    @JsonProperty("complemento")
    private String complemento;

    @JsonProperty("bairro")
    private String bairro;

    @JsonProperty("localidade")
    private String localidade;

    @JsonProperty("uf")
    private String uf;

    @JsonProperty("unidade")
    private String unidade;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("regiao")
    private String regiao;

    @JsonProperty("ibge")
    private String ibge;

    @JsonProperty("gia")
    private String gia;

    @JsonProperty("ddd")
    private String ddd;

    @JsonProperty("siafi")
    private String siafi;

    public Endereco() {
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }
    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getUnidade() {
        return unidade;
    }
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRegiao() {
        return regiao;
    }
    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getIbge() {
        return ibge;
    }
    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getGia() {
        return gia;
    }
    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDdd() {
        return ddd;
    }
    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getSiafi() {
        return siafi;
    }
    public void setSiafi(String siafi) {
        this.siafi = siafi;
    }
}
