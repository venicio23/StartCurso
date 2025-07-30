package com.venitech.startcurso.dto.inscricao;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class InscricaoRequestDTO {
    
    @NotNull(message = "ID do curso é obrigatório")
    private UUID cursoId;

    // Construtores
    public InscricaoRequestDTO() {}

    public InscricaoRequestDTO(UUID cursoId) {
        this.cursoId = cursoId;
    }

    // Getters e Setters
    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }
}
