package com.venitech.startcurso.controller;

import com.venitech.startcurso.dto.curso.CursoRequestDTO;
import com.venitech.startcurso.dto.curso.CursoResponseDTO;
import com.venitech.startcurso.service.CursoService;
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
@RequestMapping("/api/cursos")
@Tag(name = "Cursos", description = "Endpoints para gerenciamento de cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Operation(summary = "Listar todos os cursos", description = "Retorna lista de todos os cursos ativos disponíveis")
    public ResponseEntity<List<CursoResponseDTO>> listarCursos() {
        List<CursoResponseDTO> cursos = cursoService.listarCursosAtivos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso por ID", description = "Retorna detalhes de um curso específico")
    public ResponseEntity<CursoResponseDTO> buscarCursoPorId(@PathVariable UUID id) {
        try {
            CursoResponseDTO curso = cursoService.buscarPorId(id);
            return ResponseEntity.ok(curso);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar curso: " + e.getMessage());
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar cursos por termo", description = "Busca cursos por título ou descrição")
    public ResponseEntity<List<CursoResponseDTO>> buscarCursos(@RequestParam String termo) {
        List<CursoResponseDTO> cursos = cursoService.buscarPorTermo(termo);
        return ResponseEntity.ok(cursos);
    }

    @PostMapping("/criar")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Criar novo curso", description = "Cria um novo curso (apenas instrutores)")
    public ResponseEntity<CursoResponseDTO> criarCurso(@Valid @RequestBody CursoRequestDTO cursoDTO) {
        try {
            CursoResponseDTO curso = cursoService.criarCurso(cursoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(curso);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar curso: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/editar")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Editar curso", description = "Edita um curso existente (apenas o instrutor proprietário)")
    public ResponseEntity<CursoResponseDTO> editarCurso(@PathVariable UUID id,
                                                        @Valid @RequestBody CursoRequestDTO cursoDTO) {
        try {
            CursoResponseDTO curso = cursoService.editarCurso(id, cursoDTO);
            return ResponseEntity.ok(curso);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao editar curso: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/excluir")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Excluir curso", description = "Exclui um curso (apenas o instrutor proprietário)")
    public ResponseEntity<Void> excluirCurso(@PathVariable UUID id) {
        try {
            cursoService.excluirCurso(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir curso: " + e.getMessage());
        }
    }

    @GetMapping("/meus")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Meus cursos", description = "Lista cursos do instrutor logado")
    public ResponseEntity<List<CursoResponseDTO>> meusCursos() {
        try {
            List<CursoResponseDTO> cursos = cursoService.meusCursos();
            return ResponseEntity.ok(cursos);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar seus cursos: " + e.getMessage());
        }
    }
}
