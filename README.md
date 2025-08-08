# Sistema de Gerenciamento de Filas Online

Este projeto é uma API RESTful construída com Spring Boot para gerir um sistema de filas de atendimento, com suporte para diferentes níveis de prioridade e tipos de guichês de atendimento.

## Acesso à Documentação Interativa (Swagger)

Com a aplicação em execução, a documentação interativa da API (Swagger UI) pode ser acedida através do seguinte link:

[http://localhost:8080/swagger-ui/index.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui/index.html)

## Funcionalidades

- [x] **Geração de Senhas**: Endpoint para criar novas senhas para diferentes serviços e prioridades.
- [x] **Chamada de Senhas**: Endpoints para chamar a próxima senha da fila.
- [x] **Controle de Prioridade**: O sistema respeita uma ordem de prioridade (Urgente \> Preferencial \> Normal) ao chamar senhas.
- [x] **Guichês Especializados**: Endpoints específicos para guichês que atendem apenas filas normais ou apenas filas prioritárias.
- [x] **Visualização de Histórico**: Endpoint para visualizar as últimas senhas atendidas.
- [ ] **Impressão/Retirada Digital**: Funcionalidade futura para permitir que o utilizador obtenha a senha física ou digitalmente.

## Tecnologias Utilizadas

* **Backend**: Java 21, Spring Boot 3
* **Persistência de Dados**: Spring Data JPA, Hibernate
* **Base de Dados**: MariaDB / MySQL
* **Migrations**: Flyway
* **Documentação da API**: SpringDoc (OpenAPI 3 / Swagger)
* **Utilitários**: Lombok

## Pré-requisitos

* Java JDK 21 ou superior
* Maven 3.8 ou superior
* Uma instância de MariaDB ou MySQL a correr localmente.

## Configuração e Execução

1.  **Clone o repositório:**

    ```shell
    git clone <url-do-seu-repositorio>
    cd gerenciador-filas-online
    ```

2.  **Crie a Base de Dados:**
    Antes de iniciar a aplicação, a base de dados precisa de ser criada manualmente. Execute o seguinte comando no seu cliente MySQL/MariaDB:

    ```sql
    CREATE DATABASE fila_online_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_general_ci;
    ```

3.  **Configure a Conexão:**
    Abra o ficheiro `src/main/resources/application.properties` e, se necessário, ajuste as credenciais da sua base de dados:

    ```properties
    spring.datasource.url=jdbc:mariadb://localhost:3306/fila_online_db
    spring.datasource.username=root
    spring.datasource.password=1234
    ```

4.  **Execute a Aplicação:**
    Utilize o Maven para compilar e executar o projeto:

    ```shell
    mvn spring-boot:run
    ```

    A API estará disponível em `http://localhost:8080`.

## Documentação da API

A API está organizada em torno da geração e chamada de senhas.

**URL Base:** `/api`

### Endpoints de Geração de Senha

#### Gerar uma Nova Senha

Cria uma nova senha para um serviço e prioridade específicos.

* **Método:** `POST`
* **Path:** `/api/senha/gerar-senha`
* **Corpo da Requisição (`NovaSenhaRequestDTO`)**
    * `prioridade`: `0` para Normal, `1` para Preferencial, `2` para Urgente.
  <!-- end list -->
  ```json
  {
    "servicoId": 1,
    "prioridade": 0
  }
  ```
* **Resposta de Sucesso (201 Created)**
  ```json
  {
    "senha": "N001",
    "status": "AGUARDANDO",
    "dataDeCriacao": "2025-08-08T15:00:00",
    "servico": "Atendimento Geral",
    "Prioridade": "NORMAL"
  }
  ```

### Endpoints de Chamada de Fila

#### Listar Últimas 4 Senhas Atendidas

Retorna uma lista com as 4 últimas senhas concluídas no dia.

* **Método:** `GET`
* **Path:** `/api/fila/ultima-4-atendida`
* **Resposta de Sucesso (200 OK)**
  ```json
  [
      {
          "senha": "N015",
          "status": "CONCLUIDO",
          "prioridade": "NORMAL",
          "setor": "Atendimento Geral",
          "nomeServico": "Abertura de Conta",
          "dataDeCriacao": "2025-08-08T10:30:00",
          "dataDeProcessamento": "2025-08-08T10:45:10",
          "dataDeConclusao": "2025-08-08T10:55:00",
          "atendidoPor": "Ana Souza",
          "nomeCaixa": "Caixa 01"
      }
  ]
  ```

#### Chamar Próxima Senha (Guichê Misto)

Busca a próxima senha respeitando toda a ordem de prioridade (Urgente \> Preferencial \> Normal).

* **Método:** `POST`
* **Path:** `/api/fila/chamar-proxima`
* **Corpo da Requisição (`ChamarProximaSenhaRequestDTO`)**
  ```json
  {
    "atendenteId": 1,
    "caixaId": 5,
    "servicoId": 2
  }
  ```
* **Resposta de Sucesso (200 OK)**
  ```json
  {
    "senha": "U001",
    "status": "EM_ATENDIMENTO",
    "prioridade": "URGENTE",
    "setor": "Suporte",
    "nomeServico": "Suporte Técnico Nível 2",
    "dataDeCriacao": "2025-08-08T11:05:00",
    "dataDeProcessamento": "2025-08-08T11:10:22",
    "dataDeConclusao": null,
    "atendidoPor": "Mariana Costa",
    "nomeCaixa": "Guichê de Suporte"
  }
  ```

#### Chamar Próxima Senha (Guichê Normal)

Busca a próxima senha considerando apenas as prioridades **Normal** e **Urgente**.

* **Método:** `POST`
* **Path:** `/api/fila/chamar-proxima-normal`
* **Corpo da Requisição:** Idêntico ao do guichê misto.

#### Chamar Próxima Senha (Guichê Prioritário)

Busca a próxima senha considerando apenas as prioridades **Preferencial** e **Urgente**.

* **Método:** `POST`
* **Path:** `/api/fila/chamar-proxima-prioritaria`
* **Corpo da Requisição:** Idêntico ao do guichê misto.