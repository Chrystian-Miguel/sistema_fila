CREATE TABLE status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL UNIQUE
);

-- Popula a tabela com os valores padrão do sistema.
INSERT INTO status (id, descricao) VALUES
(1, 'AGUARDANDO'),
(2, 'EM_ATENDIMENTO'),
(3, 'CONCLUIDO'),
(4, 'CANCELADO');