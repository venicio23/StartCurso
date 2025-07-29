package com.venitech.startcurso.model.entity;

import com.venitech.startcurso.model.enums.StatusInscricao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inscricoes")
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @NotNull(message = "Data de inscrição é obrigatória")
    @Column(nullable = false)
    private LocalDateTime dataInscricao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status é obrigatório")
    @Column(nullable = false)
    private StatusInscricao status;

    // Construtores
    public Inscricao() {}

    public Inscricao(Usuario aluno, Curso curso) {
        this.aluno = aluno;
        this.curso = curso;
        this.dataInscricao = LocalDateTime.now();
        this.status = StatusInscricao.PENDENTE;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public StatusInscricao getStatus() {
        return status;
    }

    public void setStatus(StatusInscricao status) {
        this.status = status;
    }
}
