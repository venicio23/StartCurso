package com.venitech.startcurso.controller;

import com.venitech.startcurso.dto.auth.AuthResponseDTO;
import com.venitech.startcurso.dto.auth.CadastroRequestDTO;
import com.venitech.startcurso.dto.auth.LoginRequestDTO;
import com.venitech.startcurso.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints para cadastro e login de usuários")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar novo usuário", description = "Cria uma nova conta de usuário (Aluno ou Instrutor)")
    public ResponseEntity<AuthResponseDTO> cadastrar(@Valid @RequestBody CadastroRequestDTO cadastroDTO) {
        try {
            AuthResponseDTO response = authService.cadastrar(cadastroDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Fazer login", description = "Autentica usuário e retorna token JWT")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginDTO) {
        try {
            AuthResponseDTO response = authService.login(loginDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer login: " + e.getMessage());
        }
    }
}
