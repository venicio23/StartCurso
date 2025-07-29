package com.venitech.startcurso.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Título é obrigatório")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Column(nullable = false, length = 1000)
    private String descricao;

    @NotNull(message = "Número de vagas é obrigatório")
    @PositiveOrZero(message = "Número de vagas deve ser positivo ou zero")
    @Column(nullable = false)
    private Integer vagas;

    @NotNull(message = "Valor é obrigatório")
    @PositiveOrZero(message = "Valor deve ser positivo ou zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "Data de início é obrigatória")
    @Column(nullable = false)
    private LocalDateTime dataInicio;

    @NotNull(message = "Data de fim é obrigatória")
    @Column(nullable = false)
    private LocalDateTime dataFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrutor_id", nullable = false)
    private Usuario instrutor;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // Construtores
    public Curso() {}

    public Curso(String titulo, String descricao, Integer vagas, BigDecimal valor,
                 LocalDateTime dataInicio, LocalDateTime dataFim, Usuario instrutor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.vagas = vagas;
        this.valor = valor;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.instrutor = instrutor;
        this.ativo = true;
        this.dataCriacao = LocalDateTime.now();
    }

    // Método auxiliar para verificar se o curso é gratuito
    public boolean isGratuito() {
        return valor.compareTo(BigDecimal.ZERO) == 0;
    }

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

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
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

    public Usuario getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(Usuario instrutor) {
        this.instrutor = instrutor;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
