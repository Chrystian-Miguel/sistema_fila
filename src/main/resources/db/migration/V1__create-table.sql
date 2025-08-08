


CREATE TABLE setor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(255)
);


CREATE TABLE servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    tempo_medio_atendimento INT NULL,
    setor_id BIGINT NOT NULL,

    CONSTRAINT fk_servico_setor
        FOREIGN KEY (setor_id)
        REFERENCES setor(id)
        ON DELETE RESTRICT
);


CREATE TABLE senha_chamada (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    senha VARCHAR(50) NOT NULL,
    prioridade INT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'AGUARDANDO',
    servico_id BIGINT NOT NULL,
    data_de_criacao DATETIME NOT NULL,
    data_de_processamento DATETIME NULL,
    data_de_conclusao DATETIME NULL,

    CONSTRAINT fk_senha_chamada_servico
        FOREIGN KEY (servico_id)
        REFERENCES servico(id)
        ON DELETE RESTRICT,


    INDEX idx_fila_atendimento (status, prioridade, data_de_criacao)
);