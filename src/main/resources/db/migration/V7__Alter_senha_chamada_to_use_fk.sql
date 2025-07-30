-- PASSO 1: Adicionar as novas colunas que se tornarão as chaves estrangeiras.
-- ATENÇÃO: Este script apaga todos os dados da tabela 'senha_chamada'
-- para refatorar a estrutura sem migrar dados antigos, conforme solicitado.
TRUNCATE TABLE senha_chamada;
-- PASSO 1: Remove as colunas antigas que serão substituídas.
ALTER TABLE senha_chamada DROP COLUMN prioridade;
ALTER TABLE senha_chamada DROP COLUMN status;

-- PASSO 2: Adiciona as novas colunas que serão as chaves estrangeiras.
ALTER TABLE senha_chamada ADD COLUMN prioridade_id BIGINT NOT NULL;
ALTER TABLE senha_chamada ADD COLUMN status_id BIGINT NOT NULL;

-- PASSO 3: Adiciona as restrições de chave estrangeira (FK).
ALTER TABLE senha_chamada ADD CONSTRAINT fk_senha_chamada_prioridade FOREIGN KEY (prioridade_id) REFERENCES prioridade(id);
ALTER TABLE senha_chamada ADD CONSTRAINT fk_senha_chamada_status FOREIGN KEY (status_id) REFERENCES status(id);