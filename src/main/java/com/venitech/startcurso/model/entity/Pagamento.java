package com.venitech.startcurso.model.entity;

import com.venitech.startcurso.model.enums.StatusPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscricao_id", nullable = false)
    private Inscricao inscricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotBlank(message = "Método de pagamento é obrigatório")
    @Column(nullable = false)
    private String metodoPagamento;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status do pagamento é obrigatório")
    @Column(nullable = false)
    private StatusPagamento statusPagamento;

    @Column
    private LocalDateTime dataPagamento;

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    // Construtores
    public Pagamento() {}

    public Pagamento(Inscricao inscricao, BigDecimal valor, String metodoPagamento) {
        this.inscricao = inscricao;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
        this.statusPagamento = StatusPagamento.AGUARDANDO;
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
