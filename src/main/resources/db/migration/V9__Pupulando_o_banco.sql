-- População da tabela `setores`
-- Criando alguns setores de exemplo.
INSERT INTO `setor` (`id`, `nome`, `descricao`, `tag`) VALUES
(1, 'Atendimento Geral', 'Setor para triagem e serviços gerais.', 'ATG'),
(2, 'Financeiro', 'Setor para pagamentos e questões financeiras.', 'FIN'),
(3, 'Suporte Técnico', 'Setor para auxílio com problemas técnicos.', 'SUP');

-- População da tabela `servicos`
-- Criando serviços e associando-os aos setores criados acima.
INSERT INTO `servico` (`id`, `descricao`, `tempo_medio_atendimento`, `setor_id`, `tag`) VALUES
(1, 'Abertura de Conta', 15, 1, 'ABC'),
(2, 'Pagamento de Boletos', 5, 2, 'PGB'),
(3, 'Informações Gerais', 10, 1, 'INF'),
(4, 'Suporte de Acesso', 20, 3, 'ACS'),
(5, 'Negociação de Dívidas', 25, 2, 'NGD');

-- População da tabela `atendentes`
-- Criando alguns atendentes de exemplo.
INSERT INTO `atendente` (`id`, `nome`, `descricao`) VALUES
(1, 'Ana Clara Souza', 'Especialista em Atendimento Geral'),
(2, 'Bruno Martins', 'Analista Financeiro'),
(3, 'Carla Dias', 'Técnica de Suporte N1'),
(4, 'Diego Fernandes', 'Atendente Geral');

-- População da tabela `caixa`
-- Criando os guichês/caixas e associando-os aos setores.
-- Alguns caixas podem já ter um atendente logado.
INSERT INTO `caixa` (`id`, `descricao`, `setor_id`, `atendente_id`) VALUES
(1, 'Guichê 01', 1, 1),
(2, 'Guichê 02', 1, 4),
(3, 'Caixa Financeiro 01', 2, 2),
(4, 'Mesa de Suporte 01', 3, NULL); -- Caixa livre, sem atendente.


