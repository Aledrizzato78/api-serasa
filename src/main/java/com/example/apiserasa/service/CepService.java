package com.example.apiserasa.service;

import com.example.apiserasa.dto.EnderecoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";

    public EnderecoDTO buscarEnderecoPorCep(String cep) {
        String url = String.format(VIA_CEP_URL, cep);

        try {
            EnderecoDTO endereco = restTemplate.getForObject(url, EnderecoDTO.class);
            if (endereco != null && endereco.getCep() != null) {
                return endereco;
            } else {
                throw new RuntimeException("CEP inválido ou não encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar o CEP: " + cep);
        }
    }
}
