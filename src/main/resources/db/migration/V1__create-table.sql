


-- Tabela 'setores' onde identifica em qual local esta rodando esta fila.
CREATE TABLE setores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(255)
);

-- Tabela 'servicos' com a chave estrangeira para 'setores', identifica a qual serviço a fila pertence.
CREATE TABLE servicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    tempo_medio_atendimento INT NULL,
    setor_id BIGINT NOT NULL,
    -- Define a restrição da chave estrangeira.
    -- ON DELETE RESTRICT impede que um setor seja deletado se houver serviços associados a ele.
    CONSTRAINT fk_servicos_setor
        FOREIGN KEY (setor_id)
        REFERENCES setores(id)
        ON DELETE RESTRICT
);

-- Tabela onde as senha retiradas serao armazenadas.
CREATE TABLE senha_chamada (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    senha VARCHAR(50) NOT NULL,
    prioridade INT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'AGUARDANDO',
    servico_id BIGINT NOT NULL,
    data_de_criacao DATETIME NOT NULL,
    data_de_processamento DATETIME NULL,
    data_de_conclusao DATETIME NULL,
    -- Define a restrição da chave estrangeira para o serviço.
    -- ON DELETE RESTRICT impede que um serviço seja deletado se houver senhas associadas a ele.
    CONSTRAINT fk_senha_chamada_servico
        FOREIGN KEY (servico_id)
        REFERENCES servicos(id)
        ON DELETE RESTRICT,

    -- Índice otimizado para a busca da próxima senha a ser chamada.
    -- Essencial para a performance do sistema de filas.
    INDEX idx_fila_atendimento (status, prioridade, data_de_criacao)
);