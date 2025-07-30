package com.venitech.startcurso.controller;

import com.venitech.startcurso.dto.inscricao.InscricaoRequestDTO;
import com.venitech.startcurso.dto.inscricao.InscricaoResponseDTO;
import com.venitech.startcurso.service.InscricaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inscricoes")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Inscrições", description = "Endpoints para gerenciamento de inscrições em cursos")
public class InscricaoController {

    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping
    @Operation(summary = "Inscrever-se em curso", description = "Permite que um aluno se inscreva em um curso")
    public ResponseEntity<InscricaoResponseDTO> inscreverEmCurso(@Valid @RequestBody InscricaoRequestDTO inscricaoDTO) {
        try {
            InscricaoResponseDTO inscricao = inscricaoService.inscreverEmCurso(inscricaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(inscricao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao se inscrever no curso: " + e.getMessage());
        }
    }

    @GetMapping("/minhas")
    @Operation(summary = "Minhas inscrições", description = "Lista todas as inscrições do aluno logado")
    public ResponseEntity<List<InscricaoResponseDTO>> minhasInscricoes() {
        try {
            List<InscricaoResponseDTO> inscricoes = inscricaoService.minhasInscricoes();
            return ResponseEntity.ok(inscricoes);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar suas inscrições: " + e.getMessage());
        }
    }

    @GetMapping("/curso/{cursoId}")
    @Operation(summary = "Inscrições do curso", description = "Lista inscrições de um curso específico (apenas para o instrutor do curso)")
    public ResponseEntity<List<InscricaoResponseDTO>> inscricoesDoCurso(@PathVariable UUID cursoId) {
        try {
            List<InscricaoResponseDTO> inscricoes = inscricaoService.inscricoesDoCurso(cursoId);
            return ResponseEntity.ok(inscricoes);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar inscrições do curso: " + e.getMessage());
        }
    }

    @PutMapping("/{inscricaoId}/confirmar")
    @Operation(summary = "Confirmar inscrição", description = "Confirma uma inscrição pendente (apenas instrutor do curso)")
    public ResponseEntity<InscricaoResponseDTO> confirmarInscricao(@PathVariable UUID inscricaoId) {
        try {
            InscricaoResponseDTO inscricao = inscricaoService.confirmarInscricao(inscricaoId);
            return ResponseEntity.ok(inscricao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao confirmar inscrição: " + e.getMessage());
        }
    }

    @DeleteMapping("/{inscricaoId}/cancelar")
    @Operation(summary = "Cancelar inscrição", description = "Cancela uma inscrição (aluno ou instrutor do curso)")
    public ResponseEntity<Void> cancelarInscricao(@PathVariable UUID inscricaoId) {
        try {
            inscricaoService.cancelarInscricao(inscricaoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cancelar inscrição: " + e.getMessage());
        }
    }
}
