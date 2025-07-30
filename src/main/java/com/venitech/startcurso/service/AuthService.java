package com.venitech.startcurso.service;

import com.venitech.startcurso.dto.auth.AuthResponseDTO;
import com.venitech.startcurso.dto.auth.CadastroRequestDTO;
import com.venitech.startcurso.dto.auth.LoginRequestDTO;
import com.venitech.startcurso.model.entity.Usuario;
import com.venitech.startcurso.repository.UsuarioRepository;
import com.venitech.startcurso.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthResponseDTO cadastrar(CadastroRequestDTO cadastroDTO) {
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(cadastroDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado no sistema");
        }

        // Criar novo usuário
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(cadastroDTO.getNomeCompleto());
        usuario.setEmail(cadastroDTO.getEmail());
        usuario.setSenhaHash(passwordEncoder.encode(cadastroDTO.getSenha()));
        usuario.setTipo(cadastroDTO.getTipo());
        usuario.setAtivo(true);

        // Salvar no banco
        usuario = usuarioRepository.save(usuario);

        // Gerar token JWT
        String token = jwtTokenProvider.generateToken(usuario);

        // Retornar resposta
        return new AuthResponseDTO(
            token,
            usuario.getId(),
            usuario.getNomeCompleto(),
            usuario.getEmail(),
            usuario.getTipo()
        );
    }

    public AuthResponseDTO login(LoginRequestDTO loginDTO) {
        // Autenticar usuário
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
        );

        // Buscar usuário
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Gerar token JWT
        String token = jwtTokenProvider.generateToken(usuario);

        // Retornar resposta
        return new AuthResponseDTO(
            token,
            usuario.getId(),
            usuario.getNomeCompleto(),
            usuario.getEmail(),
            usuario.getTipo()
        );
    }
}
