package com.GerenciadorDeFila.gerenciador_filas_online.mapper;

import com.GerenciadorDeFila.gerenciador_filas_online.dto.FilaControllerDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.model.SenhaChamada;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilaMapper {


    @Mapping(target = "senha", source = "senha", defaultValue = "SENHA INDISPONÍVEL")
    @Mapping(target = "nomeServico", source = "servico.descricao", defaultValue = "Não informado")
    @Mapping(target = "setor", source = "servico.setor.nome", defaultValue = "Não informado")
    @Mapping(target = "status", source = "status.descricao", defaultValue = "Não informado")
    @Mapping(target = "prioridade", source = "prioridade.descricao", defaultValue = "Não informado")
    @Mapping(target = "atendidoPor", source = "atendente.nome", defaultValue = "N/A")
    @Mapping(target = "nomeCaixa", source = "caixa.descricao", defaultValue = "N/A")
    @Mapping(target = "dataDeConclusao", source = "dataDeConclusao")
    @Mapping(target = "dataDeCriacao", source = "dataDeCriacao")
    @Mapping(target = "dataDeProcessamento", source = "dataDeProcessamento")
    FilaControllerDTO toDTO(SenhaChamada senha);
}