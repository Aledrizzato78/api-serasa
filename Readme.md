API Serasa

Introdução

A API Serasa é um serviço REST desenvolvido em Java com Spring Boot para cadastro e consulta de pessoas, incluindo informações de score e endereço.

Tecnologias Utilizadas

Java 17

Spring Boot 3.4.1

Spring Security

H2 Database

Maven

Swagger (Springdoc OpenAPI)

Como Rodar a API

1. Requisitos

Certifique-se de ter instalado:

Java 17

Apache Maven

2. Clonar o Projeto

# Clone o repositório
$ git clone <URL_DO_REPOSITORIO>
$ cd api-serasa

3. Construir o Projeto

# Executar o build com Maven
$ mvn clean install

4. Executar a API

# Iniciar o servidor Spring Boot
$ mvn spring-boot:run

A API estará disponível em: http://localhost:8080

Endpoints Disponíveis

1. Autenticação

POST /api/auth/login - Autentica o usuário (usuário: admin, senha: admin)

2. Cadastro de Pessoa

POST /api/pessoas - Cadastra uma nova pessoa.

Exemplo de JSON:

{
"nome": "João Silva",
"idade": 30,
"cep": "12345-678",
"estado": "SP",
"cidade": "São Paulo",
"bairro": "Centro",
"logradouro": "Rua A",
"telefone": "11987654321",
"score": 750
}

3. Consulta de Pessoas

GET /api/pessoas - Retorna todas as pessoas cadastradas.

GET /api/pessoas/{id} - Retorna uma pessoa pelo ID.

4. H2 Console

Acesse o console do banco H2 para visualizar os dados:
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

Usuário: sa

Senha: (vazio)

Documentação da API

A documentação interativa da API pode ser acessada via Swagger em:
http://localhost:8080/api-docs

Autenticação

A API utiliza autenticação via JWT Token. Para acessar os endpoints protegidos:

Autentique-se em /api/auth/login com admin/admin.

Utilize o token recebido no cabeçalho das requisições:

### Perfis de Usuário

Por padrão, dois usuários em memória:
- **admin** (senha: **admin**) com ROLE_ADMIN
- **user** (senha: **user**) com ROLE_USER

#### Permissões:
- **ROLE_ADMIN**: pode criar (POST), atualizar (PUT) e excluir (DELETE).
- **ROLE_USER**: pode somente consultar (GET).

### Consulta de Endereço via CEP
Ao criar uma nova Pessoa, basta enviar o JSON com o campo "cep" (por exemplo, "01001-000"), que a aplicação consultará os dados de endereço via [ViaCEP](https://viacep.com.br/).
