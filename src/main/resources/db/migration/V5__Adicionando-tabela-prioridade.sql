CREATE TABLE prioridade (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nivel INT NOT NULL UNIQUE,
    descricao VARCHAR(100) NOT NULL
);

INSERT INTO prioridade (nivel, descricao) VALUES
(0, 'NORMAL'),
(1, 'PREFERENCIAL'),
(2, 'URGENTE');