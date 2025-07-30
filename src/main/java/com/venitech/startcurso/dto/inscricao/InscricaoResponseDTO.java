package com.venitech.startcurso.dto.inscricao;

import com.venitech.startcurso.model.enums.StatusInscricao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class InscricaoResponseDTO {

    private UUID id;
    private UUID alunoId;
    private String nomeAluno;
    private UUID cursoId;
    private String tituloCurso;
    private String nomeInstrutor;
    private BigDecimal valorCurso;
    private Boolean cursoGratuito;
    private StatusInscricao status;
    private LocalDateTime dataInscricao;
    private LocalDateTime dataInicioCurso;
    private LocalDateTime dataFimCurso;

    // Construtores
    public InscricaoResponseDTO() {}

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(UUID alunoId) {
        this.alunoId = alunoId;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public String getTituloCurso() {
        return tituloCurso;
    }

    public void setTituloCurso(String tituloCurso) {
        this.tituloCurso = tituloCurso;
    }

    public String getNomeInstrutor() {
        return nomeInstrutor;
    }

    public void setNomeInstrutor(String nomeInstrutor) {
        this.nomeInstrutor = nomeInstrutor;
    }

    public BigDecimal getValorCurso() {
        return valorCurso;
    }

    public void setValorCurso(BigDecimal valorCurso) {
        this.valorCurso = valorCurso;
    }

    public Boolean getCursoGratuito() {
        return cursoGratuito;
    }

    public void setCursoGratuito(Boolean cursoGratuito) {
        this.cursoGratuito = cursoGratuito;
    }

    public StatusInscricao getStatus() {
        return status;
    }

    public void setStatus(StatusInscricao status) {
        this.status = status;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public LocalDateTime getDataInicioCurso() {
        return dataInicioCurso;
    }

    public void setDataInicioCurso(LocalDateTime dataInicioCurso) {
        this.dataInicioCurso = dataInicioCurso;
    }

    public LocalDateTime getDataFimCurso() {
        return dataFimCurso;
    }

    public void setDataFimCurso(LocalDateTime dataFimCurso) {
        this.dataFimCurso = dataFimCurso;
    }
}
