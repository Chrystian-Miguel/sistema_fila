ALTER TABLE senha_chamada ADD COLUMN atendente_id BIGINT;

ALTER TABLE senha_chamada ADD COLUMN caixa_id BIGINT;


ALTER TABLE senha_chamada ADD CONSTRAINT fk_senha_chamada_on_atendente
FOREIGN KEY (atendente_id) REFERENCES atendente (id);

ALTER TABLE senha_chamada ADD CONSTRAINT fk_senha_chamada_on_caixa
FOREIGN KEY (caixa_id) REFERENCES caixa (id);


