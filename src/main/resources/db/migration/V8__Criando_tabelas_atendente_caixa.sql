-- Cria a tabela 'atendente'
CREATE TABLE atendentes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);

-- Cria a tabela 'caixas' com as chaves estrangeiras
CREATE TABLE caixas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    setor_id BIGINT NOT NULL,
    atendente_id BIGINT,
    CONSTRAINT fk_caixa_setores FOREIGN KEY (setor_id) REFERENCES setores(id) ON DELETE RESTRICT,

    CONSTRAINT fk_caixa_atendente FOREIGN KEY (atendente_id) REFERENCES atendentes(id) ON DELETE RESTRICT
);