
    public void setNomeInstrutor(String nomeInstrutor) {
        this.nomeInstrutor = nomeInstrutor;
    }

    public UUID getInstrutorId() {
        return instrutorId;
    }

    public void setInstrutorId(UUID instrutorId) {
        this.instrutorId = instrutorId;
    }

    public Boolean getGratuito() {
        return gratuito;
    }

    public void setGratuito(Boolean gratuito) {
        this.gratuito = gratuito;
    }

    public Long getTotalInscritos() {
        return totalInscritos;
    }

    public void setTotalInscritos(Long totalInscritos) {
        this.totalInscritos = totalInscritos;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
package com.venitech.startcurso.dto.curso;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CursoResponseDTO {

    private UUID id;
    private String titulo;
    private String descricao;
    private Integer vagas;
    private BigDecimal valor;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String nomeInstrutor;
    private UUID instrutorId;
    private Boolean gratuito;
    private Long totalInscritos;
    private LocalDateTime dataCriacao;

    // Construtores
    public CursoResponseDTO() {}

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public void setValor(BigDecimal valor) {
    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public BigDecimal getValor() {
        return valor;
    }

        this.valor = valor;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public String getNomeInstrutor() {
        return nomeInstrutor;
    }

