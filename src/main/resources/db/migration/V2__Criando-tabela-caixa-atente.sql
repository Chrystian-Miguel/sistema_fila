
-- Cria a tabela 'atendente'
CREATE TABLE atendente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);

-- Cria a tabela 'caixas' com as chaves estrangeiras
CREATE TABLE caixa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    setor_id BIGINT NOT NULL,
    atendente_id BIGINT,
    CONSTRAINT fk_caixa_setor FOREIGN KEY (setor_id) REFERENCES setor(id) ON DELETE RESTRICT,

    CONSTRAINT fk_caixa_atendente FOREIGN KEY (atendente_id) REFERENCES atendente(id) ON DELETE RESTRICT
);