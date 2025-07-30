        return cursos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public CursoResponseDTO criarCurso(CursoRequestDTO cursoDTO) {
        Usuario instrutor = getUsuarioAutenticado();
        
        if (!instrutor.getTipo().equals(TipoUsuario.INSTRUTOR)) {
            throw new RuntimeException("Apenas instrutores podem criar cursos");
        }

        // Validar datas
        if (cursoDTO.getDataInicio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Data de início deve ser futura");
        }
        
        if (cursoDTO.getDataFim().isBefore(cursoDTO.getDataInicio())) {
            throw new RuntimeException("Data de fim deve ser posterior à data de início");
        }

        Curso curso = new Curso();
        curso.setTitulo(cursoDTO.getTitulo());
        curso.setDescricao(cursoDTO.getDescricao());
        curso.setVagas(cursoDTO.getVagas());
        curso.setValor(cursoDTO.getValor());
        curso.setDataInicio(cursoDTO.getDataInicio());
        curso.setDataFim(cursoDTO.getDataFim());
        curso.setInstrutor(instrutor);
        curso.setAtivo(true);
        curso.setDataCriacao(LocalDateTime.now());

        curso = cursoRepository.save(curso);
        return convertToResponseDTO(curso);
    }
package com.venitech.startcurso.service;
    public CursoResponseDTO editarCurso(UUID id, CursoRequestDTO cursoDTO) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Usuario usuarioAutenticado = getUsuarioAutenticado();
        
        if (!curso.getInstrutor().getId().equals(usuarioAutenticado.getId())) {
            throw new RuntimeException("Você só pode editar seus próprios cursos");
        }

        // Validar datas
        if (cursoDTO.getDataInicio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Data de início deve ser futura");
        }
        
        if (cursoDTO.getDataFim().isBefore(cursoDTO.getDataInicio())) {
            throw new RuntimeException("Data de fim deve ser posterior à data de início");
        }

        curso.setTitulo(cursoDTO.getTitulo());
        curso.setDescricao(cursoDTO.getDescricao());
        curso.setVagas(cursoDTO.getVagas());
        curso.setValor(cursoDTO.getValor());
        curso.setDataInicio(cursoDTO.getDataInicio());
        curso.setDataFim(cursoDTO.getDataFim());

        curso = cursoRepository.save(curso);
        return convertToResponseDTO(curso);
    }

    public void excluirCurso(UUID id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Usuario usuarioAutenticado = getUsuarioAutenticado();
        
        if (!curso.getInstrutor().getId().equals(usuarioAutenticado.getId())) {
            throw new RuntimeException("Você só pode excluir seus próprios cursos");
        }

        // Verificar se há inscrições ativas
        long inscricoesAtivas = inscricaoRepository.countInscricoesConfirmadasPorCurso(curso);
        if (inscricoesAtivas > 0) {
            throw new RuntimeException("Não é possível excluir curso com inscrições ativas");
        }

        curso.setAtivo(false);
        cursoRepository.save(curso);
    }

    public List<CursoResponseDTO> meusCursos() {
        Usuario instrutor = getUsuarioAutenticado();
        
        if (!instrutor.getTipo().equals(TipoUsuario.INSTRUTOR)) {
            throw new RuntimeException("Apenas instrutores podem visualizar seus cursos");
        }

        List<Curso> cursos = cursoRepository.findByInstrutorandAtivoTrue(instrutor);
        return cursos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private CursoResponseDTO convertToResponseDTO(Curso curso) {
        CursoResponseDTO dto = modelMapper.map(curso, CursoResponseDTO.class);
        dto.setNomeInstrutor(curso.getInstrutor().getNomeCompleto());
        dto.setInstrutorId(curso.getInstrutor().getId());
        dto.setGratuito(curso.getValor().compareTo(BigDecimal.ZERO) == 0);
        dto.setTotalInscritos(inscricaoRepository.countInscricoesConfirmadasPorCurso(curso));
        return dto;
    }

    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}

import com.venitech.startcurso.dto.curso.CursoRequestDTO;
import com.venitech.startcurso.dto.curso.CursoResponseDTO;
import com.venitech.startcurso.model.entity.Curso;
import com.venitech.startcurso.model.entity.Usuario;
import com.venitech.startcurso.model.enums.TipoUsuario;
import com.venitech.startcurso.repository.CursoRepository;
import com.venitech.startcurso.repository.InscricaoRepository;
import com.venitech.startcurso.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CursoResponseDTO> listarCursosAtivos() {
        List<Curso> cursos = cursoRepository.findByAtivoTrue();
        return cursos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public CursoResponseDTO buscarPorId(UUID id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (!curso.getAtivo()) {
            throw new RuntimeException("Curso não está disponível");
        }

        return convertToResponseDTO(curso);
    }

    public List<CursoResponseDTO> buscarPorTermo(String termo) {
        List<Curso> cursos = cursoRepository.buscarPorTermo(termo);

