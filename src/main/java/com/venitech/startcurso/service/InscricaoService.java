package com.venitech.startcurso.service;

import com.venitech.startcurso.dto.inscricao.InscricaoRequestDTO;
import com.venitech.startcurso.dto.inscricao.InscricaoResponseDTO;
import com.venitech.startcurso.model.entity.Curso;
import com.venitech.startcurso.model.entity.Inscricao;
import com.venitech.startcurso.model.entity.Usuario;
import com.venitech.startcurso.model.enums.StatusInscricao;
import com.venitech.startcurso.model.enums.TipoUsuario;
import com.venitech.startcurso.repository.CursoRepository;
import com.venitech.startcurso.repository.InscricaoRepository;
import com.venitech.startcurso.repository.UsuarioRepository;
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
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public InscricaoResponseDTO inscreverEmCurso(InscricaoRequestDTO inscricaoDTO) {
        Usuario aluno = getUsuarioAutenticado();
        
        if (!aluno.getTipo().equals(TipoUsuario.ALUNO)) {
            throw new RuntimeException("Apenas alunos podem se inscrever em cursos");
        }

        Curso curso = cursoRepository.findById(inscricaoDTO.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (!curso.getAtivo()) {
            throw new RuntimeException("Curso não está disponível para inscrição");
        }

        // Verificar se já está inscrito no curso
        if (inscricaoRepository.existsByAlunoAndCurso(aluno, curso)) {
            throw new RuntimeException("Você já está inscrito neste curso");
        }

        // Verificar se curso já começou
        if (curso.getDataInicio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível se inscrever em curso que já iniciou");
        }

        // Verificar vagas disponíveis
        long inscricoesConfirmadas = inscricaoRepository.countInscricoesConfirmadasPorCurso(curso);
        if (inscricoesConfirmadas >= curso.getVagas()) {
            throw new RuntimeException("Curso sem vagas disponíveis");
        }

        // Criar inscrição
        Inscricao inscricao = new Inscricao(aluno, curso);
        
        // Se curso é gratuito, confirmar automaticamente
        if (curso.isGratuito()) {
            inscricao.setStatus(StatusInscricao.CONFIRMADA);
        } else {
            inscricao.setStatus(StatusInscricao.PENDENTE);
        }

        inscricao = inscricaoRepository.save(inscricao);
        return convertToResponseDTO(inscricao);
    }

    public List<InscricaoResponseDTO> minhasInscricoes() {
        Usuario aluno = getUsuarioAutenticado();
        
        if (!aluno.getTipo().equals(TipoUsuario.ALUNO)) {
            throw new RuntimeException("Apenas alunos podem visualizar suas inscrições");
        }

        List<Inscricao> inscricoes = inscricaoRepository.findByAluno(aluno);
        return inscricoes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<InscricaoResponseDTO> inscricoesDoCurso(UUID cursoId) {
        // Verificar se o usuário é o instrutor do curso
        Usuario instrutor = getUsuarioAutenticado();
        
        if (!instrutor.getTipo().equals(TipoUsuario.INSTRUTOR)) {
            throw new RuntimeException("Apenas instrutores podem visualizar inscrições dos seus cursos");
        }

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        if (!curso.getInstrutor().getId().equals(instrutor.getId())) {
            throw new RuntimeException("Você só pode visualizar inscrições dos seus próprios cursos");
        }

        List<Inscricao> inscricoes = inscricaoRepository.findByCurso(curso);
        return inscricoes.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public InscricaoResponseDTO confirmarInscricao(UUID inscricaoId) {
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));

        Usuario instrutor = getUsuarioAutenticado();
        
        if (!inscricao.getCurso().getInstrutor().getId().equals(instrutor.getId())) {
            throw new RuntimeException("Você só pode confirmar inscrições dos seus próprios cursos");
        }

        if (inscricao.getStatus().equals(StatusInscricao.CONFIRMADA)) {
            throw new RuntimeException("Inscrição já confirmada");
        }

        // Verificar vagas disponíveis
        long inscricoesConfirmadas = inscricaoRepository.countInscricoesConfirmadasPorCurso(inscricao.getCurso());
        if (inscricoesConfirmadas >= inscricao.getCurso().getVagas()) {
            throw new RuntimeException("Curso sem vagas disponíveis");
        }

        inscricao.setStatus(StatusInscricao.CONFIRMADA);
        inscricao = inscricaoRepository.save(inscricao);
        
        return convertToResponseDTO(inscricao);
    }

    public void cancelarInscricao(UUID inscricaoId) {
        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));

        Usuario usuario = getUsuarioAutenticado();
        
        // Aluno pode cancelar própria inscrição, instrutor pode cancelar qualquer inscrição do seu curso
        boolean podeCantelar = inscricao.getAluno().getId().equals(usuario.getId()) ||
                              (usuario.getTipo().equals(TipoUsuario.INSTRUTOR) && 
                               inscricao.getCurso().getInstrutor().getId().equals(usuario.getId()));

        if (!podeCantelar) {
            throw new RuntimeException("Você não tem permissão para cancelar esta inscrição");
        }

        // Verificar se curso já começou
        if (inscricao.getCurso().getDataInicio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível cancelar inscrição de curso que já iniciou");
        }

        inscricao.setStatus(StatusInscricao.RECUSADA);
        inscricaoRepository.save(inscricao);
    }

    private InscricaoResponseDTO convertToResponseDTO(Inscricao inscricao) {
        InscricaoResponseDTO dto = new InscricaoResponseDTO();
        dto.setId(inscricao.getId());
        dto.setAlunoId(inscricao.getAluno().getId());
        dto.setNomeAluno(inscricao.getAluno().getNomeCompleto());
        dto.setCursoId(inscricao.getCurso().getId());
        dto.setTituloCurso(inscricao.getCurso().getTitulo());
        dto.setNomeInstrutor(inscricao.getCurso().getInstrutor().getNomeCompleto());
        dto.setValorCurso(inscricao.getCurso().getValor());
        dto.setCursoGratuito(inscricao.getCurso().getValor().compareTo(BigDecimal.ZERO) == 0);
        dto.setStatus(inscricao.getStatus());
        dto.setDataInscricao(inscricao.getDataInscricao());
        dto.setDataInicioCurso(inscricao.getCurso().getDataInicio());
        dto.setDataFimCurso(inscricao.getCurso().getDataFim());
        return dto;
    }

    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return usuarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
